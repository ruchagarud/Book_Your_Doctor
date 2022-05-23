package com.example.login.Doctors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.login.R;
import com.example.login.profile;

public class neuroDoctor extends AppCompatActivity implements View.OnClickListener {

    Button B1,B2,B3,B4,B5,B6;
    Intent i;
    String[] typeDoc={"type","Doctor"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neuro_doctor);
        typeDoc[0]="neurologist";

        B1 = findViewById(R.id.neuro1Btn);
        B2 = findViewById(R.id.neuroDoc2btn);
        B3 = findViewById(R.id.neuroDoc3Btn);
        B4 = findViewById(R.id.neuro4Btn);
        B5 = findViewById(R.id.neuro5Btn);
        B6 = findViewById(R.id.neuroDoc6btn);
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
            i =new Intent(neuroDoctor.this,profile.class);
            typeDoc[1]="Dr. Swati Kadam";
            i.putExtra("key",typeDoc);
        }
        if(v == B2){
            i =new Intent(neuroDoctor.this,profile.class);
            typeDoc[1]="Dr. Suyash Kadam";
            i.putExtra("key",typeDoc);
        }
        if(v == B3){
            i =new Intent(neuroDoctor.this,profile.class);
            typeDoc[1]="Dr. Pranita Pawar";
            i.putExtra("key",typeDoc);
        }
        if(v == B4){
            i =new Intent(neuroDoctor.this,profile.class);
            typeDoc[1]="Dr. Rupesh Ghare";
            i.putExtra("key",typeDoc);
        }
        if(v == B5){
            i =new Intent(neuroDoctor.this,profile.class);
            typeDoc[1]="Dr. Sakshi Yadav";
            i.putExtra("key",typeDoc);
        }
        if(v == B6){
            i =new Intent(neuroDoctor.this,profile.class);
            typeDoc[1]="Dr. Vedansh Kadam";
            i.putExtra("key",typeDoc);
        }
        startActivity(i);
    }
}
