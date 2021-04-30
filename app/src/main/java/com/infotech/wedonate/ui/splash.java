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
import com.infotech.wedonate.util.GetDonations;
import com.infotech.wedonate.util.Retroclient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class splash extends AppCompatActivity implements  Runnable{

    Handler h;
    SharedPreferences sf;
    String email,usertype,name,mobile,address,charity_email;
    data_model CurentUser;
    private APIinterface apIinterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        apIinterface = Retroclient.retroinit();
        CurentUser = new data_model();
        sf = getSharedPreferences("Login", MODE_PRIVATE);
        usertype= sf.getString("usertype","nodata");
        email=sf.getString("useremail","nodata");
        name=sf.getString("username","nodata");
        mobile=sf.getString("mobile","nodata");
        address=sf.getString("address","nodata");
        charity_email=sf.getString("chemail","nodata");
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
        CurentUser.setCharityemail(charity_email);
        data_bank.curUser=CurentUser;
        if(usertype.equals("donor"))
        {
            data_bank.menu_flag=1;
            intent =  new Intent(this, home.class);
            GetDonations donations= new GetDonations("donor");
            donations.fetchdata();

        }
        else if(usertype.equals("member")){
            data_bank.menu_flag=2;
            GetDonations donations = new GetDonations("charity",charity_email);
            donations.fetchdata();
            intent =  new Intent(this, home.class);
        }
        else if( usertype.equals("charity")){
            data_bank.menu_flag=3;
            Log.d("charity",data_bank.curUser.getEmail());
            GetDonations donations = new GetDonations("charity",data_bank.curUser.getEmail());
            donations.fetchdata();
            intent =  new Intent(this, home.class);
        }
        else{
            intent = new Intent(this,info.class);
        }
        startActivity(intent);
        finish();
    }



}