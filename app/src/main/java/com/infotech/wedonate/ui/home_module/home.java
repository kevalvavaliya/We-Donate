package com.infotech.wedonate.ui.home_module;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.ui.home_module.charity.charity_fragment;
import com.infotech.wedonate.ui.home_module.donor.donor_fragment;

public class home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    FragmentManager fm;
    FragmentTransaction ft;
    BottomNavigationView btm_nav;
    SharedPreferences sf;
    data_model CurentUser;
    String email,usertype,name,mobile,address;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //checkuserset();
        //checkTimeLeft();
        btm_nav = findViewById(R.id.btm_nav);
        btm_nav.setOnNavigationItemSelectedListener(this);
        setfragment();
        //startService(new Intent(home.this, TimerService.class));
    }

   /* private void checkuserset() {
        if(data_bank.curUser.getUsertype()==null){
            CurentUser = new data_model();
            sf = getSharedPreferences("Login", MODE_PRIVATE);
            usertype= sf.getString("usertype","nodata");
            email=sf.getString("useremail","nodata");
            name=sf.getString("username","nodata");
            mobile=sf.getString("mobile","nodata");
            address=sf.getString("address","nodata");
            CurentUser.setEmail(email);
            CurentUser.setUsertype(usertype);
            CurentUser.setName(name);
            CurentUser.setMobile(mobile);
            CurentUser.setAddress(address);
            data_bank.curUser=CurentUser;
        }

    }*/

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
//    private void checkTimeLeft() {
//
//        for (Long l : data_bank.cur_req_end_time) {
//            long reqest_time = l + data_bank.finish_time;
//            long cure = System.currentTimeMillis() /1000;
//            data_bank.left_time.add(reqest_time - cure);
//        }
//    }
}
