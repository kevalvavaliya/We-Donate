package com.infotech.wedonate.ui.signup_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.infotech.wedonate.R;
import com.infotech.wedonate.user_selector;
import com.infotech.wedonate.util.passtoogler;

public class signup_member extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    CheckBox passtoggle;
    Toolbar toolbar;
    EditText passmember;
    Drawable dr;
    Button signup_mem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_member);
        passtoggle=findViewById(R.id.pass_toggle);
        passmember=findViewById(R.id.pass_member);
        toolbar = findViewById(R.id.toolbar);
        signup_mem= findViewById(R.id.signup_btn_mem);

   /******************Temprorary ************************/
        signup_mem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(signup_member.this,otp_verify_screen.class);
                startActivity(intent);
                finish();
            }
        });
     /*******************************************************/

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
                Intent intent = new Intent(signup_member.this, user_selector.class);
                startActivity(intent);
                //  Log.d("keval","press");
            }
        });
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        passtoogler p = new passtoogler();
        p.tooglevisiblity(isChecked,passmember);
    }
}