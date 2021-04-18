package com.infotech.wedonate.ui.home_module.donor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.curLocation;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.ui.home_module.home;
import com.infotech.wedonate.util.FetchLocation;
import com.infotech.wedonate.util.Retroclient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class confirm_donation extends Fragment implements View.OnClickListener {

    View view;
    Button confirm_donation;
    FetchLocation location;
    ArrayList<String> location_coordinates = new ArrayList<>();
    APIinterface apIinterface;
    curLocation curloc;
    LinearLayout confirm_back;
    TextView charity_name,charity_address,charity_mobile,charity_email,item_category,item_name,item_desc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_confirm_donation, container, false);

        intialization();
        setdata();

        return view;
    }
    void intialization(){
        charity_name = view.findViewById(R.id.cnf_charity_name);
        charity_email=view.findViewById(R.id.cnf_charity_email);
        charity_address= view.findViewById(R.id.cnf_charity_address);
        charity_mobile=view.findViewById(R.id.cnf_charity_mobile);
        item_category=view.findViewById(R.id.cnf_item_category);
        item_desc=view.findViewById(R.id.cnf_item_desc);
        item_name=view.findViewById(R.id.cnf_item_name);
        confirm_donation=view.findViewById(R.id.cnf_donation);
        confirm_donation.setOnClickListener(this);
        apIinterface= Retroclient.retroinit();
        curloc = new curLocation();
        confirm_back = view.findViewById(R.id.linear_confirm_back);
    }
    void setdata(){
        charity_name.setText(data_bank.donations.get(data_bank.position).getCharity_name());
        charity_email.setText(data_bank.donations.get(data_bank.position).getCharity_email());
        charity_mobile.setText("+91 "+data_bank.donations.get(data_bank.position).getCharity_mobile());
        charity_address.setText(data_bank.donations.get(data_bank.position).getAddress());
        item_name.setText(data_bank.donations.get(data_bank.position).getItem_name());
        item_category.setText(data_bank.donations.get(data_bank.position).getItem_category());
        item_desc.setText(data_bank.donations.get(data_bank.position).getItem_desc());
        if(data_bank.flag_donor_category==0){
            confirm_back.setBackgroundColor(Color.rgb(255,217,29));

        }
        else if(data_bank.flag_donor_category==1)
        {
            confirm_back.setBackgroundColor(Color.rgb(98,216,241));

        }
        else if(data_bank.flag_donor_category==2)
        {
            confirm_back.setBackgroundColor(Color.rgb(243,102,216));

        }
        else if(data_bank.flag_donor_category==3)
        {
            confirm_back.setBackgroundColor(Color.rgb(240,149,97));

        }
        else if(data_bank.flag_donor_category==4)
        {
            confirm_back.setBackgroundColor(Color.rgb(92,212,150));
        }
    }

    @Override
    public void onClick(View v) {
        request_location();
    }
    void request_location(){
        String permission[] = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
        requestPermissions(permission,0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            location = new FetchLocation(getContext());
            location.GetLocation();
            if(data_bank.current_location !=null){
                curloc.setLatitude(data_bank.current_location.getLatitude());
                curloc.setLongitude(data_bank.current_location.getLongitude());
                curloc.setUseremail(data_bank.curUser.getEmail());
                curloc.setUsername(data_bank.curUser.getName());
                curloc.setUsertype(data_bank.curUser.getUsertype());
                storelocation();
            }
        }
        else
        {
            request_location();
            Toast.makeText(getContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            getContext().startActivity(intent);
        }
    }

    public void storelocation() {
        Log.d("LOCATION",curloc.getUsertype());
        Call<String> c = apIinterface.locations(curloc);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().equals("true"))
                {
                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(), "Server Failure", Toast.LENGTH_SHORT).show();
            }
        });



    }


}