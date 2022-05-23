package com.example.login.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.login.Doctors.*;
import com.example.login.R;



public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        cDoctor = FirebaseFirestore.getInstance();
        Button btndentist = view.findViewById(R.id.dentistBtn);
        btndentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),dentistDoctor.class);
                in.putExtra("","Dentist");
                startActivity(in);
            }
        });

        Button btnGeneral = view.findViewById(R.id.generalBtn);
        btnGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),generalDoctor.class);
                in.putExtra("some","some data");
                startActivity(in);
            }
        });

        Button btnNeurologist = view.findViewById(R.id.neurologisttBtn);
        btnNeurologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),neuroDoctor.class);
                in.putExtra("some","some data");
                startActivity(in);
            }
        });
        Button btnOrthopedic = view.findViewById(R.id.orthopedicBtn);
        btnOrthopedic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),orthoDoctor.class);
                in.putExtra("some","some data");
                startActivity(in);
            }
        });
        Button btnCardioloist = view.findViewById(R.id.cardiologistBtn);
        btnCardioloist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),cardioDoctor.class);
                in.putExtra("some","some data");
                startActivity(in);
            }
        });
        Button btnGynologiest = view.findViewById(R.id.gynologiestBtn);
        btnGynologiest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),gynologistDoctor.class);
                in.putExtra("some","some data");
                startActivity(in);
            }
        });
        Button btnPeditrician = view.findViewById(R.id.pediatricianBtn);
        btnPeditrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),pediatricianDcotor.class);
                in.putExtra("some","some data");
                startActivity(in);
            }
        });
        Button btnPetDog = view.findViewById(R.id.petdogBtn);
        btnPetDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),petdogDoctor.class);
                in.putExtra("some","some data");
                startActivity(in);
            }
        });
        Button btnPsyco = view.findViewById(R.id.psychoBtn);
        btnPsyco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),psychologistDoctor.class);
                in.putExtra("some","some data");
                startActivity(in);
            }
        });
        Button btnRadio = view.findViewById(R.id.radiologistBtn);
        btnRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),radiologistDoctor.class);
                in.putExtra("some","some data");
                startActivity(in);
            }
        });
        Button btnSkin = view.findViewById(R.id.skinBtn);
        btnSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),skinDoctor.class);
                in.putExtra("some","some data");
                startActivity(in);
            }
        });
        Button btnENT = view.findViewById(R.id.entBtn);
        btnENT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),entDoctor.class);
                in.putExtra("some","some data");
                startActivity(in);
            }
        });

        return view;
    }

}