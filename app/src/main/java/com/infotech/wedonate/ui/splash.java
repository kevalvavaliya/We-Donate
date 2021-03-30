package com.infotech.wedonate.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.ui.home_module.home;
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class splash extends AppCompatActivity implements  Runnable{

    Handler h;
    SharedPreferences sf;
    String email,usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        h = new Handler();
        h.postDelayed(this,3000);

        sf = getSharedPreferences("Login", MODE_PRIVATE);
        usertype= sf.getString("usertype","nodata");
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
        if(usertype.equals("donor") || usertype.equals("member") || usertype.equals("charity"))
        {
            intent =  new Intent(this, home.class);
        }
        else{
            intent = new Intent(this,info.class);
        }
        startActivity(intent);
        finish();
    }
}