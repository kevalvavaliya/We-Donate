package com.infotech.wedonate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.infotech.wedonate.ui.signup_module.signup_charity;
import com.infotech.wedonate.ui.signup_module.signup_donor;
import com.infotech.wedonate.ui.signup_module.signup_member;

public class user_selector extends AppCompatActivity implements View.OnClickListener {

    Button next_bt;
    RadioButton member_r, donor_r, charity_r;
    LinearLayout member_l, donor_l, charity_l;
    int flag =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selector);

        next_bt = findViewById(R.id.next_btn);
        member_r = findViewById(R.id.member_radio_btn);
        donor_r = findViewById(R.id.donor_radio_btn);
        charity_r = findViewById(R.id.charity_radio_btn);

        member_l = findViewById(R.id.member_btn);
        donor_l = findViewById(R.id.donor_btn);
        charity_l = findViewById(R.id.charity_btn);

        next_bt.setBackgroundColor(Color.rgb(230, 59, 81));

        member_l.setOnClickListener(this);
        donor_l.setOnClickListener(this);
        charity_l.setOnClickListener(this);

        member_r.setOnClickListener(this);
        donor_r.setOnClickListener(this);
        charity_r.setOnClickListener(this);

        next_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.member_btn || id == R.id.member_radio_btn) {
            member_r.setChecked(true);
            donor_r.setChecked(false);
            charity_r.setChecked(false);
            flag=0;
        }
        if (id == R.id.donor_btn || id == R.id.donor_radio_btn) {
            member_r.setChecked(false);
            donor_r.setChecked(true);
            charity_r.setChecked(false);
            flag=1;
        }

        if (id == R.id.charity_btn || id == R.id.charity_radio_btn) {
            member_r.setChecked(false);
            donor_r.setChecked(false);
            charity_r.setChecked(true);
            flag=2;
        }
        if(id==R.id.next_btn) {
            Intent intent;
            switch (flag) {

                case 0:
                    intent = new Intent(this, signup_member.class);
                    startActivity(intent);
                    finish();
                    break;
                case 1:
                    intent = new Intent(this, signup_donor.class);
                    startActivity(intent);
                    finish();
                    break;

                case 2:
                    intent = new Intent(this, signup_charity.class);
                    startActivity(intent);
                    finish();
                    break;
                case -1:
                    Toast.makeText(this, "please choose yourself", Toast.LENGTH_LONG).show();
            }
        }
    }
}