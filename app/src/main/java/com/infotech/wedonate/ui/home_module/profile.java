package com.infotech.wedonate.ui.home_module;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.ui.info;
import com.infotech.wedonate.ui.login_module.forgotpass;
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class profile extends Fragment implements View.OnClickListener {
    View view;
    Button add_address,save;
    EditText address;
    TextView pr_username,pr_email,pr_mobile,change_pass,signout;
    String addr;
    data_model user;
    APIinterface apIinterface;
    SharedPreferences sf;
    SharedPreferences.Editor ed;
    androidx.appcompat.widget.Toolbar toolbar_dr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initalization();

        add_address.setOnClickListener(this);
        save.setOnClickListener(this);
        change_pass.setOnClickListener(this);
        signout.setOnClickListener(this);
        return view;
    }
    void initalization(){
        add_address= view.findViewById(R.id.add_address);
        address= view.findViewById(R.id.address);
        save= view.findViewById(R.id.save);
        pr_email=view.findViewById(R.id.pr_email);
        pr_mobile= view.findViewById(R.id.pr_mobile);
        pr_username= view.findViewById(R.id.pr_username);
        change_pass= view.findViewById(R.id.chng_pass);
        user = new data_model();
        signout=view.findViewById(R.id.signout);
        address.setEnabled(false);
        save.setVisibility(View.GONE);
        apIinterface = Retroclient.retroinit();
        sf = this.getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        ed = sf.edit();
        toolbar_dr = getActivity().findViewById(R.id.toolbar_dr);

        toolbar_dr.setVisibility(View.GONE);
        if(data_bank.curUser.getAddress()!= null)
        {
            address.setText(data_bank.curUser.getAddress());

        }
        if(data_bank.curUser.getName()!=null)
        {
            pr_username.setText(data_bank.curUser.getName());
            pr_email.setText(data_bank.curUser.getEmail());
            pr_mobile.setText("+91 "+data_bank.curUser.getMobile());
        }

    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.add_address) {
            address.setEnabled(true);
            address.requestFocus();
            save.setVisibility(View.VISIBLE);
        }
        else if(v.getId()==R.id.chng_pass){
            Intent i =  new Intent(getActivity(), forgotpass.class);
            startActivity(i);
            getActivity().finish();
        }
        else if(v.getId()==R.id.signout){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Are you sure,You wanted to sign out");
                    alertDialogBuilder.setPositiveButton("yes",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                        data_bank.curUser = null;
                                        SharedPreferences sf = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
                                        SharedPreferences.Editor ed = sf.edit();
                                        ed.clear();
                                        ed.commit();
                                Intent i = new Intent(getActivity(), info.class);
                                startActivity(i);
                                getActivity().finishAffinity();
                                }
                            });

            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else if(v.getId()==R.id.save){
            addr= address.getText().toString();
            save.setVisibility(View.INVISIBLE);
            address.setEnabled(false);
            if(addr.length()!=0)
            {
                user.setAddress(addr);
                ed.putString("address",addr);
                ed.commit();
                data_bank.curUser.setAddress(addr);
                String usertype= data_bank.curUser.getUsertype();
                String email= data_bank.curUser.getEmail();
                user.setEmail(email);
                user.setUsertype(usertype);

                Call<String> c = apIinterface.setaddress(user);
                c.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().equals("true")){

                            address.setText(addr);
                          Toast.makeText(getActivity(),"Address updated",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getActivity(),"Address update fail",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                Toast.makeText(getActivity(),"Enter address", Toast.LENGTH_SHORT).show();
            }

        }
    }

}