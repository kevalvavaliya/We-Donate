package com.infotech.wedonate.ui.home_module;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profile extends Fragment implements View.OnClickListener {
    View view;
    Button add_address,save;
    EditText address;
    String addr;
    data_model user;
    APIinterface apIinterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initalization();

        add_address.setOnClickListener(this);
        return view;
    }
    void initalization(){
        add_address= view.findViewById(R.id.add_address);
        address= view.findViewById(R.id.address);
        save= view.findViewById(R.id.save);
        user = new data_model();
        address.setEnabled(false);
        save.setVisibility(View.INVISIBLE);
        apIinterface = Retroclient.retroinit();

    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.add_address) {
            address.setEnabled(true);
            address.requestFocus();
            save.setVisibility(View.VISIBLE);
        }
        else if(v.getId()==R.id.save){
            addr= address.getText().toString();
            save.setVisibility(View.INVISIBLE);
            address.setEnabled(false);
            if(addr.length()!=0)
            {
                user.setAddress(addr);
                String usertype= data_bank.curUser.getUsertype();
                String email= data_bank.curUser.getEmail();
                user.setEmail(email);
                user.setUsertype(usertype);

                Call<String> c = apIinterface.setaddress(user);
                c.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().equals("true")){
                            Toast.makeText(getActivity(),"Address updated",Toast.LENGTH_SHORT).show();
                            
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
            else{
                Toast.makeText(getActivity(),"Enter address", Toast.LENGTH_SHORT).show();
            }

        }
    }

}