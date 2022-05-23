package com.example.login.Doctors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.login.R;
import com.example.login.profile;

public class cardioDoctor extends AppCompatActivity implements View.OnClickListener{

    Button B1,B2,B3,B4,B5,B6;
    Intent i;
    String[] typeDoc={"type","Doctor"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_doctor);
        typeDoc[0]="cardiologist";

        B1 = findViewById(R.id.cardio1Btn);
        B2 = findViewById(R.id.cardioDoc2btn);
        B3 = findViewById(R.id.cardioDoc3Btn);
        B4 = findViewById(R.id.cardio4Btn);
        B5 = findViewById(R.id.cardio5Btn);
        B6 = findViewById(R.id.cardioDoc6btn);

        B1.setOnClickListener(this);
        B2.setOnClickListener(this);
        B3.setOnClickListener(this);
        B4.setOnClickListener(this);
        B5.setOnClickListener(this);
        B6.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){

        if(v == B1){
            i =new Intent(cardioDoctor.this,profile.class);
            typeDoc[1]="Dr. Komal Yadav";
            i.putExtra("key",typeDoc);
        }
        if(v == B2){
            i =new Intent(cardioDoctor.this,profile.class);
            typeDoc[1]="Dr. Aayush Patil";
            i.putExtra("key",typeDoc);
        }
        if(v == B3){
            i =new Intent(cardioDoctor.this,profile.class);
            typeDoc[1]="Dr. Swaraj Garud";
            i.putExtra("key",typeDoc);
        }
        if(v == B4){
            i =new Intent(cardioDoctor.this,profile.class);
            typeDoc[1]="Dr. Rupali Yadav";
            i.putExtra("key",typeDoc);
        }
        if(v == B5){
            i =new Intent(cardioDoctor.this,profile.class);
            typeDoc[1]="Dr. Sneha Patil";
            i.putExtra("key",typeDoc);
        }
        if(v == B6){
            i =new Intent(cardioDoctor.this,profile.class);
            typeDoc[1]="Dr. Kishor Kadam";
            i.putExtra("key",typeDoc);
        }
        Log.d("typeDoc", "onClick: "+typeDoc[0]+typeDoc[1]);
        startActivity(i);
    }
}
