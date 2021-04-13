package com.infotech.wedonate.ui.home_module.donor;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.ui.home_module.charity.charity_setfragment_activity;

public class donor_fragment extends Fragment implements View.OnClickListener {

    TextView user_name;
    String username;
    View view;
    LinearLayout donor_donate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_donor_home, container, false);

        intialization();
        return view;
    }

    void intialization(){
        user_name = view.findViewById(R.id.user_name);
        donor_donate=view.findViewById(R.id.donor_donate);


        String uname = data_bank.curUser.getName();
        int index = uname.indexOf(" ");
        username ="Hello, " + uname.substring(0,index);
        user_name.setText(username);

        donor_donate.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.donor_donate)
        {
            Intent intent = new Intent(getActivity(),donor_setfragment_activity.class);
            startActivity(intent);
        }
    }
}