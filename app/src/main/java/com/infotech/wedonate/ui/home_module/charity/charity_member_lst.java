package com.infotech.wedonate.ui.home_module.charity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.Api;
import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.adapter.Charity_member_list_adapter;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.util.Retroclient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class charity_member_lst extends Fragment {

    View view;
    RecyclerView charity_member_recyler;
    com.google.android.material.floatingactionbutton.FloatingActionButton floatingActionButton;
    APIinterface apIinterface;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_charity_member_lst, container, false);
         intialization();
         setdata();
         floatingActionButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 fetchdata();
             }
         });
         return view;
    }

    private void intialization() {
        charity_member_recyler = view.findViewById(R.id.charity_memberlist);
        floatingActionButton = view.findViewById(R.id.floating_chartity_mem_list);
        apIinterface = Retroclient.retroinit();
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.rgb(243, 102, 216));

    }

    private void setdata() {
        charity_member_recyler.setLayoutManager(new GridLayoutManager(getContext(),1));
        if(data_bank.ch_mem_list.size()==0) {
            fetchdata();
        }
        else{
            Charity_member_list_adapter ad = new Charity_member_list_adapter(data_bank.ch_mem_list);
            charity_member_recyler.setAdapter(ad);
        }

    }

    private void fetchdata() {
        Call<ArrayList<data_model>> c = apIinterface.fetch_member_list(data_bank.curUser.getEmail());
        c.enqueue(new Callback<ArrayList<data_model>>() {
            @Override
            public void onResponse(Call<ArrayList<data_model>> call, Response<ArrayList<data_model>> response) {
                if(response.code()==200)
                {
                    data_bank.ch_mem_list=response.body();
                    Charity_member_list_adapter ad = new Charity_member_list_adapter(data_bank.ch_mem_list);
                    charity_member_recyler.setAdapter(ad);

                }
                else{
                    //Log.d("fetchmem","fail");
                }
                
            }

            @Override
            public void onFailure(Call<ArrayList<data_model>> call, Throwable t) {

            }
        });
    }

}