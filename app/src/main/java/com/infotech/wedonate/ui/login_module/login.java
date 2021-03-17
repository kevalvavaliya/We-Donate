package com.infotech.wedonate.ui.login_module;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.infotech.wedonate.R;

public class login extends AppCompatActivity {

    ImageView login_gif;
    Spinner login_user_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_gif=findViewById(R.id.login_g);
        login_user_spinner=findViewById(R.id.login_user_spinner);

        Glide.with(this).load(R.drawable.login).into(login_gif);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.user, R.layout.spinner_layout);
        login_user_spinner.setAdapter(adapter);

    }
}