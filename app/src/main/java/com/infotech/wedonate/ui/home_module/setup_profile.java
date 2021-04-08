package com.infotech.wedonate.ui.home_module;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;

public class setup_profile extends Fragment implements View.OnClickListener {
    View view;
    Button complete_profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_setup_profile, container, false);
        complete_profile= view.findViewById(R.id.complete_profile);
        complete_profile.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getActivity(),home.class);
        i.putExtra("fragment","profile");
        startActivity(i);
        getActivity().finish();
    }
}