package com.infotech.wedonate.ui.home_module.donor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.Token_model;
import com.infotech.wedonate.data.curLocation;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.donation_model;
import com.infotech.wedonate.services.locationservice;
import com.infotech.wedonate.util.FetchLocation;
import com.infotech.wedonate.util.Retroclient;
import com.infotech.wedonate.util.asyncTask;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class confirm_donation extends Fragment implements View.OnClickListener {

    View view;
    Button confirm_donation;
    ArrayList<String> location_coordinates = new ArrayList<>();
    APIinterface apIinterface;
    curLocation curloc;
    LinearLayout confirm_back;
    ProgressDialog progressDialog;
    FragmentManager fm;
    FragmentTransaction ft;
    FetchLocation location ;
    TextView charity_name, charity_address, charity_mobile, charity_email, item_category, item_name, item_desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_confirm_donation, container, false);

        intialization();
        setdata();

        return view;
    }

    void intialization() {
        charity_name = view.findViewById(R.id.cnf_charity_name);
        charity_email = view.findViewById(R.id.cnf_charity_email);
        charity_address = view.findViewById(R.id.cnf_charity_address);
        charity_mobile = view.findViewById(R.id.cnf_charity_mobile);
        item_category = view.findViewById(R.id.cnf_item_category);
        item_desc = view.findViewById(R.id.cnf_item_desc);
        item_name = view.findViewById(R.id.cnf_item_name);
        confirm_donation = view.findViewById(R.id.cnf_donation);
        confirm_donation.setOnClickListener(this);
        apIinterface = Retroclient.retroinit();
        curloc = new curLocation();
        confirm_back = view.findViewById(R.id.linear_confirm_back);
        fm=getActivity().getSupportFragmentManager();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Fetching location coordinates...");
        location = new FetchLocation(getContext());
    }

    void setdata() {
        charity_name.setText(data_bank.donations.get(data_bank.position).getCharity_name());
        charity_email.setText(data_bank.donations.get(data_bank.position).getCharity_email());
        charity_mobile.setText("+91 " + data_bank.donations.get(data_bank.position).getCharity_mobile());
        charity_address.setText(data_bank.donations.get(data_bank.position).getAddress());
        item_name.setText(data_bank.donations.get(data_bank.position).getItem_name());
        item_category.setText(data_bank.donations.get(data_bank.position).getItem_category());
        item_desc.setText(data_bank.donations.get(data_bank.position).getItem_desc());
        if (data_bank.flag_donor_category == 0) {
            confirm_back.setBackgroundColor(Color.rgb(255, 217, 29));

        } else if (data_bank.flag_donor_category == 1) {
            confirm_back.setBackgroundColor(Color.rgb(98, 216, 241));

        } else if (data_bank.flag_donor_category == 2) {
            confirm_back.setBackgroundColor(Color.rgb(243, 102, 216));

        } else if (data_bank.flag_donor_category == 3) {
            confirm_back.setBackgroundColor(Color.rgb(240, 149, 97));

        } else if (data_bank.flag_donor_category == 4) {
            confirm_back.setBackgroundColor(Color.rgb(92, 212, 150));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            request_location();
        } else {
            progressDialog.show();
            location.GetLocation(new asyncTask() {
                @Override
                public void actionPerformed() {
                    //Log.d("else",data_bank.current_location+"");
                    if (data_bank.current_location != null) {
                        curloc.setLatitude(data_bank.current_location.getLatitude());
                        curloc.setLongitude(data_bank.current_location.getLongitude());
                        curloc.setUseremail(data_bank.curUser.getEmail());
                        curloc.setUsername(data_bank.curUser.getName());
                        curloc.setUsertype(data_bank.curUser.getUsertype());
                        storelocation();
                        progressDialog.dismiss();
                    }

                }
            });
        }    }

    void request_location() {
        String permission[] = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        requestPermissions(permission, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

            location.GetLocation(new asyncTask() {
                @Override
                public void actionPerformed() {
                    Log.d("store",data_bank.current_location +"");
                    if (data_bank.current_location != null) {
                        curloc.setLatitude(data_bank.current_location.getLatitude());
                        curloc.setLongitude(data_bank.current_location.getLongitude());
                        curloc.setUseremail(data_bank.curUser.getEmail());
                        curloc.setUsername(data_bank.curUser.getName());
                        curloc.setUsertype(data_bank.curUser.getUsertype());
                        storelocation();
                    }
                }
            });

        } else {
            request_location();
            Toast.makeText(getContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            getContext().startActivity(intent);
        }
    }

    public void storelocation() {
        Call<ArrayList<curLocation>> c = apIinterface.locations(curloc);
        c.enqueue(new Callback<ArrayList<curLocation>>() {
            @Override
            public void onResponse(Call<ArrayList<curLocation>> call, Response<ArrayList<curLocation>> response) {
                if(response.code()==200)
                {
                    data_bank.member_location_array=response.body();
                    find_nearest_member();
                }
                else{
                    progressDialog.dismiss();
                    Log.d("fail","fail");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<curLocation>> call, Throwable t) {
                Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();
            }
        });
       }


    private void  find_nearest_member() {
            String near_mem = "";
            String near_name="";
            boolean flag=false;
            ArrayList<curLocation> mem_loc = data_bank.member_location_array;

            Double maxLatitute = Double.parseDouble(data_bank.current_location.getLatitude())+0.5;
            Double minLatitute = Double.parseDouble(data_bank.current_location.getLatitude())-0.5;

            Double maxLongitude =Double.parseDouble(data_bank.current_location.getLongitude())+0.5;
            Double minLongitude = Double.parseDouble(data_bank.current_location.getLongitude())-0.5;

        for(curLocation c : mem_loc){
                Double latitude= Double.parseDouble(c.getLatitude());
                Double longitude= Double.parseDouble(c.getLongitude());
            //Log.d("near1",latitude+":::"+longitude);
            //Log.d("near1","min::"+minLatitute+":::"+minLongitude);
            //Log.d("neardonor",data_bank.current_location.getLatitude()+"::"+data_bank.current_location.getLongitude());

                if(latitude >= minLatitute && latitude <= maxLatitute ){
                    if(longitude >= minLongitude && longitude <= maxLongitude )
                    {
                        Log.d("nearmember",c.getUseremail());
                        near_mem = c.getUseremail();
                        near_name=c.getUsername();
                        flag=false;
                    }
                }
                else{
                    flag=true;
                    //Log.d("flag",flag+"");
                }
            }

        if(flag!=true) {
           // Log.d("nearmember","heelp");
           // progressDialog.dismiss();
            progressDialog.setMessage("Finding member...");
            data_bank.nearest_user.setUseremail(near_mem);
            data_bank.nearest_user.setUsername(near_name);
            notifymember();

            fm.beginTransaction().replace(R.id.donor_donation_frm, new complete_confirm_donation()).commit();
        }
        else{
            progressDialog.dismiss();
            fm.beginTransaction().replace(R.id.donor_donation_frm, new incomplete_confirm_donation()).commit();
        }

    }

    private void notifymember() {

        Token_model t = new Token_model();
        t.setEmail(data_bank.nearest_user.getUseremail());
        t.setDonor_email(data_bank.curUser.getEmail());
        t.setUsertype("member");
        Call<String> c = apIinterface.sendNotify(t);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()==200)
                {
                    //Log.d("notifcation","success");
                    store_current_donation();
                }
                else{
                    Log.d("notifcation","fail");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

    private void store_current_donation() {
        donation_model cur = new donation_model();
        cur.setDonor_name(data_bank.curUser.getName());
        cur.setDonor_email(data_bank.curUser.getEmail());
        cur.setDonor_mobile(data_bank.curUser.getMobile());
        cur.setCharity_email(data_bank.current_donation.getCharity_email());
        cur.setItem_category(data_bank.current_donation.getItem_category());
        cur.setItem_name(data_bank.current_donation.getItem_name());
        cur.setMem_email(data_bank.nearest_user.getUseremail());

        Call<String> c = apIinterface.current_donation(cur);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()==200)
                {
                   // Log.d("Current_Donation","success");
                    progressDialog.dismiss();
                }
                else{
                  //  Log.d("Current_Donation","fail");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
               // Log.d("Current_Donation","server error");
                progressDialog.dismiss();

            }
        });
        

    }
}