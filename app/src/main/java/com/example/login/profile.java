package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.*;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.*;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.*;

import java.text.DateFormat;
import java.util.*;

import static com.example.login.App.CHANNEL_1_ID;

public class profile extends AppCompatActivity implements View.OnClickListener{
    String format;
    static TextView timeshow,dateshow,state;
    Button book;
    int year,day,month,minute,hour;
    String doctorname[]={"type","doctor"},userID,type,doctor;
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    EditText patient,phone;
    ProgressDialog progressDialog;
    final  Calendar time = Calendar.getInstance();
    final  Calendar date = Calendar.getInstance();
    private NotificationManagerCompat notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dateshow = findViewById(R.id.dateShow);
        timeshow = findViewById(R.id.timeShow);
        patient = findViewById(R.id.patient);
        book = findViewById(R.id.bookbtn);
        phone = findViewById(R.id.phone);
        progressDialog = new ProgressDialog(this);

        dateshow.setOnClickListener(this);
        timeshow.setOnClickListener(this);
        book.setOnClickListener(this);

        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        notificationManager = NotificationManagerCompat.from(this);

        userID = mAuth.getCurrentUser().getUid();
        doctorname= getIntent().getStringArrayExtra("key");
        type = doctorname[0];
        doctor = doctorname[1];
        Log.d("profile", "onCreate: type : "+type+"Doctor: "+doctor);


    }
    public  void onClick(View v){
        if (v == dateshow){
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(profile.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            date.set(Calendar.YEAR,year);
                            date.set(Calendar.MONTH,month);
                            date.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                            dateshow.setText("Date :- "+dayOfMonth+"/"+(month+1)+"/"+year);
                        }
                    },year,month,day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
            datePickerDialog.show();
        }
        if (v == timeshow){
            final  Calendar c = Calendar.getInstance();
            Intent intent = new Intent(profile.this,timeSlots.class);
            Log.d("TAG", "onClick: "+dateshow.getText().toString());
            intent.putExtra("date",dateshow.getText().toString());
            startActivity(intent);

//            String timeSlot = getIntent().getExtras().getString("time");
//            timeshow.setText(timeSlot);






//            hour = c.get(Calendar.HOUR_OF_DAY);
//            minute = c.get(Calendar.MINUTE);
//            TimePickerDialog timePickerDialog = new TimePickerDialog(profile.this,
//                    new TimePickerDialog.OnTimeSetListener() {
//                        @Override
//                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                            time.set(Calendar.HOUR_OF_DAY,hourOfDay);
//                            time.set(Calendar.MINUTE,minute);
//                            if(time.getTimeInMillis()>=c.getTimeInMillis()||date.getTimeInMillis()>=c.getTimeInMillis()){
////                                hour = hourOfDay % 12;
//                                showTime(hourOfDay,minute);
//                            }else{
//                                timeshow.setText("");
//                                Toast.makeText(profile.this,"Invalid Time", Toast.LENGTH_LONG).show();
//                            }
////                            showTime(hourOfDay,minute);
//                        }
//                    },hour,minute,false);
//            timePickerDialog.show();
        }
        if(v == book){
            if(TextUtils.isEmpty(patient.getText().toString())){
                patient.setError("Patient Name is required.");
                return;
            }
            if(TextUtils.isEmpty(dateshow.getText().toString())){
                dateshow.setError("No date selected.");
                return;
            }
            if(TextUtils.isEmpty(timeshow.getText().toString())){
                timeshow.setError("No time selected.");
                return;
            }
            if(TextUtils.isEmpty(phone.getText().toString()) || phone.length()>10){
                phone.setError("Invalid Phone number.");
                return;
            }
            progressDialog.setMessage("Booking....");
            progressDialog.show();
            submitDetails();
        }
    }

//    public void showTime(int hour, int min) {
//        if (hour == 0) {
//            hour += 12;
//            format = "AM";
//        } else if (hour == 12) {
//            format = "PM";
//        } else if (hour > 12) {
//            hour -= 12;
//            format = "PM";
//        } else {
//            format = "AM";
//        }
//
//        timeshow.setText(new StringBuilder().append("Time :- ").append(hour).append(":").append(min)
//                .append("").append(format));
//    }

    private void submitDetails() {
        DocumentReference documentReference = mStore.collection("Doctors").document(type).collection(doctor).document(userID);
        Map<String,Object> user = new HashMap<>();
        user.put("Name",patient.getText().toString());
        user.put("Date",dateshow.getText().toString());
        user.put("Time",timeshow.getText().toString());
        user.put("Phone",phone.getText().toString());
        user.put("Status","Booked");
//        user.put("timestamp", ServerValue.TIMESTAMP);
//        Log.d("ServerValue.TIMESTAMP", "submitDetails: "+ServerValue.TIMESTAMP);


        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Log.i("profile", "onSuccess: Appointment is booked for : "+userID);
                sendNotification();
                Toast.makeText(profile.this,"Appointment booked Successfully!!!",Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void sendNotification() {
        String title = "BookYourDoctor";
        String message = "Hello "+patient.getText().toString()+"\n Your Appointment is booked Successfully!!!";

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
