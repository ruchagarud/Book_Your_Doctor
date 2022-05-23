package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity {

    Calendar calendar;
    TextView dateView;
    Button button;
    int year,month,day;

    EditText firstname,email,city;
    Button signIn;
    String userId;
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        Log.d("register", "onCreate: okk");
        Toast.makeText(registration.this,"Register Here!",Toast.LENGTH_SHORT).show();

        dateView = findViewById(R.id.dateText);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        firstname=findViewById(R.id.name);
        email=findViewById(R.id.email);
        city = findViewById(R.id.city);
        signIn=findViewById(R.id.signin);
        userId = mAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = mStore.collection("users").document(userId);


        button = findViewById(R.id.datebtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Calendar c = Calendar.getInstance();
               year = c.get(Calendar.YEAR);
               month = c.get(Calendar.MONTH);
               day = c.get(Calendar.DAY_OF_MONTH);
               DatePickerDialog datePickerDialog = new DatePickerDialog(registration.this,
                       new DatePickerDialog.OnDateSetListener() {
                           @Override
                           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                               dateView.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                           }
                       },year,month,day);
               datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
               datePickerDialog.show();
//                Toast.makeText(getApplicationContext(),"ca",Toast.LENGTH_LONG).show();
            }
        });
//        if(mAuth.getCurrentUser()!= null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                if(TextUtils.isEmpty(firstname.getText().toString())){
                    firstname.setError("User Name is required.");
                    return;
                }
                if(TextUtils.isEmpty(email.getText().toString())){
                    email.setError("Email is required.");
                    return;
                }

                if(TextUtils.isEmpty(city.getText().toString())){
                    city.setError("Please enter your city.");
                    return;
                }

                //datebase
                if(!firstname.getText().toString().isEmpty()  &&!email.getText().toString().isEmpty()){
                    String first = firstname.getText().toString();
                    String userEmail = email.getText().toString();
                    String userCity = city.getText().toString();

                    Map<String, Object> user = new HashMap<>();
                    user.put("first", first);
                    user.put("Email", userEmail);
                    user.put("City", userCity);
                    documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(),mainScreen.class));
                                finish();
                            }else{
                                Toast.makeText(registration.this,"Data insertion failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(registration.this,"All Fields are requried",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(registration.this,Login.class);
                startActivity(intent);
//                Toast.makeText(registration.this,"Done!",Toast.LENGTH_SHORT).show();


            }

        });

    }

    protected Dialog onCreateDialog(int id){
        if(id == 999){
            return new DatePickerDialog(this,myDateListener,year,month,day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showDate(arg1,arg2+1,arg3);
        }
    };
    private void showDate(int year, int month, int day){
        dateView.setText(new StringBuffer().append(day).append("/").append(month).append("/").append(year));
    }

}

