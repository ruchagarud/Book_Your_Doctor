package com.example.login.ui.Logout;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.login.Login;
import com.example.login.R;
import com.google.firebase.auth.FirebaseAuth;

public class logout extends Fragment {

    private LogoutViewModel mViewModel;

    public static logout newInstance() {
        return new logout();
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), Login.class));
        getActivity().finish();
        Log.d("Logout", "onStart: Logout");

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.logout_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LogoutViewModel.class);
        // TODO: Use the ViewModel
    }

}
