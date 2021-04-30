package com.infotech.wedonate.ui.home_module.member;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.donation_model;
import com.infotech.wedonate.ui.home_module.home;
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class notification_intent extends AppCompatActivity {

    TextView donor_name,donor_email,charity_email,item_name,item_category,donor_mobile;
    Button pick_up,view_map;
    static String d_email,mem_email;
    APIinterface apIinterface;
    SharedPreferences sf;

    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificatiion);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(404);

        intialization();
        Intent i = getIntent();
        if(i.getStringExtra("activity")!=null){
            if(i.getStringExtra("activity").equals("notification")){
                d_email = i.getStringExtra("donoremail");
                sf = getSharedPreferences("Login", MODE_PRIVATE);
                ed = sf.edit();
                ed.putString("current_donation_donor", d_email);
                ed.putString("current_donation_flag","1");
                ed.commit();

            }
        }
        setdata();

    }


    void intialization(){
        donor_name=findViewById(R.id.mem_donor_name);
        donor_email=findViewById(R.id.mem_donor_email);
        donor_mobile=findViewById(R.id.mem_donor_mobile);
        charity_email= findViewById(R.id.mem_charity_email);
        item_name= findViewById(R.id.mem_item_name);
        item_category = findViewById(R.id.mem_item_category);
        sf = getSharedPreferences("Login", MODE_PRIVATE);
        view_map=findViewById(R.id.mem_map_view);
        pick_up = findViewById(R.id.mem_pickup);

        d_email = sf.getString("current_donation_donor","nodata");
        mem_email=sf.getString("useremail","nodata");
        if(data_bank.curUser!=null){
        data_bank.curUser.setEmail(mem_email);
        }
        apIinterface= Retroclient.retroinit();

        view_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(notification_intent.this,member_map.class);
                startActivity(i);
            }
        });
        pick_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(notification_intent.this,picked_up_donations.class);
                startActivity(i);
            }
        });
    }

    private void setdata() {
        //Log.d("donor",d_email);
        donation_model d = new donation_model();
        Log.d("mem_email",mem_email);
        d.setMem_email(mem_email);
        d.setDonor_email(d_email);
        Call<donation_model> c = apIinterface.fetch_current_donation(d);

        c.enqueue(new Callback<donation_model>() {
            @Override
            public void onResponse(Call<donation_model> call, Response<donation_model> response) {
                if(response.code()==200)
                {
                     data_bank.current_donation = response.body();
                     donor_name.setText(response.body().getDonor_name());
                     donor_email.setText(response.body().getDonor_email());
                     donor_mobile.setText(response.body().getDonor_mobile());
                     charity_email.setText(response.body().getCharity_email());
                     item_name.setText(response.body().getItem_name());
                     item_category.setText(response.body().getItem_category());

                }
            }

            @Override
            public void onFailure(Call<donation_model> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, home.class);
        startActivity(i);
        finish();
    }
}