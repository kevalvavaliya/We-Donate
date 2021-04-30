package com.infotech.wedonate.ui.home_module.member;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.ui.home_module.donor.donor_setfragment_activity;
import com.infotech.wedonate.ui.home_module.home;

public class member_setfragement_activity extends AppCompatActivity {

    FragmentTransaction ft;
    FragmentManager fm;
    androidx.appcompat.widget.Toolbar toolbar;
    TextView toolbar_text;
    Drawable dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_setfragement_activity);

        toolbar = findViewById(R.id.toolbar);
        toolbar_text = findViewById(R.id.toolbar_text);
        toolbar_text.setText("Home");
        dr = getResources().getDrawable(R.drawable.back_arrow);
        toolbar.setNavigationIcon(dr);
        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(member_setfragement_activity.this, home.class);
                startActivity(intent);
            }
        });
        setfragment();
    }

    private void setfragment() {
        if(data_bank.flag_mem_category==0){
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction().replace(R.id.member_donation_frm,new no_current_donations());
            ft.commit();
        }

        else if(data_bank.flag_mem_category==1){
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction().replace(R.id.member_donation_frm,new member_ch_donation_list());
            ft.commit();
        }
    }
}