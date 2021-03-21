package com.infotech.wedonate.ui.login_module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.infotech.wedonate.R;

public class forgotpass extends AppCompatActivity {


    FragmentManager fm;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);


        fm = getSupportFragmentManager();
        ft=fm.beginTransaction();
        ft.replace(R.id.forgot_pass_frm,new forgotpass_find_accnt());
        ft.commit();

    }

}