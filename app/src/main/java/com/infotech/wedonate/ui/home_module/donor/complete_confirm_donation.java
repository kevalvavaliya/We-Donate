package com.infotech.wedonate.ui.home_module.donor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.ui.home_module.home;
import com.infotech.wedonate.ui.home_module.map;

public class complete_confirm_donation extends Fragment {


    View view;
    Button map_btn,home_btn;
    androidx.appcompat.widget.Toolbar toolbar;
    TextView tx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_complete_confirm_donation, container, false);

        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.rgb(206,37,58));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        map_btn = view.findViewById(R.id.map_btn);
        home_btn = view.findViewById(R.id.rtn_home);
        tx = view.findViewById(R.id.msg);
        tx.setText("Your donation request has been \n successfuly sent to the member "+data_bank.nearest_user.getUsername());
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), map.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), home.class);
                startActivity(i);
                getActivity().finish();

            }
        });
    }
}