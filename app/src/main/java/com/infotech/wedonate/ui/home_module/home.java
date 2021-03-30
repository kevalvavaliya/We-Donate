package com.infotech.wedonate.ui.home_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

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
        ft.replace(R.id.home_frame,new donor_fragment());
        ft.commit();
        if(! data_bank.curUser.getEmail().isEmpty())
            Log.d("user", data_bank.curUser.getName());
    }
}