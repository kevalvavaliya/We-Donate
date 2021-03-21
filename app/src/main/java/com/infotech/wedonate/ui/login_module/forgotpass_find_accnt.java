package com.infotech.wedonate.ui.login_module;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forgotpass_find_accnt extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText forgot_email;
    TextView errormsg;
    String email,usertype;
    Button next_find_accnt;
    Spinner choose_user;
    data_model user;
    APIinterface apIinterface;
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_forgotpass_find_accnt, container, false);

        forgot_email=v.findViewById(R.id.forgot_pass_email);
        errormsg=v.findViewById(R.id.errormsg);
        next_find_accnt=v.findViewById(R.id.forgot_pass_next);
        choose_user=v.findViewById(R.id.choose_user);
        user= new data_model();
        apIinterface= Retroclient.retroinit();

        errormsg.setVisibility(View.INVISIBLE);
        forgot_email.setBackgroundResource(R.drawable.forgot_edittext_default);

        next_find_accnt.setOnClickListener(this);
        choose_user.setOnItemSelectedListener(this);

        return v;



    }

    @Override
    public void onClick(View v) {

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
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        forgot_otp_verification fragment = new forgot_otp_verification();
                        ft.replace(R.id.forgot_pass_frm, fragment);
                        ft.commit();
                    }
                    else{
                        forgot_email.setBackgroundResource(R.drawable.forgot_edittext);
                        errormsg.setText("No user exists");
                        errormsg.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<response> call, Throwable t) {
                    Toast.makeText(getActivity(),"Server failure",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
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
        Toast.makeText(getActivity(),"Choose yourself",Toast.LENGTH_LONG).show();
    }
}