package com.infotech.wedonate.ui.home_module.member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.Token_model;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.donation_model;
import com.infotech.wedonate.ui.home_module.home;
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class picked_up_donations extends AppCompatActivity {

    TextView item_name,item_cat,donor_email,ch_email;
    APIinterface apIinterface;
    Button return_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picked_up_donations);
        initalization();
        sendnotificationdonor();

        setdata();
    }

    private void sendnotificationcharity() {
        Token_model t = new Token_model();
        t.setEmail(data_bank.current_donation.getCharity_email());
        t.setUsertype("charity");
        t.setDonor_email(data_bank.curUser.getEmail());
        Call<String> c = apIinterface.sendNotify(t);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()==200)
                {
                    //Log.d("notifcation","success");
                    delete_currentdonation();
                }
                else{
                 //   Log.d("notifcation","fail");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void sendnotificationdonor() {
        Token_model t = new Token_model();
        t.setEmail(data_bank.current_donation.getDonor_email());
        t.setUsertype("donor");

        Call<String> c = apIinterface.sendNotify(t);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()==200)
                {
                   // Log.d("notifcation_donor","success");
                    sendnotificationcharity();

                }
                else{
                   // Log.d("notifcation","fail");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void setdata() {
        item_name.setText(data_bank.current_donation.getItem_name());
        item_cat.setText(data_bank.current_donation.getItem_category());
        donor_email.setText(data_bank.current_donation.getDonor_email());
        ch_email.setText(data_bank.current_donation.getCharity_email());
    }

    private void initalization() {
        item_cat=findViewById(R.id.pick_item_category);
        item_name=findViewById(R.id.pick_item_name);
        donor_email=findViewById(R.id.pick_donor_email);
        ch_email = findViewById(R.id.pick_charity_email);
        return_home=findViewById(R.id.rtn_donation);
        apIinterface = Retroclient.retroinit();

        return_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(picked_up_donations.this, home.class);
                startActivity(i);
                finish();            }
        });

    }
    private void delete_currentdonation(){
        donation_model d = new donation_model();
        d.setDonor_email(data_bank.current_donation.getDonor_email());
        d.setMem_email(data_bank.curUser.getEmail());
        d.setCharity_email(data_bank.current_donation.getCharity_email());

        Call<String> c = apIinterface.delete_current_donation(d);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                data_bank.flag_mem_category=0;
                SharedPreferences sf = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor ed;
                ed= sf.edit();
                ed.putString("current_donation_flag","0");
                ed.commit();
               // Log.d("delete",response.code()+"");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

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