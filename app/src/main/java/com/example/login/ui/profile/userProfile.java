package com.example.login.ui.profile;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.*;
import com.example.login.R;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.*;
import com.squareup.picasso.Picasso;

import android.widget.*;
import android.content.Intent;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import android.provider.MediaStore;
import android.util.Log;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class userProfile extends Fragment implements View.OnClickListener {

    private UserProfileViewModel mViewModel;
    public static final int GALLERY_REQUEST_CODE = 105;
    int TAKE_IMAGE_CODE = 10001;
    ImageView selectedImage;
    ImageButton camera,gallery;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    TextView fullName,email,phone,userCity;
    String mName,mEmail,mPhone,userId,mCity;
    FirebaseUser user;
    public  static final int RequestPermissionCode  = 1 ;
    DocumentReference documentReference;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile_fragment, container, false);

        EnableRuntimePermission();

        camera = view.findViewById(R.id.imageView5);
        gallery = view.findViewById(R.id.gallary);
        selectedImage = view.findViewById(R.id.profile);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = FirebaseAuth.getInstance().getCurrentUser();
        documentReference = fStore.collection("users").document(userId);

        fullName = view.findViewById(R.id.profileFullName);
        email = view.findViewById(R.id.profileEmail);
        phone = view.findViewById(R.id.profilePhone);
        userCity = view.findViewById(R.id.UserCity);

        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);

        DocumentReference docRef =fStore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    mName = documentSnapshot.getString("first");
                    mEmail = documentSnapshot.getString("Email");
                    mCity = documentSnapshot.getString("City");
                    mPhone = firebaseAuth.getCurrentUser().getPhoneNumber();
                    Log.d("userProfile", "documentSnapshot.getString(\"first:\") "+documentSnapshot.getString("first"));

                    fullName.setText(mName);
                    email.setText(mEmail);
                    phone.setText(mPhone);
                    userCity.setText(mCity);
                    Log.d("userProfile", "Retrieving Data: "+mName+mCity+mEmail+mPhone);

                    if(user.getPhotoUrl() != null){
                        Picasso.get().load(user.getPhotoUrl()).into(selectedImage);
                        Log.d("userProfile", "onCreateView:If photoURL  "+user.getPhotoUrl());

                    }

                }else {
                    Log.d("userProfile", "Retrieving Data: Profile Data Not Found ");
                }
            }
        });
        return  view;


    }

    private void chooseImage() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Images"), GALLERY_REQUEST_CODE);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        // TODO: Use the ViewModel
    }

    public void handleImageClick(){
        Log.d("userProfile", "handleImageClick: comig");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_IMAGE_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("userProfile", "onActivityResult: "+requestCode);
        if(requestCode == TAKE_IMAGE_CODE){

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Log.d("userProfile", "onActivityResult: camera "+bitmap);
            selectedImage.setImageBitmap(bitmap);
            handleUpload(bitmap);

        }
        if(requestCode == GALLERY_REQUEST_CODE) {
                Uri contentUri = data.getData();

                try {
                    Bitmap bitmap = (Bitmap) MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),contentUri);
                    Log.d("userProfile", "onActivityResult: Gallery " + bitmap);

                    selectedImage.setImageBitmap(bitmap);

//                    Picasso.get().load(contentUri).into(selectedImage);
                    handleUpload(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d("userProfile", "onActivityResult: Gallery contentUri:  " + contentUri);
//

        }
    }

    private void handleUpload(Bitmap bitmap) {
//        Log.d("userProfile", "handleUpload: coming");
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading....");
        progressDialog.show();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(userId +".jpeg");
        storageReference.putBytes(byteArrayOutputStream.toByteArray())

                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        getDownloadUri(storageReference);
                        progressDialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Failed to upload Image",Toast.LENGTH_SHORT).show();
                        Log.d("usrProfile", "onFailure: "+e.getCause());
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        Log.d("userProfile", "onProgress: "+progress);
                        progressDialog.setMessage("uploading...."+(int)progress+"%");

                    }
                });

    }



    private  void  getDownloadUri(StorageReference sReference){

        sReference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("userProfile", "onSuccess: "+uri);
                        setUserProfileUrl(uri);
                    }
                });
    }

    private void setUserProfileUrl(Uri uri){
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        fuser.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(),"Update Successfully.",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Profile upload failed.",Toast.LENGTH_SHORT).show();
                        Log.d("userProfile", "onFailure: "+e.getCause());
                    }
                });
    }

    @Override
//    public void onPause() {
//        super.onPause();
//        String first = fullName.getText().toString();
//        String emailUser = email.getText().toString();
//        String cityUser = userCity.getText().toString();
//
//        Map<String, Object> user = new HashMap<>();
//        user.put("first", first);
//        user.put("Email", emailUser);
//        user.put("City", cityUser);
//        documentReference.set(user);
//    }

//    @Override
    public void onClick(View v) {
        if( v == camera){
            handleImageClick();
        }
        if(v == gallery){
            Log.d("userProfile", "onClick: comig");
            chooseImage();

        }

    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA))
        {
            Toast.makeText(getActivity(),"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }
}
