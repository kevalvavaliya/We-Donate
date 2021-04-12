package com.infotech.wedonate.ui.home_module;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.ui.home_module.charity.charity_fragment;
import com.infotech.wedonate.ui.home_module.donor.donor_fragment;

public class home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    FragmentManager fm;
    FragmentTransaction ft;
    BottomNavigationView btm_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btm_nav = findViewById(R.id.btm_nav);
        btm_nav.setOnNavigationItemSelectedListener(this);
        setfragment();



    }
    void setfragment(){
        fm = getSupportFragmentManager();
        ft=fm.beginTransaction();

        Intent i = getIntent();
        String frg= i.getStringExtra("fragment");

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fm = getSupportFragmentManager();
        ft=fm.beginTransaction();

        int id = item.getItemId();
        if(id==R.id.home){
            if(data_bank.curUser.getUsertype().equals("donor")) {
                ft.replace(R.id.home_frame, new donor_fragment());
                ft.commit();
            }
            else if(data_bank.curUser.getUsertype().equals("charity")){
                ft.replace(R.id.home_frame, new charity_fragment());
                ft.commit();
            }


        }
        else if(id ==R.id.profile)
        {
            ft.replace(R.id.home_frame, new profile());
            ft.commit();
        }
        return false;
    }
}