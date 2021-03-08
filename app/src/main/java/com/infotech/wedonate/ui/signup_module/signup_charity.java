package com.infotech.wedonate.ui.signup_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.infotech.wedonate.R;
import com.infotech.wedonate.user_selector;
import com.infotech.wedonate.util.passtoogler;

public class signup_charity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


    CheckBox passtoggle;
    Toolbar toolbar;
    EditText passcharity;
    Drawable dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_charity);

        passtoggle=findViewById(R.id.pass_toggle);
        passcharity =findViewById(R.id.pass_charity);
        toolbar = findViewById(R.id.toolbar);
        dr=getResources().getDrawable(R.drawable.back_arrow);

        passtoggle.setOnCheckedChangeListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(dr);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup_charity.this, user_selector.class);
                startActivity(intent);
                //  Log.d("keval","press");
            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        passtoogler p = new passtoogler();
        p.tooglevisiblity(isChecked, passcharity);
    }
}