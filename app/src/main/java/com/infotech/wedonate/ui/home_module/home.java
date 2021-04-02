package com.infotech.wedonate.ui.home_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;

public class home extends AppCompatActivity {
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fm = getSupportFragmentManager();
        ft=fm.beginTransaction();

        if(data_bank.curUser.getUsertype().equals("donor")) {
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