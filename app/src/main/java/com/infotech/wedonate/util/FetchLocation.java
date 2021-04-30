package com.infotech.wedonate.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.data.curLocation;
import com.infotech.wedonate.data.data_bank;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchLocation {

    public Double latitude;
    public Double longitude;
    FusedLocationProviderClient locClient;
    Context context;
    curLocation curloc = new curLocation();
    APIinterface apIinterface = Retroclient.retroinit();

    public FetchLocation(Context context) {
        this.context = context;
    }

    @SuppressLint("MissingPermission")
    public void GetLocation(asyncTask Task) {
        ArrayList<String> coordinate = new ArrayList<>();
        if (LocationEnabled()) {
            locClient = new FusedLocationProviderClient(context);
            locClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location curLoc = task.getResult();
                    if (curLoc != null) {
                        latitude = curLoc.getLatitude();
                        longitude = curLoc.getLongitude();
                        data_bank.current_location = new curLocation(latitude.toString(), longitude.toString());
                         Log.d("LOCATION",String.valueOf(latitude)+":::"+ String.valueOf(longitude));                        Task.actionPerformed();
                    } else {
                        requestLocation();
                    }
                }
            });
        } else {
            Toast.makeText(context, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(intent);
        }
    }

    @SuppressLint("MissingPermission")
    private void requestLocation() {
        LocationRequest request = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5)
                .setFastestInterval(0)
                .setNumUpdates(1);

        locClient = LocationServices.getFusedLocationProviderClient(context);
        locClient.requestLocationUpdates(request, locationCallback, Looper.myLooper());
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Location curLoc = locationResult.getLastLocation();
            latitude = curLoc.getLatitude();
            longitude = curLoc.getLongitude();

        }
    };

    public boolean LocationEnabled() {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void storelocation(asyncTask Task) {

        if (data_bank.current_location!=null) {
            curloc.setLatitude(data_bank.current_location.getLatitude());
            curloc.setLongitude(data_bank.current_location.getLongitude());
            curloc.setUseremail(data_bank.curUser.getEmail());
            curloc.setUsername(data_bank.curUser.getName());
            curloc.setUsertype(data_bank.curUser.getUsertype());

        }

        Call<String> c = apIinterface.member_locations(curloc);
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("true")) {
                    Task.actionPerformed();
                } else {
                    GetLocation(new asyncTask() {
                        @Override
                        public void actionPerformed() {
                            storelocation(new asyncTask() {
                                @Override
                                public void actionPerformed() {

                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }
}
