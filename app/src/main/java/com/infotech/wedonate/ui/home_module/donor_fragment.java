package com.infotech.wedonate.ui.home_module;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;

public class donor_fragment extends Fragment {

    TextView user_name;
    String username;
    View view;
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

        String uname = data_bank.curUser.getName();
        int index = uname.indexOf(" ");
        username ="Hello, " + uname.substring(0,index);

        user_name.setText(username);

    }

}