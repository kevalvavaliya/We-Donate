package com.infotech.wedonate.ui.home_module.charity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.ArrayAdapter.createFromResource;

public class generate_request extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    View v;
    Spinner category;
    EditText charity_name,Item_name,Item_desc,charity_mobile;
    Button req_gen;
    String item_name,item_desc,category_value,name,mobile,email,address;
    data_model req_donation;
    APIinterface apIinterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_generate_request, container, false);
        initalization();

        return v;
    }
    void  initalization(){
        category= v.findViewById(R.id.category_req);
        charity_name= v.findViewById(R.id.req_charity_name);
        charity_mobile = v.findViewById(R.id.req_charity_mobile);
        Item_name = v.findViewById(R.id.req_item_name);
        Item_desc = v.findViewById(R.id.req_item_desc);
        req_gen = v.findViewById(R.id.req_gen);
        req_donation = new data_model();
        apIinterface= Retroclient.retroinit();

        ArrayAdapter ad = ArrayAdapter.createFromResource(getActivity(),R.array.category,R.layout.spinner_category_text);
        category.setAdapter(ad);
        charity_name.setEnabled(false);
        charity_mobile.setEnabled(false);

        category.setOnItemSelectedListener(this);
        req_gen.setOnClickListener(this);
    }
    void requestform(){
        name=data_bank.curUser.getName();
        email=data_bank.curUser.getEmail();
        mobile=data_bank.curUser.getMobile();
        address=data_bank.curUser.getAddress();

        item_name = Item_name.getText().toString();
        item_desc = Item_desc.getText().toString();

        charity_name.setText(name);
        charity_mobile.setText("+91 "+mobile);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0)
           category_value = null;
        else if (position == 1)
            category_value = "health";
        else if (position == 2)
            category_value = "food";
        else if (position == 3)
            category_value = "education";
        else if (position == 3)
            category_value = "nature";
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        requestform();
        if(name.length()!=0 && email.length()!=0 && mobile.length()!=0 && item_desc.length()!=0 && item_name.length()!=0 && category_value!=null){
            req_donation.setEmail(email);
            req_donation.setName(name);
            req_donation.setMobile(mobile);
            req_donation.setAddress(address);
            req_donation.setItem_category(category_value);
            req_donation.setItem_name(item_name);
            req_donation.setItem_desc(item_desc);

            Call<String> c = apIinterface.request_donation(req_donation);
            c.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body().equals("true"))
                        Toast.makeText(getActivity(),"Request generated",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(),"Request failed",Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getActivity(),"server error",Toast.LENGTH_SHORT).show();
                }
            });

        }
        else{
            Toast.makeText(getActivity(), "please complete all fields", Toast.LENGTH_SHORT).show();
        }
    }
}