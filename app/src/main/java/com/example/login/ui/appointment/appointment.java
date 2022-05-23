package com.example.login.ui.appointment;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.*;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.*;
import android.view.*;
import android.widget.*;

import com.example.login.AppointmentDetails;
import com.example.login.DoctorTypeData;
import com.example.login.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class appointment extends Fragment {

    TextView doctorName1,doctorType1,appDateAndTime;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userid,uDate,uTime,status,doctor,type,dateAndTime,patientName,phone,dateStr,dateUser;
    DocumentReference documentReference;
    String dateA[]={"",""},time[]={"",""};
    DoctorTypeData doctorTypeData;
    int count=0,dateInt,dateStrInt;
    final Calendar timeCalender = Calendar.getInstance();
    final  Calendar dateCalender = Calendar.getInstance();

    ProgressDialog progressDialog;
    CardView cardview;
    Context context;

    Date date;
    SimpleDateFormat simpleDateFormat;

    LinearLayout linearLayout,cardLinearLayout;
    LinearLayout.LayoutParams layoutParams;


    private AppointmentViewModel mViewModel;
    public static appointment newInstance() {
        return new appointment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appointment_fragment, container, false);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userid = fAuth.getCurrentUser().getUid();

        date = Calendar.getInstance().getTime();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateStr = simpleDateFormat.format(date);
        Log.d("timeShow", "dateStr : "+dateStr);
//        Log.d("timeShow", "date time : "+date);

        doctorTypeData = new DoctorTypeData();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please wait.");
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        linearLayout = view.findViewById(R.id.linearLayout);
        context = getActivity().getApplicationContext();
        for(int i=0; i<doctorTypeData.doctors.length;i++ ){
            String[] getDocType = doctorTypeData.checkDocType(i);
            final String doctorType = doctorTypeData.doctors[i];
            for(final String docId :getDocType) {
                documentReference = fStore.collection("Doctors").document(doctorType).collection(docId).document(userid);
                Log.d("appointment", "doctorType: "+doctorType);

                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            progressDialog.dismiss();
                            Log.d("appointment", "onSuccess: exist");
                            status = documentSnapshot.getString("Status");

                            try {
                                if (status.equals("Booked")) {

                                    patientName = documentSnapshot.getString("Name");
                                    phone = documentSnapshot.getString("Phone");
                                    uDate = documentSnapshot.getString("Date");
                                    uTime = documentSnapshot.getString("Time");
                                    Log.d("timeShow", "uDate: "+uDate);

                                    dateA = uDate.split(":-");
                                    time = uTime.split(":-");
//                                    dateInt = Integer.parseInt( dateA[1] );
//                                    dateStrInt = Integer.parseInt( dateStr );
//                                    if(dateInt > dateStrInt){
//                                        Log.d("timeShow", "uDate.compareTo(dateStr) :"+uDate+"is after "+dateStr);
//                                    }
//                                    else{
//                                        Log.d("timeShow", "comig");

//                                        Map<String,Object> user = new HashMap<>();
//
//                                        user.put("Name",patientName);
//                                        user.put("Date",uDate);
//                                        user.put("Time",uTime);
//                                        user.put("Phone",phone);
//                                        user.put("Status","Canceled");
//
//                                        documentReference.set(user);
//                                        return;
//                                    }
                                    Log.d("timeShow", "date :" + dateStr + "  dateA: " + dateA[1] + "Time: " + time[1]);

                                    doctor = docId;
                                    type = doctorType;
                                    dateAndTime = dateA[1] + "" + time[1];
                                    addCardView(doctor, type, dateAndTime, patientName, phone);
                                }
                            }
                            catch(Exception e){
                                Log.d("timeShow", "Error :"+e.getMessage());
                            }
                        }
                    }
                });
                count++;
            }


        }

        return view;
    }

    private void addCardView(String doctor,String docType,String dateAndTime,String patient,String Pphone) {
        Log.d("appointment", "addCardView: done"+doctor+docType+dateAndTime);

        final String passingValues[] = {doctor,docType,dateAndTime,patient,Pphone};

        cardview = new CardView(context);
        cardLinearLayout = new LinearLayout(context);
        cardLinearLayout.setOrientation(LinearLayout.VERTICAL);

        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );


        layoutParams.setMargins(10,20,10,10);
        cardview.setLayoutParams(layoutParams);
        cardview.setRadius(20);

        cardLinearLayout.setPadding(25, 25, 25, 25);
        cardview.setMaxCardElevation(30);
        cardview.setMaxCardElevation(6);

        doctorName1 = new TextView(context);
        doctorType1 = new TextView(context);
        appDateAndTime = new TextView(context);

        doctorName1.setText(doctor);
        doctorType1.setText(docType);
        appDateAndTime.setText(dateAndTime);

        cardLinearLayout.addView(doctorName1);
        cardLinearLayout.addView(doctorType1);
        cardLinearLayout.addView(appDateAndTime);
//        cardLinearLayout.addView(appointmentDetails);

        doctorName1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        doctorType1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        appDateAndTime.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

        cardview.addView(cardLinearLayout);
        linearLayout.addView(cardview);
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AppointmentDetails.class);
                intent.putExtra("values",passingValues);
                startActivity(intent);
            }
        });

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AppointmentViewModel.class);
        // TODO: Use the ViewModel
    }

}
