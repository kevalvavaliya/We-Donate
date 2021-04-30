package com.infotech.wedonate.ui.login_module;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.ui.home_module.home;
import com.infotech.wedonate.user_selector;
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    ImageView login_gif;
    Spinner login_user_spinner;
    EditText login_email, login_pass;
    Button login_btn;
    TextView register, forgot_pass;
    String email, pass, usertype;
    data_model user;
    APIinterface apIinterface;
    SharedPreferences.Editor ed;
    SharedPreferences sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initalization();
        login_btn.setOnClickListener(this);
        forgot_pass.setOnClickListener(this);
        register.setOnClickListener(this);
        login_user_spinner.setOnItemSelectedListener(this);


    }

    void initalization() {
        login_gif = findViewById(R.id.login_g);
        login_email = findViewById(R.id.login_email);
        login_pass = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        register = findViewById(R.id.register_btn);
        forgot_pass = findViewById(R.id.forgot_pass);
        login_user_spinner = findViewById(R.id.login_user_spinner);
        Glide.with(this).load(R.drawable.login).into(login_gif);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.user, R.layout.spinner_layout);
        login_user_spinner.setAdapter(adapter);
        user = new data_model();
        apIinterface = Retroclient.retroinit();


        sf = getSharedPreferences("Login", MODE_PRIVATE);
        data_bank.curUser = new data_model();

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn) {
            Dologin();
        }
        if (v.getId() == R.id.register_btn) {
            Intent i = new Intent(this, user_selector.class);
            startActivity(i);
        }
        if (v.getId() == R.id.forgot_pass) {
            Intent i = new Intent(this, forgotpass.class);
            startActivity(i);

        }
    }

    void Dologin() {
        email = login_email.getText().toString().trim();
        pass = login_pass.getText().toString().trim();

        if (email.length() != 0 && pass.length() != 0) {
            user.setEmail(email);
            user.setPass(pass);
            user.setUsertype(usertype);


            Call<data_model> c = apIinterface.login(user);
            c.enqueue(new Callback<data_model>() {
                @Override
                public void onResponse(Call<data_model> call, Response<data_model> response) {
                   // Log.d("mylog", response.body().getCode() + "");
                    if (response.body().getCode() == 200) {
                        ed = sf.edit();
                        ed.putString("useremail", email);
                        ed.putString("usertype", usertype);

                        if (response.body().getUsertype().equalsIgnoreCase("donor")) {
                            ed.putString("username", response.body().getName());
                            ed.putString("mobile",response.body().getMobile());
                            data_bank.curUser.setEmail(response.body().getEmail());
                            data_bank.curUser.setName(response.body().getName());
                            data_bank.curUser.setUsertype(response.body().getUsertype());
                            data_bank.curUser.setMobile(response.body().getMobile());

                            Intent intent = new Intent(login.this, home.class);
                            startActivity(intent);
                            finish();
                        } else if (response.body().getUsertype().equalsIgnoreCase("member")) {
                            ed.putString("username", response.body().getName());
                            ed.putString("mobile",response.body().getMobile());
                            ed.putString("chemail",response.body().getCharityemail());
                            data_bank.curUser.setEmail(response.body().getEmail());
                            data_bank.curUser.setName(response.body().getName());
                            data_bank.curUser.setUsertype(response.body().getUsertype());
                            data_bank.curUser.setMobile(response.body().getMobile());
                            data_bank.curUser.setCharityemail(response.body().getCharityemail());

                            Intent intent = new Intent(login.this, home.class);
                            startActivity(intent);
                            finish();

                        } else if (response.body().getUsertype().equalsIgnoreCase("charity")) {
                            ed.putString("username", response.body().getName());
                            ed.putString("mobile",response.body().getMobile());
                            data_bank.curUser.setEmail(response.body().getEmail());
                            data_bank.curUser.setName(response.body().getName());
                            data_bank.curUser.setUsertype(response.body().getUsertype());
                            data_bank.curUser.setMobile(response.body().getMobile());

                            Intent intent = new Intent(login.this, home.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(login.this, "login fail", Toast.LENGTH_SHORT).show();
                        }
                        ed.commit();
                    } else {
                        Toast.makeText(login.this, "login fail", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<data_model> call, Throwable t) {
                    Toast.makeText(login.this, "server failure", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(this, "Enter credentials", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Log.d("mylog",position+"");
        if (position == 0)
            usertype = "donor";
        else if (position == 1)
            usertype = "member";
        else if (position == 2)
            usertype = "charity";
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Choose yourself", Toast.LENGTH_LONG).show();
    }
}