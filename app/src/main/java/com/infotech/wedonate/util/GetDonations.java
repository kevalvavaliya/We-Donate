package com.infotech.wedonate.util;

import android.util.Log;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.donation_model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDonations {
    APIinterface apIinterface = Retroclient.retroinit();
    public ArrayList<donation_model> temp_list = new ArrayList<>();
    String usertype;
    String email;
    public GetDonations(String usertype){
        this.usertype = usertype;
    }
    public  GetDonations(String usertype,String email)
    {
        this.usertype=usertype;
        this.email=email;
    }
    public void fetchdata() {
        Call<ArrayList<donation_model>> c = apIinterface.fetch_donation_list();
        c.enqueue(new Callback<ArrayList<donation_model>>() {
            @Override
            public void onResponse(Call<ArrayList<donation_model>> call, Response<ArrayList<donation_model>> response) {
                if (response.code() == 200) {
                    if(usertype.equals("charity")){
                        temp_list=response.body();

                    }
                    else if(usertype.equals("donor")){
                        data_bank.donations = response.body();
                    }
                    if (data_bank.donations.size() != 0) {
                        for (int i = 0; i < data_bank.donations.size(); i++) {
                            if(data_bank.donations.get(i).getItem_category().equalsIgnoreCase("food")){
                                data_bank.donations_amenities.add(data_bank.donations.get(i));
                            }
                            else if(data_bank.donations.get(i).getItem_category().equalsIgnoreCase("health")){
                                data_bank.donations_health.add(data_bank.donations.get(i));
                            }
                            else if(data_bank.donations.get(i).getItem_category().equalsIgnoreCase("education")){
                                data_bank.donations_education.add(data_bank.donations.get(i));
                            }
                            else if(data_bank.donations.get(i).getItem_category().equals("nature")){
                                data_bank.donations_nature.add(data_bank.donations.get(i));
                            }

                        }
                    }
                    if(temp_list.size()!=0){
                        Log.d("ch_mem",email+"");
                        for(int i=0;i<temp_list.size();i++)
                        {
                            if(temp_list.get(i).getCharity_email().equalsIgnoreCase(email)){
                                data_bank.charity_donations.add(temp_list.get(i));
                                Log.d("ch_mem",data_bank.charity_donations.size()+"");

                            }
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<donation_model>> call, Throwable t) {
                Log.d("Server", "Error");
                //Toast.makeText(home.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
