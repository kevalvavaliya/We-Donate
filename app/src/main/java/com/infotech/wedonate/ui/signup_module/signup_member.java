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

public class signup_member extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    CheckBox passtoggle;
    Toolbar toolbar;
    EditText m_pass, m_name, m_email, m_mobile, m_charity_email;
    Drawable dr;
    String name, email, pass, mobile, usertype, charityemail;
    APIinterface apIinterface;
    Button signup_mem;
    data_model member_user;
    ProgressDialog progressDialog;
    TextView toolbar_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_member);

        initalization();


        /******************Temprorary ************************
         signup_mem.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        Intent intent= new Intent(signup_member.this,otp_verify_screen.class);
        startActivity(intent);
        finish();
        }
        });
         /*******************************************************/

        dr = getResources().getDrawable(R.drawable.back_arrow);

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

        signup_mem.setOnClickListener(this);
    }

    void initalization() {
        passtoggle = findViewById(R.id.pass_toggle);
        m_name = findViewById(R.id.m_name);
        m_email = findViewById(R.id.m_email);
        m_charity_email = findViewById(R.id.m_charityemail);
        m_mobile = findViewById(R.id.m_mobile);
        m_pass = findViewById(R.id.pass_member);
        toolbar = findViewById(R.id.toolbar);
        toolbar_text= findViewById(R.id.toolbar_text);
        signup_mem = findViewById(R.id.signup_btn_mem);

        member_user = new data_model();

        apIinterface = Retroclient.retroinit();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing...");
        toolbar_text.setText("Signup");

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        passtoogler p = new passtoogler();
        p.tooglevisiblity(isChecked, m_pass);
    }

    @Override
    public void onClick(View v) {
        usertype = "member";
        name = m_name.getText().toString();
        email = m_email.getText().toString();
        pass = m_pass.getText().toString();
        mobile = m_mobile.getText().toString();
        charityemail = m_charity_email.getText().toString();

        if (name.length() == 0 || email.length() == 0 || mobile.length() == 0 || pass.length() == 0 || charityemail.length() == 0) {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            member_user.setName(name);
            member_user.setEmail(email);
            member_user.setPass(pass);
            member_user.setCharityemail(charityemail);
            member_user.setMobile(mobile);
            member_user.setUsertype(usertype);

            Call<response> c = apIinterface.signup_member(member_user);
            c.enqueue(new Callback<response>() {
                @Override
                public void onResponse(Call<response> call, Response<response> response) {
                    if (response.body().getCode() == 400 || response.body().getCode() == 401){
                        progressDialog.dismiss();
                        if(response.body().getMsg().trim().equalsIgnoreCase("user already exists"))
                            Toast.makeText(signup_member.this, "User already exists", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(signup_member.this,"Signup Failed",Toast.LENGTH_LONG).show();
                    } else if(response.body().getCode()==200){
                        Intent i = new Intent(signup_member.this,otp_verify_screen.class);
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
                    Toast.makeText(signup_member.this, "Failure", Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}