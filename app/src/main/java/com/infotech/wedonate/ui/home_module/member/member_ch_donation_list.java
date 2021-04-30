package com.infotech.wedonate.ui.home_module.member;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.infotech.wedonate.R;
import com.infotech.wedonate.adapter.Charity_Recycler_adapter;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.util.GetDonations;

public class member_ch_donation_list extends Fragment {

    View view;
    RecyclerView mem_ch_lst;
    FloatingActionButton floatingActionButton;
    androidx.appcompat.widget.Toolbar toolbar;
    GetDonations getDonations = new GetDonations("charity",data_bank.curUser.getCharityemail());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_member_ch_donation_list, container, false);
        intialization();
        setdata();
        return view;
    }


    private void intialization() {
        mem_ch_lst = view.findViewById(R.id.mem_ch_donationlist);
        floatingActionButton = view.findViewById(R.id.floating_mem_ch_donation_list);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.rgb(255, 217, 29));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDonations.fetchdata();
                setdata();
            }
        });
    }


    private void setdata() {
        mem_ch_lst.setLayoutManager(new GridLayoutManager(getContext(),1));
        Charity_Recycler_adapter ad;
//        Log.d("ch_mem",data_bank.charity_donations.size()+"");

        if(data_bank.charity_donations.size()==0){
            getDonations.fetchdata();
        }
        ad = new Charity_Recycler_adapter(data_bank.charity_donations);
        mem_ch_lst.setAdapter(ad);
    }
}