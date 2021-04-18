package com.infotech.wedonate.ui.home_module.charity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;


public class charity_fragment extends Fragment implements View.OnClickListener {

    View view;
    LinearLayout request_donation;
    ImageView your_donations;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_charity_fragment, container, false);
        your_donations = view.findViewById(R.id.charity_your_donations);
        request_donation=view.findViewById(R.id.request_donation);
        request_donation.setOnClickListener(this);
        your_donations.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if(v.getId() == R.id.request_donation){
            data_bank.flag_charity_category=0;
        }
        else if(v.getId() == R.id.charity_your_donations){
            data_bank.flag_charity_category=1;
        }
        intent = new Intent(getActivity(), charity_setfragment_activity.class);
        startActivity(intent);
    }
}