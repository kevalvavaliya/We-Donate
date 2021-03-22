package com.infotech.wedonate.ui.login_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.infotech.wedonate.R;
import com.infotech.wedonate.ui.signup_module.otp_verify_screen;

public class update_password extends AppCompatActivity implements View.OnClickListener {

    EditText forgot_pass,forgot_cpass;
    TextView errormsg1;
    Button next_btn;
    String email,usertype,activity,pass,cpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        forgot_pass=findViewById(R.id.forgot_pass_pass);
        forgot_cpass=findViewById(R.id.forgot_pass_cpass);
        errormsg1=findViewById(R.id.errormsg1);
        next_btn=findViewById(R.id.forgot_updatepass_next);

        errormsg1.setVisibility(View.INVISIBLE);
        forgot_pass.setBackgroundResource(R.drawable.forgot_edittext_default);
        forgot_cpass.setBackgroundResource(R.drawable.forgot_edittext_default);

        next_btn.setOnClickListener(this);

        Intent intent = getIntent();
        email= intent.getStringExtra("email");
        usertype=intent.getStringExtra("usertype");
        activity="forgotpass";
    }

    @Override
    public void onClick(View v) {
        pass = forgot_pass.getText().toString().trim();
        cpass=forgot_cpass.getText().toString().trim();

        if(pass.length()!=0 && cpass.length()!=0)
        {
            if(pass.equals(cpass))
            {
                Intent i= new Intent(this, otp_verify_screen.class);
                i.putExtra("email",email);
                i.putExtra("usertype",usertype);
                i.putExtra("pass",pass);
                i.putExtra("activity",activity);
                startActivity(i);
                finish();
            }
            else{
                errormsg1.setText("Password do not match");
                forgot_cpass.setBackgroundResource(R.drawable.forgot_edittext);
                errormsg1.setVisibility(View.VISIBLE);
            }
        }
        else {
            Toast.makeText(this,"Enter all credentials",Toast.LENGTH_SHORT).show();
        }
    }
}