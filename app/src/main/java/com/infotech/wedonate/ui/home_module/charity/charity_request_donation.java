package com.infotech.wedonate.ui.home_module.charity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.ui.home_module.home;
import com.infotech.wedonate.ui.home_module.setup_profile;
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class charity_request_donation extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbar_text;
    Drawable dr;
    FragmentTransaction ft;
    FragmentManager fm;
    APIinterface apIinterface;
    data_model user;
    String email;
    int flg=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_request_donation);

        initialization();
        setfragment();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(charity_request_donation.this, home.class);
                startActivity(intent);
            }
        });

    }

    void initialization() {
        toolbar = findViewById(R.id.toolbar);
        toolbar_text = findViewById(R.id.toolbar_text);
        toolbar_text.setText("Home");
        toolbar.setBackgroundColor(Color.argb(255, 255, 240, 204));
        dr = getResources().getDrawable(R.drawable.back_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(dr);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        apIinterface = Retroclient.retroinit();
        user= new data_model();

    }

    void setfragment() {
        email = data_bank.curUser.getEmail();
        boolean ch = isprofilecomplete();

    }

    boolean isprofilecomplete() {

        user.setEmail(email);
        user.setUsertype("charity");
        Call<String> c = apIinterface.check_profile(user);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("true")) {
                    ft.replace(R.id.request_donation_frm, new generate_request());
                    ft.commit();
                    flg=1;
                } else {
                    ft.replace(R.id.request_donation_frm, new setup_profile());
                    ft.commit();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(charity_request_donation.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
        if(flg==1){
            return true;
        }
        else
            return false;

    }

}