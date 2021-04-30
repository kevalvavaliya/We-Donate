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

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.adapter.Charity_Recycler_adapter;
import com.infotech.wedonate.adapter.Charity_member_list_adapter;
import com.infotech.wedonate.adapter.Charity_recieved_adapter;
import com.infotech.wedonate.adapter.RecylerAdapter;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.donation_model;
import com.infotech.wedonate.util.GetDonations;
import com.infotech.wedonate.util.Retroclient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class charity_donation_list extends Fragment {

    View view;
    RecyclerView recyclerView;
    APIinterface apIinterface;

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
        apIinterface = Retroclient.retroinit();

    }
    void setdonationlist()
    {



        if(data_bank.flag_charity_category==1) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            Charity_Recycler_adapter ad;
          //  Log.d("charity_list1",data_bank.charity_donations.size()+"");

            if(data_bank.charity_donations.size()==0){
                GetDonations getDonations =  new GetDonations("charity",data_bank.curUser.getEmail());
                getDonations.fetchdata();
            }
            ad = new Charity_Recycler_adapter(data_bank.charity_donations);
            recyclerView.setAdapter(ad);
        }
        else if(data_bank.flag_charity_category==3){

            Call<ArrayList<donation_model>> c = apIinterface.fetch_recieved_donations(data_bank.curUser.getEmail());
            c.enqueue(new Callback<ArrayList<donation_model>>() {
                @Override
                public void onResponse(Call<ArrayList<donation_model>> call, Response<ArrayList<donation_model>> response) {
                    if(response.code()==200)
                    {
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                        Charity_recieved_adapter ad = new Charity_recieved_adapter(response.body());
                        recyclerView.setAdapter(ad);
                    }
                    else{
                        Log.d("no recieved","donation");
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<donation_model>> call, Throwable t) {

                }
            });
        }
    }
}