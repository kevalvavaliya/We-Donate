package com.infotech.wedonate.ui.home_module;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.infotech.wedonate.R;


public class charity_fragment extends Fragment {

    View view;
    LinearLayout request_donation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_charity_fragment, container, false);

        request_donation=view.findViewById(R.id.request_donation);

        return view;
    }
}