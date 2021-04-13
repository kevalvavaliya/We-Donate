package com.infotech.wedonate.ui.home_module.donor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.adapter.RecylerAdapter;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.data.donation_model;
import com.infotech.wedonate.util.Retroclient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class donate extends Fragment {

    View view;
    RecyclerView donation_list;
    APIinterface apIinterface;
    String email;
    data_model user;
    //List<String> charity_name,charity_address,item_name,item_desc,category;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_donate, container, false);
       initalization();
       if(data_bank.donations.size()==0)
            fetchdata();

       setdonationlist();

       return view;
    }
    void initalization(){
        donation_list=view.findViewById(R.id.donate_donationlist);
        apIinterface= Retroclient.retroinit();
        user =  new data_model();
    }
    void setdonationlist(){
        donation_list.setLayoutManager(new LinearLayoutManager(getContext()));
            RecylerAdapter ad = new RecylerAdapter(data_bank.donations);
            donation_list.setAdapter(ad);
        }

    void fetchdata()
    {
        Call<ArrayList<donation_model>> c = apIinterface.fetch_donation_list();
        c.enqueue(new Callback<ArrayList<donation_model>>() {
            @Override
            public void onResponse(Call<ArrayList<donation_model>> call, Response<ArrayList<donation_model>> response) {
                if(response.code()==200){
                    data_bank.donations= response.body();
                    RecylerAdapter ad = new RecylerAdapter(data_bank.donations);
                    donation_list.setAdapter(ad);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<donation_model>> call, Throwable t) {
                Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}