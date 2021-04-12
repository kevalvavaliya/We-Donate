package com.infotech.wedonate.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.ui.home_module.home;

public class splash extends AppCompatActivity implements  Runnable{

    Handler h;
    SharedPreferences sf;
    String email,usertype,name,mobile,address;
    data_model CurentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        CurentUser = new data_model();
        sf = getSharedPreferences("Login", MODE_PRIVATE);
        usertype= sf.getString("usertype","nodata");
        email=sf.getString("useremail","nodata");
        name=sf.getString("username","nodata");
        mobile=sf.getString("mobile","nodata");
        address=sf.getString("address","nodata");
        h = new Handler();
        h.postDelayed(this,3000);



      /*  Call<String> c = apIinterface.connect();
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().equals("true"))
                {
                    Log.d("connect","Connection Success");
                }
                else{
                    Log.d("connect","Connection Fail");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("connect","server failure");
            }
        });*/

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
}