package com.infotech.wedonate.ui.home_module.charity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.ui.home_module.donor.donate;
import com.infotech.wedonate.ui.home_module.home;
import com.infotech.wedonate.ui.home_module.setup_profile;
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class charity_setfragment_activity extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbar_text;
    Drawable dr;
    FragmentTransaction ft;
    FragmentManager fm;
    APIinterface apIinterface;
    data_model user;
    String email;
    SharedPreferences sf;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_request_donation);

        initialization();
        setfragment();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(charity_setfragment_activity.this, home.class);
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
        if(data_bank.curUser.getAddress()==null || data_bank.curUser.getAddress().equals("nodata")) {
            isprofilecomplete();
        }
        else{
            if(data_bank.flag_charity_category==0){
                ft.replace(R.id.request_donation_frm, new generate_request());
            }
            else if(data_bank.flag_charity_category==1){
                ft.replace(R.id.request_donation_frm, new charity_donation_list());
            }
            else if(data_bank.flag_charity_category==2){
                ft.replace(R.id.request_donation_frm, new charity_member_lst());
            } else if (data_bank.flag_charity_category == 3) {
                ft.replace(R.id.request_donation_frm, new charity_donation_list());
            }
            ft.commit();
        }


    }

  void isprofilecomplete() {
      email = data_bank.curUser.getEmail();
        user.setEmail(email);
        user.setUsertype("charity");
        Call<data_model> c = apIinterface.check_profile(user);
//        Log.d("check",data_bank.curUser.address);
        c.enqueue(new Callback<data_model>() {
            @Override
            public void onResponse(Call<data_model> call, Response<data_model> response) {
                if (response.body().getCode()==400) {
                    ft.replace(R.id.request_donation_frm, new setup_profile());
                    ft.commit();

                } else if(response.body().getCode()==200){

                    if(data_bank.flag_charity_category==0){
                        ft.replace(R.id.request_donation_frm, new generate_request());
                    }
                    else if(data_bank.flag_charity_category==1){
                        ft.replace(R.id.request_donation_frm, new charity_donation_list());
                    }
                    else if(data_bank.flag_charity_category==2){
                        ft.replace(R.id.request_donation_frm, new charity_member_lst());
                    }
                    data_bank.curUser.setAddress(response.body().getAddress());
                    sf = getSharedPreferences("Login", MODE_PRIVATE);
                    ed = sf.edit();

                    ed.putString("address",response.body().getAddress());
                    ft.commit();

                }
            }
            @Override
            public void onFailure(Call<data_model> call, Throwable t) {
                Toast.makeText(charity_setfragment_activity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}