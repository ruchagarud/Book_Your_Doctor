package com.example.login.Doctors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.login.R;
import com.example.login.profile;

public class entDoctor extends AppCompatActivity implements View.OnClickListener {
    Button B1,B2,B3,B4,B5,B6;
    Intent i;
    String[] typeDoc={"type","Doctor"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ent_doctor);
        TextView book = findViewById(R.id.title);
        typeDoc[0]="Ent";

        B1 = findViewById(R.id.ent1Btn);
        B2 = findViewById(R.id.entDoc2btn);
        B3 = findViewById(R.id.entDoc3Btn);
        B4 = findViewById(R.id.ent4Btn);
        B5 = findViewById(R.id.ent5Btn);
        B6 = findViewById(R.id.entDoc6btn);

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
            i =new Intent(entDoctor.this,profile.class);
            typeDoc[1]="Dr. Swapnali Ghare";
            i.putExtra("key",typeDoc);
        }
        if(v == B2){
            i =new Intent(entDoctor.this,profile.class);
            typeDoc[1]="Dr. Aman Kale";
            i.putExtra("key",typeDoc);
        }
        if(v == B3){
            i =new Intent(entDoctor.this,profile.class);
            typeDoc[1]="Dr. Swapnil Sathi";
            i.putExtra("key",typeDoc);
        }
        if(v == B4){
            i =new Intent(entDoctor.this,profile.class);
            typeDoc[1]="Dr. Gayatri Kadam";
            i.putExtra("key",typeDoc);
        }
        if(v == B5){
            i =new Intent(entDoctor.this,profile.class);
            typeDoc[1]="Dr. Riya Kumar";
            i.putExtra("key",typeDoc);
        }
        if(v == B6){
            i =new Intent(entDoctor.this,profile.class);
            typeDoc[1]="Dr. Asish mane";
            i.putExtra("key",typeDoc);
        }
        startActivity(i);
    }
}
