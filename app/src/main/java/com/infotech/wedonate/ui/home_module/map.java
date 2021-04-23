package com.infotech.wedonate.ui.home_module;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.curLocation;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.util.Retroclient;

import java.util.ArrayList;

import kotlin.collections.DoubleIterator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class map extends FragmentActivity implements OnMapReadyCallback {
    static Double latitude =-34.0,longitude =151.0;
    private GoogleMap mMap;
    FloatingActionButton recenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        recenter = findViewById(R.id.recenter);
        recenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLocation();
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
       // Log.d("map",latitude+"::"+longitude);
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title(""));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
    }

    public void changeLocation(){
        APIinterface apIinterface = Retroclient.retroinit();
        //Log.d("usermem",data_bank.nearest_user.getUseremail());
        Call<ArrayList<curLocation>> c = apIinterface.map_member_location(data_bank.nearest_user.getUseremail());
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