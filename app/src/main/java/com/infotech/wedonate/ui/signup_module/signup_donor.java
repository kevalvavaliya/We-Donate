package com.infotech.wedonate.ui.signup_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.API.MyRetrofit;
import com.infotech.wedonate.API.signup_response;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.signup_data_model;
import com.infotech.wedonate.user_selector;
import com.infotech.wedonate.util.Retroclient;
import com.infotech.wedonate.util.passtoogler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class signup_donor extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    CheckBox passtoggle;
    Toolbar toolbar;
    EditText d_pass, d_name, d_email, d_mobile;
    Drawable dr;
    Button signup;
    String name, email, pass, mobile, usertype;
    signup_data_model donor_user;
    APIinterface apIinterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_donor);

        initalization();
        apIinterface = Retroclient.retroinit();

        passtoggle.setOnCheckedChangeListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(dr);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup_donor.this, user_selector.class);
                startActivity(intent);
                //  Log.d("keval","press");
            }
        });

        signup.setOnClickListener(this);
    }

    void initalization() {
        passtoggle = findViewById(R.id.pass_toggle);
        d_pass = findViewById(R.id.d_pass);
        d_name = findViewById(R.id.d_name);
        d_email = findViewById(R.id.d_email);
        d_mobile = findViewById(R.id.d_mobile);
        signup = findViewById(R.id.signup_btn_donor);
        toolbar = findViewById(R.id.toolbar);
        donor_user = new signup_data_model();

        dr = getResources().getDrawable(R.drawable.back_arrow);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        passtoogler p = new passtoogler();
        p.tooglevisiblity(isChecked, d_pass);
    }

    @Override
    public void onClick(View v) {

        usertype = "donor";
        name = d_name.getText().toString();
        email = d_email.getText().toString();
        mobile = d_mobile.getText().toString();
        pass = d_pass.getText().toString();
        if (name.length() == 0 || email.length() == 0 || mobile.length() == 0 || pass.length() == 0) {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_LONG).show();
        } else {
            donor_user.setName(name);
            donor_user.setEmail(email);
            donor_user.setMobile(mobile);
            donor_user.setPass(pass);
            donor_user.setUsertype(usertype);

            Call<signup_response> c = apIinterface.signup_donor(donor_user);
            c.enqueue(new Callback<signup_response>() {
                @Override
                public void onResponse(Call<signup_response> call, Response<signup_response> response) {

                    if(response.code()==400)
                    {
                        Toast.makeText(signup_donor.this,"signup failed",Toast.LENGTH_LONG).show();
                    }
                    else if(response.code()==200)
                        Toast.makeText(signup_donor.this,"signup success",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<signup_response> call, Throwable t) {
                   // Log.d("api",t.toString());
                    Toast.makeText(signup_donor.this,"Failure",Toast.LENGTH_LONG).show();

                }
            });
        }
    }

}