package com.infotech.wedonate.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.infotech.wedonate.R;

public class splash extends AppCompatActivity implements  Runnable{

    Handler h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        h = new Handler();
        h.postDelayed(this,3000);

    }

    @Override
    public void run() {
        Intent intent = new Intent(this,info.class);
        startActivity(intent);
        finish();
    }
}