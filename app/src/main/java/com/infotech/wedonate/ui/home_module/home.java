package com.infotech.wedonate.ui.home_module;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.ui.home_module.charity.charity_fragment;
import com.infotech.wedonate.ui.home_module.charity.charity_setfragment_activity;
import com.infotech.wedonate.ui.home_module.donor.donor_fragment;
import com.infotech.wedonate.ui.home_module.donor.donor_setfragment_activity;
import com.infotech.wedonate.ui.home_module.member.member_fragment;
import com.infotech.wedonate.ui.home_module.member.member_map;
import com.infotech.wedonate.ui.info;
import com.infotech.wedonate.util.Retroclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    FragmentManager fm;
    FragmentTransaction ft;
    BottomNavigationView btm_nav;
    androidx.appcompat.widget.Toolbar toolbar_dr;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    APIinterface apIinterface;
    int flg=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setdrawernavigation();
        btm_nav = findViewById(R.id.btm_nav);
        btm_nav.setOnNavigationItemSelectedListener(this);
        setfragment();

    }
    void setdrawernavigation(){
        toolbar_dr = findViewById(R.id.toolbar_dr);
        drawerLayout = findViewById(R.id.drwaer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar_dr,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar_dr.setNavigationIcon(R.drawable.hamburger);
        navigationView = findViewById(R.id.drawer_navigation);
        if(data_bank.menu_flag == 2) {
            final Menu menu = navigationView.getMenu();
            MenuItem item = menu.getItem(2);
            item.setTitle("Track Donor");
        }
        if(data_bank.menu_flag == 3) {
            final Menu menu = navigationView.getMenu();
            MenuItem item = menu.getItem(2);
            item.setIcon(R.drawable.gift);
            item.setTitle("Recieved Donations");
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();

                int id = item.getItemId();
                if(id==R.id.p_menu_home){
                    drawer_home();
                }
                if(id==R.id.p_menu_profile){
                    fm.beginTransaction().replace(R.id.home_frame, new profile()).commit();
                }
                if(id == R.id.p_menu_track_mem){
                    if(data_bank.menu_flag == 3){
                        data_bank.flag_charity_category = 3;
                        Intent i = new Intent(home.this, charity_setfragment_activity.class);
                        startActivity(i);
                    }
                    else{
                    isactivedonation();}
                }
                if(id==R.id.p_menu_logout)
                {

                    alertuser();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void drawer_home() {
        if (data_bank.curUser.getUsertype().equals("donor")) {
            //Log.d("cur1",data_bank.curUser.getUsertype()+"");
            fm.beginTransaction().replace(R.id.home_frame, new donor_fragment()).commit();
        } else if (data_bank.curUser.getUsertype().equals("charity")) {
            ft.replace(R.id.home_frame, new charity_fragment());
            ft.commit();
        } else if (data_bank.curUser.getUsertype().equals("member")) {
            ft.replace(R.id.home_frame, new member_fragment());
            ft.commit();
        }
    }

    private void alertuser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You wanted to sign out");
        alertDialogBuilder.setPositiveButton("yes",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                data_bank.curUser = null;
                SharedPreferences sf = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor ed = sf.edit();
                ed.clear();
                ed.commit();
                Intent i = new Intent(home.this, info.class);
                startActivity(i);
                finishAffinity();
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void isactivedonation() {
        apIinterface= Retroclient.retroinit();
        Call<String> c = apIinterface.is_active_donation(data_bank.curUser.getEmail());
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()==400)
                {
                    if(data_bank.menu_flag == 1){
                        if(data_bank.curUser.getUsertype().equalsIgnoreCase("donor")){
                            Intent i = new Intent(home.this, map.class);
                            drawerLayout.closeDrawer(GravityCompat.START);
                            startActivity(i);
                        }
                    }else if(data_bank.menu_flag == 2){
                        if(data_bank.curUser.getUsertype().equalsIgnoreCase("member")){
                            Intent i = new Intent(home.this, member_map.class);
                            drawerLayout.closeDrawer(GravityCompat.START);
                            startActivity(i);
                        }
                    }

                }
                else{
                    Intent i = new Intent(home.this, donor_setfragment_activity.class);
                    i.putExtra("nodonations","false");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(home.this,"server error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setfragment() {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        Intent i = getIntent();
        String frg = i.getStringExtra("fragment");

        if (frg != null) {
            if (frg.equals("profile")) {
                ft.replace(R.id.home_frame, new profile());
                ft.commit();
            }
        } else if (data_bank.curUser.getUsertype().equals("donor")) {
            ft.replace(R.id.home_frame, new donor_fragment());
            ft.commit();
        } else if (data_bank.curUser.getUsertype().equals("charity")) {
            ft.replace(R.id.home_frame, new charity_fragment());
            ft.commit();
        }
        else if (data_bank.curUser.getUsertype().equals("member")) {
            ft.replace(R.id.home_frame, new member_fragment());
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {


        if(flg==1){
            finishAffinity();
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            flg=1;

        } else {
            finishAffinity();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        int id = item.getItemId();

        if (id == R.id.home) {
            if (data_bank.curUser.getUsertype().equals("donor")) {
                ft.replace(R.id.home_frame, new donor_fragment());
                ft.commit();
            } else if (data_bank.curUser.getUsertype().equals("charity")) {
                ft.replace(R.id.home_frame, new charity_fragment());
                ft.commit();
            } else if (data_bank.curUser.getUsertype().equals("member")) {
                ft.replace(R.id.home_frame, new member_fragment());
                ft.commit();
            }
        } else if (id == R.id.profile) {
            ft.replace(R.id.home_frame, new profile());
            ft.commit();
        }
        return false;
    }

}
