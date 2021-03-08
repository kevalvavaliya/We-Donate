package com.infotech.wedonate.ui.signup_module;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.infotech.wedonate.R;
import com.infotech.wedonate.util.GenericTextWatcher;

public class otp_verify_screen extends AppCompatActivity {

    EditText otp1,otp2,otp3,otp4;

    Button verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify_screen);

        otp1=findViewById(R.id.ed1);
        otp2=findViewById(R.id.ed2);
        otp3=findViewById(R.id.ed3);
        otp4=findViewById(R.id.ed4);

    EditText[] edit ={otp1,otp2,otp3,otp4};

    otp1.addTextChangedListener(new GenericTextWatcher(otp1,edit));
    otp2.addTextChangedListener(new GenericTextWatcher(otp2,edit));
    otp3.addTextChangedListener(new GenericTextWatcher(otp3,edit));
    otp4.addTextChangedListener(new GenericTextWatcher(otp4,edit));

    }
}