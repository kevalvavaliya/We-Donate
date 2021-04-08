package com.infotech.wedonate.ui.home_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.ui.home_module.charity.charity_fragment;
import com.infotech.wedonate.ui.home_module.donor.donor_fragment;

public class home extends AppCompatActivity {
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setfragment();


    }
    void setfragment(){
        fm = getSupportFragmentManager();
        ft=fm.beginTransaction();

        Intent i = getIntent();
        String frg= i.getStringExtra("fragment");
        String address=i.getStringExtra("addaddress");

        if(frg != null)
        {
            if(frg.equals("profile")){
                ft.replace(R.id.home_frame,new profile());
                ft.commit();
            }
        }
        else if(data_bank.curUser.getUsertype().equals("donor")) {
            ft.replace(R.id.home_frame, new donor_fragment());
            ft.commit();
        }
        else if(data_bank.curUser.getUsertype().equals("charity")){
            ft.replace(R.id.home_frame, new charity_fragment());
            ft.commit();
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}