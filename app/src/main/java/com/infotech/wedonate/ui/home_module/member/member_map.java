package com.infotech.wedonate.ui.home_module.member;

import androidx.fragment.app.FragmentActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.curLocation;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.util.Retroclient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class member_map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SharedPreferences sf;
    static Double latitude =-34.0,longitude =151.0;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_mem);
        sf = getSharedPreferences("Login", MODE_PRIVATE);
        mapFragment.getMapAsync(this::onMapReady);
        floatingActionButton = findViewById(R.id.recenter_map);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchlocation();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title(""));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
    }

    public void fetchlocation(){
        APIinterface apIinterface = Retroclient.retroinit();
        String email = sf.getString("current_donation_donor","nodata");
        Call<ArrayList<curLocation>> c = apIinterface.donor_location(email);
        c.enqueue(new Callback<ArrayList<curLocation>>() {
            @Override
            public void onResponse(Call<ArrayList<curLocation>> call, Response<ArrayList<curLocation>> response) {
                if(response.code()==200){
                    ArrayList<curLocation> mem = response.body();
                    if(mem.size()==1)
                    {
                        curLocation c = mem.get(0);
                        latitude =Double.parseDouble(c.getLatitude());
                        longitude =Double.parseDouble(c.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),15));
                    }
                }
                else{

                }
            }
            @Override
            public void onFailure(Call<ArrayList<curLocation>> call, Throwable t) {

            }
        });
        onMapReady(mMap);
    }
}