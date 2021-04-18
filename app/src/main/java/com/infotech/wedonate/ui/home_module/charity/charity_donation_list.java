package com.infotech.wedonate.ui.home_module.charity;

import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infotech.wedonate.R;
import com.infotech.wedonate.adapter.Charity_Recycler_adapter;
import com.infotech.wedonate.adapter.RecylerAdapter;
import com.infotech.wedonate.data.data_bank;

public class charity_donation_list extends Fragment {

    View view;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.fragment_charity_donation_list, container, false);
        initalization();
        setdonationlist();
        return view;
    }
    void initalization(){
        recyclerView = view.findViewById(R.id.charity_recyler_view);

    }
    void setdonationlist()
    {

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
       Charity_Recycler_adapter ad;
        ad = new Charity_Recycler_adapter(data_bank.charity_donations);
        recyclerView.setAdapter(ad);
    }
}