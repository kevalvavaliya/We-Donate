
package com.infotech.wedonate.ui.home_module.member;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.services.locationservice;
import com.infotech.wedonate.util.FetchLocation;
import com.infotech.wedonate.util.Retroclient;
import com.infotech.wedonate.util.asyncTask;

public class member_fragment extends Fragment {

    View view;
    FetchLocation location;
    APIinterface apIinterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_member_fragment, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apIinterface = Retroclient.retroinit();
        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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


}