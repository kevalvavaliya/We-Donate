package com.infotech.wedonate.ui.signup_module;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.API.response;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.ui.login_module.login;
import com.infotech.wedonate.util.GenericTextWatcher;
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class otp_verify_screen extends AppCompatActivity {

    EditText otp1,otp2,otp3,otp4;

    Button otp_verify;
    EditText[] edit;
    Intent intent;
    String email,usertype,otp,activity,pass;
    data_model user;
    APIinterface apIinterface;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify_screen);

        initialization();
        cursormovable();
        intent = getIntent();
        email=intent.getStringExtra("email");
        usertype=intent.getStringExtra("usertype");
        activity=intent.getStringExtra("activity");

        if(activity.equals("forgotpass"))
        {
            pass=intent.getStringExtra("pass");
            user.setPass(pass);
        }


    }
    void initialization(){
        otp1=findViewById(R.id.ed1);
        otp2=findViewById(R.id.ed2);
        otp3=findViewById(R.id.ed3);
        otp4=findViewById(R.id.ed4);
        edit =new EditText[]{otp1,otp2,otp3,otp4};
        apIinterface = Retroclient.retroinit();
        user = new data_model();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Verifying...");
    }
    void cursormovable(){
        otp1.addTextChangedListener(new GenericTextWatcher(otp1,edit));
        otp2.addTextChangedListener(new GenericTextWatcher(otp2,edit));
        otp3.addTextChangedListener(new GenericTextWatcher(otp3,edit));
        otp4.addTextChangedListener(new GenericTextWatcher(otp4,edit));

    }

    public void verify_otp(View view) {
        if(otp1.getText().length()!=1 || otp2.getText().length()!=1 || otp3.getText().length()!=1 || otp4.getText().length()!=1 ){
            Toast.makeText(this,"Enter OTP",Toast.LENGTH_SHORT).show();
        }
        else{
            otp=otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();
            user.setOtp(otp);
            user.setUsertype(usertype);
            user.setEmail(email);
            user.setActivity(activity);
            progressDialog.show();
            Call<response> c =apIinterface.otp_verification(user);
            c.enqueue(new Callback<response>() {
                @Override
                public void onResponse(Call<response> call, Response<response> response) {
                    Log.d("mylog",response.body().getMsg());
                    if (response.body().getCode() == 400) {
                        progressDialog.dismiss();
                        Toast.makeText(otp_verify_screen.this, "verification failed", Toast.LENGTH_LONG).show();
                    } else if (response.body().getCode() == 200) {
                       // Toast.makeText(otp_verify_screen.this, "verification success", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        Intent i = new Intent(otp_verify_screen.this, login.class);
                        startActivity(i);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<response> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(otp_verify_screen.this, "Server Failure", Toast.LENGTH_LONG).show();
                }
            });


        }
    }
}