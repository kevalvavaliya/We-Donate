
package com.infotech.wedonate.ui.home_module.member;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.services.locationservice;
import com.infotech.wedonate.util.FetchLocation;
import com.infotech.wedonate.util.Retroclient;
import com.infotech.wedonate.util.asyncTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class member_fragment extends Fragment implements View.OnClickListener {

    View view;
    FetchLocation location;
    APIinterface apIinterface;
    ImageView current_donations;
    SharedPreferences sf;
    String flag;
    TextView user_name;
    String username;
    ImageView chariy_donations;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_member_fragment, container, false);
        chariy_donations = view.findViewById(R.id.mem_ch_donation);
        chariy_donations.setOnClickListener(this);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apIinterface = Retroclient.retroinit();
        current_donations = view.findViewById(R.id.deliver_current_donations);
        user_name = view.findViewById(R.id.user_name);

        current_donations.setOnClickListener(this);
        sf = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        flag = sf.getString("current_donation_flag", "0");

        //inserting notification token---------------------------------------

        if (data_bank.noti_token.getToken() != null) {
            data_bank.noti_token.setEmail(data_bank.curUser.getEmail());
            data_bank.noti_token.setUsertype(data_bank.curUser.getUsertype());
            insertnotificationToken();
        }

        //title user name string----------------------------------------
        String uname = data_bank.curUser.getName();
        int index = uname.indexOf(" ");
        if (index != -1) {
            username = "Hello, " + uname.substring(0, index);
            user_name.setText(username);
        } else {
            user_name.setText("Hello, " + uname);
        }

        //checking location permissions------------------------------------
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            request_location();
        } else {
            location = new FetchLocation(getContext());
            location.GetLocation(new asyncTask() {
                @Override
                public void actionPerformed() {
                    getContext().startService(new Intent(getContext(), locationservice.class));
                }
            });
        }

    }

    private void insertnotificationToken() {
        Call<String> c = apIinterface.insert_notification_token(data_bank.noti_token);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                } else {
                    //Log.d("response", "false");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

    }

    void request_location() {
        String permission[] = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        requestPermissions(permission, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            location = new FetchLocation(getContext());
            location.GetLocation(new asyncTask() {
                @Override
                public void actionPerformed() {
                    getContext().startService(new Intent(getContext(), locationservice.class));
                }
            });

        } else {
            request_location();
            Toast.makeText(getContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            getContext().startActivity(intent);
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.deliver_current_donations) {
            if (flag.equals("1")) {
                Intent i = new Intent(getActivity(), notification_intent.class);
                startActivity(i);
            }
            if (flag.equals("0")) {
                data_bank.flag_mem_category = 0;
                Intent i = new Intent(getActivity(), member_setfragement_activity.class);
                startActivity(i);

            }
        } else if (v.getId() == R.id.mem_ch_donation) {
            data_bank.flag_mem_category = 1;
            Intent i = new Intent(getActivity(), member_setfragement_activity.class);
            startActivity(i);
        }
    }
}