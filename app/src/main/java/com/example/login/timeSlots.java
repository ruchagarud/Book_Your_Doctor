package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



public class timeSlots extends AppCompatActivity implements View.OnClickListener {
    TextView dateview;
    Button first,second,third,fourth,fifth,six,seven,eight,nine,tenth,eleven,twelve;
    Button thirteen,fourteen,fifteen,sixteen,seventeen,eighteen,nineteen,twenty,twentyOne,twentyTwo,twentyThree,twentyFour;
    String timeArray[] = {"",""},dateSplit[]= {"",""},dateArray[]= {"",""};
    DoctorTypeData doctorTypeData;
    FirebaseFirestore fStroe;
    String userDocumentFromDoctorColl;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slots);

        dateview = findViewById(R.id.dateview);
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        fourth = findViewById(R.id.fourth);
        fifth = findViewById(R.id.fifth);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        tenth = findViewById(R.id.tenth);
        eleven = findViewById(R.id.eleven);
        twelve = findViewById(R.id.twelve);
        thirteen = findViewById(R.id.thirteen);
        fourteen = findViewById(R.id.fourteen);
        fifteen = findViewById(R.id.fifteen);
        sixteen = findViewById(R.id.sixteen);
        seventeen = findViewById(R.id.seventeen);
        eighteen = findViewById(R.id.eighteen);
        nineteen = findViewById(R.id.nineteen);
        twenty = findViewById(R.id.twenty);
        twentyOne = findViewById(R.id.twentyOne);
        twentyTwo = findViewById(R.id.twentyTwo);
        twentyThree = findViewById(R.id.twentyThree);
        twentyFour = findViewById(R.id.twentyFour);

        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);
        fourth.setOnClickListener(this);
        fifth.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        tenth.setOnClickListener(this);
        eleven.setOnClickListener(this);
        twelve.setOnClickListener(this);
        thirteen.setOnClickListener(this);
        fourteen.setOnClickListener(this);
        fifteen.setOnClickListener(this);
        sixteen.setOnClickListener(this);
        seventeen.setOnClickListener(this);
        eighteen.setOnClickListener(this);
        nineteen.setOnClickListener(this);
        twenty.setOnClickListener(this);
        twentyOne.setOnClickListener(this);
        twentyTwo.setOnClickListener(this);
        twentyThree.setOnClickListener(this);
        twentyFour.setOnClickListener(this);

        doctorTypeData = new DoctorTypeData();
        fStroe = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();


        String dateReceive = getIntent().getExtras().getString("date");
        Log.d("TAG", "onCreate: " + dateReceive);
        dateview.setText(dateReceive);
        dateSplit = dateReceive.split(":-");

        getTimeStamp();
//        try {
//            for (String dateStr :doctorTypeData.dateUsed) {
//                Log.d("mostSpecial", "dateUsed.legth: "+dateStr);
//
//                if (dateSplit[1].trim().equals(dateStr)) {
//
//                }
//            }
//            doctorTypeData.dateUsed.add(dateSplit[1].trim());
//            Log.d("mostSpecial", "doctorTypeData.dateUsed[doctorTypeData.dateUsed.length]: "+doctorTypeData.dateUsed);
//        }
//        catch(Exception e){
//            Log.d("mostSpecial", "onCreate: "+e.getMessage());
//        }


    }

    private void getTimeStamp() {
        for (int i = 0; i < doctorTypeData.doctors.length; i++) {
            String[] getDocType = doctorTypeData.checkDocType(i);
            final String doctorType = doctorTypeData.doctors[i];
            for (final String docId : getDocType) {
                fStroe.collection("Doctors")
                        .document(doctorType)
                        .collection(docId).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Log.d("mostSpecial", "task Complete");
                                    try {
                                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                            userDocumentFromDoctorColl = queryDocumentSnapshot.getId();
                                            if(!userDocumentFromDoctorColl.equals("")) {
                                                String status = queryDocumentSnapshot.getString("Status");
                                                String date = queryDocumentSnapshot.getString("Date");
                                                dateArray = date.split(":-");
                                                Log.d("mostSpecial", "dateArray[1]: "+dateArray[1]+" dateSplit[1]: "+dateSplit[1]);

                                                if (status.equals("Booked") && dateArray[1].trim().equals(dateSplit[1].trim())) {

                                                    String time = queryDocumentSnapshot.getString("Time");
                                                    Log.d("mostSpecial", "onComplete: time: " + time);
                                                    timeArray = time.split(":-");
                                                    Log.d("mostSpecial", "Status: " + status + "split: " + timeArray[0] + "  " + timeArray[1]);
                                                    Log.d("mostSpecial", "onComplete: doctype " + docId);
                                                    Log.d("mostSpecial", "onComplete: " + userDocumentFromDoctorColl + "Time: " + timeArray[1]);
                                                    compareTimeStamp(timeArray[1].trim());
                                                }
                                                else {
                                                    Log.d("this", "onComplete: Error while gettig ");
                                                }
                                            }
                                        }
                                    }
                                    catch (Exception e){
                                        Log.d("mostSpecial", "Error : "+e.getMessage());
                                    }
                                }
                            }
                        });


            }
        }

    }

    private void compareTimeStamp(String time) {

        Log.d("mostSpecial", "compareTimeStamp: comig");
        for(String timeStr :doctorTypeData.timeStamp){
            Log.d("mostSpecial", "compareTimeStamp:timeStr: "+timeStr);
            if(time.equals(timeStr)){
                Log.d("mostSpecial", "compareTimeStamp: compared");
                compareText(time);
                break;
            }
        }

    }


    private void compareText(String time) {
        Log.d("mostSpecial", "compareText: comig");
        if(time.equals(first.getText().toString())){
            Log.d("mostSpecial", "first.setEnabled(false)");
            first.setEnabled(false);
        }
        if(time.equals(second.getText().toString())){
            second.setEnabled(false);
        }
        if(time.equals(third.getText().toString())){
            third.setEnabled(false);
        }
        if(time.equals(fourth.getText().toString())){
            fourth.setEnabled(false);
        }
        if(time.equals(fifth.getText().toString())){
            fifth.setEnabled(false);
        }
        if(time.equals(six.getText().toString())){
            six.setEnabled(false);
        }
        if(time.equals(seven.getText().toString())){
            seven.setEnabled(false);
        }
        if(time.equals(eight.getText().toString())){
            eight.setEnabled(false);
        }
        if(time.equals(nine.getText().toString())){
            nine.setEnabled(false);
        }
        if(time.equals(tenth.getText().toString())){
            tenth.setEnabled(false);
        }
        if(time.equals(eleven.getText().toString())){
            eleven.setEnabled(false);
        }
        if(time.equals(twelve.getText().toString())){
            twelve.setEnabled(false);
        }
        if(time.equals(thirteen.getText().toString())){
            thirteen.setEnabled(false);
        }
        if(time.equals(fourteen.getText().toString())){
            fourteen.setEnabled(false);
        }
        if(time.equals(fifteen.getText().toString())){
            fifteen.setEnabled(false);
        }
        if(time.equals(sixteen.getText().toString())){
            sixteen.setEnabled(false);
        }

        if(time.equals(seventeen.getText().toString())){
            seventeen.setEnabled(false);
        }

        if(time.equals(eighteen.getText().toString())){
            eighteen.setEnabled(false);
        }
        if(time.equals(nineteen.getText().toString())){
            nineteen.setEnabled(false);
        }
        if(time.equals(twenty.getText().toString())){
            twenty.setEnabled(false);
        }
        if(time.equals(twentyOne.getText().toString())){
            twentyOne.setEnabled(false);
        }
        if(time.equals(twentyTwo.getText().toString())){
            twentyTwo.setEnabled(false);
        }
        if(time.equals(twentyThree.getText().toString())){
            twentyThree.setEnabled(false);
        }
        if(time.equals(twentyFour.getText().toString())){
            twentyFour.setEnabled(false);
        }
        Log.d("mostSpecial", "compareText: dom");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.first:
                disableButton(first);

                break;
            case R.id.second:
                disableButton(second);
                break;
            case R.id.third:
                disableButton(third);
                break;
            case R.id.fourth:
                disableButton(fourth);
                break;
            case R.id.fifth:
                disableButton(fifth);
                break;
            case R.id.six:
                disableButton(six);
                break;
            case R.id.seven:
                disableButton(seven);
                break;
            case R.id.eight:
                disableButton(eight);
                break;
            case R.id.nine:
                disableButton(nine);
                break;
            case R.id.tenth:
                disableButton(tenth);
                break;
            case R.id.eleven:
                disableButton(eleven);
                break;
            case R.id.twelve:
                disableButton(twelve);
                break;
            case R.id.thirteen:
                disableButton(thirteen);
                break;
            case R.id.fourteen:
                disableButton(fourteen);
                break;
            case R.id.fifteen:
                disableButton(fifteen);
                break;
            case R.id.sixteen:
                disableButton(sixteen);
                break;
            case R.id.seventeen:
                disableButton(seventeen);
                break;
            case R.id.eighteen:
                disableButton(eighteen);
                break;
            case R.id.nineteen:
                disableButton(nineteen);
                break;
            case R.id.twenty:
                disableButton(twenty);
                break;
            case R.id.twentyOne:
                disableButton(twentyOne);
                break;
            case R.id.twentyTwo:
                disableButton(twentyTwo);
                break;
            case R.id.twentyThree:
                disableButton(twentyThree);
                break;
            case R.id.twentyFour:
                disableButton(twentyFour);
                break;
        }
    }

    private void disableButton(Button timeButton) {
        profile.timeshow.setText("Time :- "+timeButton.getText().toString());
        timeButton.setEnabled(false);
        Toast.makeText(timeSlots.this,timeButton.getText().toString(),Toast.LENGTH_SHORT).show();
        finish();

    }
}
