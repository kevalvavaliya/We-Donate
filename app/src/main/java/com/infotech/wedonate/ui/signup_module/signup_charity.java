package com.infotech.wedonate.ui.signup_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.API.signup_response;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.signup_data_model;
import com.infotech.wedonate.user_selector;
import com.infotech.wedonate.util.Retroclient;
import com.infotech.wedonate.util.passtoogler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup_charity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {


    CheckBox passtoggle;
    Toolbar toolbar;
    EditText c_pass, c_name, c_email, c_mobile;
    Drawable dr;
    Button signup_charity_btn;
    signup_data_model charity_user;
    APIinterface apIinterface;
    String name, email, pass, mobile, usertype;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_charity);

        initalization();

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

        signup_charity_btn.setOnClickListener(this);
    }

    void initalization() {
        passtoggle = findViewById(R.id.pass_toggle);
        c_pass = findViewById(R.id.c_pass);
        c_name = findViewById(R.id.c_name);
        c_email = findViewById(R.id.c_email);
        c_mobile = findViewById(R.id.c_mobile);
        signup_charity_btn = findViewById(R.id.signup_btn_chr);
        toolbar = findViewById(R.id.toolbar);
        charity_user = new signup_data_model();

        dr = getResources().getDrawable(R.drawable.back_arrow);

        apIinterface = Retroclient.retroinit();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        passtoogler p = new passtoogler();
        p.tooglevisiblity(isChecked, c_pass);
    }

    @Override
    public void onClick(View v) {

        usertype = "charity";
        name = c_name.getText().toString();
        email = c_email.getText().toString();
        mobile = c_mobile.getText().toString();
        pass = c_pass.getText().toString();

        if (name.length() == 0 || email.length() == 0 || mobile.length() == 0 || pass.length() == 0) {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            charity_user.setName(name);
            charity_user.setEmail(email);
            charity_user.setMobile(mobile);
            charity_user.setPass(pass);
            charity_user.setUsertype(usertype);

            Call<signup_response> c = apIinterface.signup_charity(charity_user);
            c.enqueue(new Callback<signup_response>() {
                @Override
                public void onResponse(Call<signup_response> call, Response<signup_response> response) {
                    if (response.code() == 400) {
                        progressDialog.dismiss();
                        Toast.makeText(signup_charity.this, "signup failed", Toast.LENGTH_LONG).show();

                    } else if (response.code() == 200) {
                        progressDialog.dismiss();
                        Toast.makeText(signup_charity.this, "signup success", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<signup_response> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(signup_charity.this, "server Failure", Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}