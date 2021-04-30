package com.infotech.wedonate.ui.home_module.charity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class charity_fragment extends Fragment implements View.OnClickListener {

    View view;
    LinearLayout request_donation;
    ImageView your_donations,member_list,recieved_donations;
    TextView user_name;
    String username;
    APIinterface apIinterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (data_bank.noti_token.getToken() != null) {
            data_bank.noti_token.setEmail(data_bank.curUser.getEmail());
            data_bank.noti_token.setUsertype(data_bank.curUser.getUsertype());
            insertnotificationToken();
        }

        view = inflater.inflate(R.layout.fragment_charity_fragment, container, false);
        your_donations = view.findViewById(R.id.charity_your_donations);
        request_donation=view.findViewById(R.id.request_donation);
        user_name = view.findViewById(R.id.user_name);
        member_list = view.findViewById(R.id.charity_member_list);
        recieved_donations=view.findViewById(R.id.recieved_donations);

        request_donation.setOnClickListener(this);
        your_donations.setOnClickListener(this);
        member_list.setOnClickListener(this);
        recieved_donations.setOnClickListener(this);





        String uname = data_bank.curUser.getName();
        int index = uname.indexOf(" ");
        if(index!=-1) {
            username = "Hello, " + uname.substring(0, index);
            user_name.setText(username);
        }
        else{
            user_name.setText("Hello, "+uname);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if(v.getId() == R.id.request_donation){
            data_bank.flag_charity_category=0;
        }
        else if(v.getId() == R.id.charity_your_donations){
            data_bank.flag_charity_category=1;
        }
        else if(v.getId() == R.id.charity_member_list){
            data_bank.flag_charity_category=2;
        }
        else if(v.getId()==R.id.recieved_donations){
            data_bank.flag_charity_category=3;
        }

        intent = new Intent(getActivity(), charity_setfragment_activity.class);
        startActivity(intent);
    }


    private void insertnotificationToken() {
        apIinterface= Retroclient.retroinit();
        Call<String> c = apIinterface.insert_notification_token(data_bank.noti_token);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    Log.d("response_ch_noti", "false");
                } else {
                    Log.d("response_ch_noti", "false");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

    }
}