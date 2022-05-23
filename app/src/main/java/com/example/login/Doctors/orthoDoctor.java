package com.example.login.Doctors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.login.R;
import com.example.login.profile;

public class orthoDoctor extends AppCompatActivity implements View.OnClickListener {
    Button B1,B2,B3,B4,B5,B6;
    Intent i;
    String[] typeDoc={"type","Doctor"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ortho_doctor);
        typeDoc[0]="orthopedic";

        B1 = findViewById(R.id.ortho1Btn);
        B2 = findViewById(R.id.orthoDoc2btn);
        B3 = findViewById(R.id.orthoDoc3Btn);
        B4 = findViewById(R.id.ortho4Btn);
        B5 = findViewById(R.id.ortho5Btn);
        B6 = findViewById(R.id.orthoDoc6btn);

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
            i =new Intent(orthoDoctor.this,profile.class);
            typeDoc[1]="Dr. Aarohi Kadam";
            i.putExtra("key",typeDoc);
        }
        if(v == B2){
            i =new Intent(orthoDoctor.this,profile.class);
            typeDoc[1]="Dr. Tanvi Patil";
            i.putExtra("key",typeDoc);
        }
        if(v == B3){
            i =new Intent(orthoDoctor.this,profile.class);
            typeDoc[1]="Dr. Santosh Patil";
            i.putExtra("key",typeDoc);
        }
        if(v == B4){
            i =new Intent(orthoDoctor.this,profile.class);
            typeDoc[1]="Dr. Harsh Yadav";
            i.putExtra("key",typeDoc);
        }
        if(v == B5){
            i =new Intent(orthoDoctor.this,profile.class);
            typeDoc[1]="Dr. Rani Yadav";
            i.putExtra("key",typeDoc);
        }
        if(v == B6){
            i =new Intent(orthoDoctor.this,profile.class);
            typeDoc[1]="Dr. Omkar Jadhav";
            i.putExtra("key",typeDoc);
        }
        startActivity(i);
    }

}
