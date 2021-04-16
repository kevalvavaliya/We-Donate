package com.infotech.wedonate.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.data.donation_model;
import com.infotech.wedonate.ui.home_module.home;
import com.infotech.wedonate.util.Retroclient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class splash extends AppCompatActivity implements  Runnable{

    Handler h;
    SharedPreferences sf;
    String email,usertype,name,mobile,address;
    data_model CurentUser;
    private APIinterface apIinterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        apIinterface = Retroclient.retroinit();
        fetchdata();

        CurentUser = new data_model();
        sf = getSharedPreferences("Login", MODE_PRIVATE);
        usertype= sf.getString("usertype","nodata");
        email=sf.getString("useremail","nodata");
        name=sf.getString("username","nodata");
        mobile=sf.getString("mobile","nodata");
        address=sf.getString("address","nodata");
        h = new Handler();
        h.postDelayed(this,3000);


    }

    @Override
    public void run() {
        Intent intent;
        CurentUser.setEmail(email);
        CurentUser.setUsertype(usertype);
        CurentUser.setName(name);
        CurentUser.setMobile(mobile);
        CurentUser.setAddress(address);

        data_bank.curUser=CurentUser;
        if(usertype.equals("donor"))
        {
            intent =  new Intent(this, home.class);
        }
        else if(usertype.equals("member")){
            intent =  new Intent(this, home.class);
        }
        else if( usertype.equals("charity")){
            intent =  new Intent(this, home.class);
        }

        else{
            intent = new Intent(this,info.class);
        }
        startActivity(intent);
        finish();
    }


    void fetchdata() {
        Call<ArrayList<donation_model>> c = apIinterface.fetch_donation_list();
        c.enqueue(new Callback<ArrayList<donation_model>>() {
            @Override
            public void onResponse(Call<ArrayList<donation_model>> call, Response<ArrayList<donation_model>> response) {
                if (response.code() == 200) {
                    data_bank.donations = response.body();

                    for (donation_model m : response.body()) {
                        String time = m.getTime();
                        data_bank.cur_req_end_time.add(Long.parseLong(time));
                    }
                    for(long d: data_bank.cur_req_end_time){
                        long cur_time = System.currentTimeMillis() / 1000;
                        data_bank.left_time.add(cur_time - d);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<donation_model>> call, Throwable t) {
                Toast.makeText(splash.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}