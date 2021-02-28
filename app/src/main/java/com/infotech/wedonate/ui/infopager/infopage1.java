package com.infotech.wedonate.ui.infopager;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.infotech.wedonate.R;
import com.infotech.wedonate.ui.info;

public class infopage1 extends Fragment {

    Button signin,signup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_infopage1, container, false);
       /* signin= ((info)getActivity()).signin_bt;
        signup = ((info)getActivity()).signup_bt;
        signin.setBackgroundColor(Color.rgb(230,59,81));
        signup.setBackgroundColor(Color.rgb(230,59,81));*/
        return view;
    }
}