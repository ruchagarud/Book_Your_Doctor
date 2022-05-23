package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.*;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    EditText phone,codeEnter;
    Button nextBtn;
    ProgressBar progressBar;
    TextView state;
    CountryCodePicker codePicker;
    String verificationId;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationInProsess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        phone = findViewById(R.id.phone);
        codeEnter = findViewById(R.id.codeEnter);
        progressBar = findViewById(R.id.progressBar);
        nextBtn = findViewById(R.id.nextBtn);
        state = findViewById(R.id.state);
        codePicker = findViewById(R.id.ccp);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verificationInProsess){
                    if(!phone.getText().toString().isEmpty() && phone.getText().toString().length() == 10){

                        String phoneNum = "+"+codePicker.getSelectedCountryCode()+phone.getText().toString();
                        Log.d("Register", "onClick: phoneNumber = "+phoneNum);
                        progressBar.setVisibility(View.VISIBLE);
                        state.setText("Sending OTP..");
                        state.setVisibility(View.VISIBLE);
                        requestOTP(phoneNum);


                    }else{
                        phone.setError("Phone is not Valid.");
                    }
                }else{
                    String userOTP = codeEnter.getText().toString();
                    if(!userOTP.isEmpty() && userOTP.length() == 6){
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,userOTP);
                        verifyAuth(credential);

                    }else{
                        codeEnter.setError("Valid OTP is required.");
                    }

                }
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), mainScreen.class));
            finish();

        }
    }



    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    checkUserProfile();

                }else{
                    Toast.makeText(Login.this,"Authentication is failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void checkUserProfile() {
//        DocumentReference docRef  = fstore.collection("users").document(fAuth.getCurrentUser().getUid());
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.exists()){
//                    startActivity(new Intent(getApplicationContext(),mainScreen.class));
//                    finish();
//                }else{
//                    startActivity(new Intent(getApplicationContext(),registration.class));
//                    finish();
//                }
//            }
//        });
        fstore.collection("users").document(fAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            startActivity(new Intent(getApplicationContext(), mainScreen.class));
                            finish();
                        }else {
                            startActivity(new Intent(getApplicationContext(), registration.class));
                            finish();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Login", "onFailure: data failed.");
                    }
                });

    }



    private void requestOTP(String phone) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60L, TimeUnit.SECONDS, Login.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                progressBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                codeEnter.setVisibility(View.VISIBLE);
                verificationId =s;
                token = forceResendingToken;
                nextBtn.setText("Verify");
                nextBtn.setEnabled(true);
                verificationInProsess = true;

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(Login.this,"OTP is Expired.",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                verifyAuth(phoneAuthCredential);

            }
            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(Login.this,"Cannot create Account "+e.getMessage(),Toast.LENGTH_SHORT).show();


            }
        });

    }


}

