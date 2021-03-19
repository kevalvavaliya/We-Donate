package com.infotech.wedonate.ui.login_module;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infotech.wedonate.R;

public class login extends AppCompatActivity {

    ImageView login_gif;
    Spinner login_user_spinner;
    EditText login_email,login_pass;
    Button login_btn;
    TextView register,forgot_pass;
    String email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initalization();



    }
    void initalization(){
        login_gif=findViewById(R.id.login_g);
        login_email=findViewById(R.id.login_email);
        login_pass=findViewById(R.id.login_password);
        login_btn=findViewById(R.id.login_btn);
        register=findViewById(R.id.register_btn);
        forgot_pass=findViewById(R.id.forgot_pass);
        login_user_spinner=findViewById(R.id.login_user_spinner);
        Glide.with(this).load(R.drawable.login).into(login_gif);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.user, R.layout.spinner_layout);
        login_user_spinner.setAdapter(adapter);


    }
}