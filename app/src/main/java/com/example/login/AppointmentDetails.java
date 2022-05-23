package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.example.login.App.CHANNEL_1_ID;


public class AppointmentDetails extends AppCompatActivity {
    Button cancel;
    String userId,Pname,Phone,time1,doctorStr;
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    TextView dateTimeShow,CName,CMobile,doctor;
    ProgressDialog progressDialog;
    private NotificationManagerCompat notificationManager;

    final int SEND_SMS_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        final String acceptValues [] = getIntent().getStringArrayExtra("values");
        final String dateTime[] = acceptValues[2].split(" ");

        cancel = findViewById(R.id.cancleAppointment);
        dateTimeShow = findViewById(R.id.Time);
        CName = findViewById(R.id.CName);
        CMobile = findViewById(R.id.CMobile);
        doctor = findViewById(R.id.DoctName);

        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
        progressDialog.setTitle("Please Wait.");

        notificationManager = NotificationManagerCompat.from(this);

        Pname = acceptValues[3];
        Phone = acceptValues[4];
        time1 = acceptValues[2];
        doctorStr = acceptValues[0];
        Log.d("AppointmentDetails", "acceptedValues: "+time1);

        CName.setText(Pname);
        CMobile.setText(Phone);
        dateTimeShow.setText(time1);
        doctor.setText(doctorStr);

//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_CODE);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
//                try {
//                    if(checkPermission(Manifest.permission.SEND_SMS)){
//                        SmsManager smsManager = SmsManager.getDefault();
//                        smsManager.sendTextMessage(num,"7499885942","Your Appointment is canceled.",null,null);
//                        Toast.makeText(AppointmentDetails.this,"Message Send!",Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText(AppointmentDetails.this,"Permission Denied!",Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//                catch (Exception e){
//                    Log.d("appointmentDetails", "Error: "+e.getMessage());
//                }

                DocumentReference documentReference = mStore.collection("Doctors").document(acceptValues[1]).collection(acceptValues[0]).document(userId);
                Map<String,Object> user = new HashMap<>();

                user.put("Name",Pname);
                user.put("Date",dateTime[1]);
                user.put("Time",dateTime[2]);
                user.put("Phone",Phone);
                user.put("Status","Canceled");

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        sendNotification();
                        Log.i("AppointmentDetails", "onSuccess: Appointment is Canceled for : "+userId);
                        Toast.makeText(AppointmentDetails.this,"Your Appointment is canceled!!",Toast.LENGTH_SHORT).show();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });


            }
        });
    }
    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(AppointmentDetails.this,permission);
        Log.d("appointmentDetails", "Coming");
        return (check == PackageManager.PERMISSION_GRANTED);
    }
    public void sendNotification() {
        String title = "BookYourDoctor";
        String message = "Hello "+Pname+"\n Your Appointment is canceled!!!";

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", message);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();

        notificationManager.notify(1, notification);
    }
}
