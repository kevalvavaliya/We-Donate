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
import android.widget.TextView;
import android.widget.Toast;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.API.response;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_model;
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
    data_model charity_user;
    APIinterface apIinterface;
    String name, email, pass, mobile, usertype;
    ProgressDialog progressDialog;
    TextView toolbar_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_charity);

        initalization();

        passtoggle.setOnCheckedChangeListener(this);
        setSupportActionBar(toolbar);
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
        toolbar_text= findViewById(R.id.toolbar_text);
        charity_user = new data_model();

        dr = getResources().getDrawable(R.drawable.back_arrow);

        apIinterface = Retroclient.retroinit();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
        toolbar_text.setText("Signup");
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

            Call<response> c = apIinterface.signup_charity(charity_user);
            c.enqueue(new Callback<response>() {
                @Override
                public void onResponse(Call<response> call, Response<response> response) {
                    if (response.body().getCode() == 400 || response.body().getCode() == 401){
                        progressDialog.dismiss();
                        if(response.body().getMsg().trim().equalsIgnoreCase("user already exists"))
                            Toast.makeText(signup_charity.this, "User already exists", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(signup_charity.this,"Signup Failed",Toast.LENGTH_LONG).show();
                    } else if(response.body().getCode()==200){
                        Intent i = new Intent(signup_charity.this,otp_verify_screen.class);
                        i.putExtra("email",email);
                        i.putExtra("usertype",usertype);
                        i.putExtra("activity", "signup");
                        startActivity(i);
                        finish();
                    }

                }

                @Override
                public void onFailure(Call<response> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(signup_charity.this, "server Failure", Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}