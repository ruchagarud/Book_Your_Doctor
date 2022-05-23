package com.example.login.Doctors;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.profile;
import android.content.Intent;
import android.view.View;
public class dentistDoctor extends AppCompatActivity implements View.OnClickListener {
    Button B1,B2,B3,B4,B5,B6;
    TextView dentistText1;
    Intent i;
    String[] typeDoc={"type","Doctor"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dentist_doctor);
        dentistText1 = findViewById(R.id.dentistText1);
        typeDoc[0]="Dentist";
        Bundle bundle = getIntent().getExtras();

        if(bundle!= null){
            if(bundle.getString("some")!=null){
                Toast.makeText(getApplicationContext(),"data:"+bundle.getString("some"),Toast.LENGTH_SHORT).show();
            }
        }
        B1 = findViewById(R.id.dentist1Btn);
        B2 = findViewById(R.id.dentistDoc2btn);
        B3 = findViewById(R.id.dentistDoc3Btn);
        B4 = findViewById(R.id.dentist4Btn);
        B5 = findViewById(R.id.dentist5Btn);
        B6 = findViewById(R.id.dentistDoc6btn);

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
            i =new Intent(dentistDoctor.this,profile.class);
            typeDoc[1]="Dr. Pooja Yadav";
            i.putExtra("key",typeDoc);
        }
        if(v == B2){
            i =new Intent(dentistDoctor.this,profile.class);
            typeDoc[1]="Dr. Aman Chitransh";
            i.putExtra("key",typeDoc);

        }if(v == B3){
            i =new Intent(dentistDoctor.this,profile.class);
            typeDoc[1]="Dr. Komal Kadam";
            i.putExtra("key",typeDoc);

        }if(v == B4){
            i =new Intent(dentistDoctor.this,profile.class);
            typeDoc[1]="Dr. Ritish Yadav";
            i.putExtra("key",typeDoc);

        }if(v == B5){
            i =new Intent(dentistDoctor.this,profile.class);
            typeDoc[1]="Dr. Rimi Singha";
            i.putExtra("key",typeDoc);

        }if(v == B6){
            i =new Intent(dentistDoctor.this,profile.class);
            typeDoc[1]="Dr. Ajit Kale";
            i.putExtra("key",typeDoc);

        }
        startActivity(i);
    }
}
