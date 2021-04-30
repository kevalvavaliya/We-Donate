package com.infotech.wedonate.ui.login_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.API.response;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.ui.signup_module.otp_verify_screen;
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forgotpass extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    EditText forgot_email;
    TextView errormsg;
    Button next_find_accnt;
    Spinner choose_user;
    data_model user;
    APIinterface apIinterface;
    String email,usertype;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        forgot_email=findViewById(R.id.forgot_pass_email);
        errormsg=findViewById(R.id.errormsg);
        next_find_accnt=findViewById(R.id.forgot_pass_next);
        choose_user=findViewById(R.id.choose_user);
        user= new data_model();
        apIinterface= Retroclient.retroinit();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing ... ");

        errormsg.setVisibility(View.INVISIBLE);
        forgot_email.setBackgroundResource(R.drawable.forgot_edittext_default);

        next_find_accnt.setOnClickListener(this);
        choose_user.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        progressDialog.show();
        email=forgot_email.getText().toString().trim();
        if(email.length()!=0){
            user.setUsertype(usertype);
            user.setEmail(email);
            Call<response> c = apIinterface.find_accnt(user);
            c.enqueue(new Callback<response>() {
                @Override
                public void onResponse(Call<response> call, Response<response> response) {
                    if(response.body().getMsg().equalsIgnoreCase("accnt success") || response.body().getCode()==200)
                    {
                        progressDialog.dismiss();
                        Intent intent = new Intent(forgotpass.this, update_password.class);
                        intent.putExtra("email",email);
                        intent.putExtra("usertype",usertype);
                        startActivity(intent);

                    }
                    else{
                        progressDialog.dismiss();
                        forgot_email.setBackgroundResource(R.drawable.forgot_edittext);
                        errormsg.setText("No user exists");
                        errormsg.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onFailure(Call<response> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(forgotpass.this,"Server failure",Toast.LENGTH_SHORT).show();

                }
            });
        }
        else{
            progressDialog.dismiss();
            forgot_email.setBackgroundResource(R.drawable.forgot_edittext);
            errormsg.setText("Enter email address");
            errormsg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0)
            usertype="donor";
        else if(position==1)
            usertype="member";
        else if(position==2)
            usertype="charity";
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(forgotpass.this,"Choose yourself",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i  = new Intent(this,login.class);
        startActivity(i);
        finish();
    }
}