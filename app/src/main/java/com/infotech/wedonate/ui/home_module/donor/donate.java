package com.infotech.wedonate.ui.home_module.donor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.API.response;
import com.infotech.wedonate.R;
import com.infotech.wedonate.adapter.RecylerAdapter;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.donation_model;
import com.infotech.wedonate.util.Retroclient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class donate extends Fragment {


    View view;
    RecyclerView donation_list;

    APIinterface apIinterface;

    FloatingActionButton floatingActionButton;

    FragmentManager fm;
    FragmentTransaction ft;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_donate, container, false);
        initalization();
        setdonationlist();

        return view;
    }


    void initalization() {
        donation_list = view.findViewById(R.id.donate_donationlist);
        apIinterface = Retroclient.retroinit();
        floatingActionButton = view.findViewById(R.id.floating_donation_list);
        fm = getActivity().getSupportFragmentManager();
        ft = fm.beginTransaction();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchdata();
                setdonationlist();
            }
        });
    }

    void setdonationlist() {
        donation_list.setLayoutManager(new LinearLayoutManager(getContext()));
        RecylerAdapter ad;

        if (data_bank.left_time.size() == 0)
            fetchdata();

        ad = new RecylerAdapter(data_bank.donations, data_bank.left_time,getContext());
        //Log.d("HIII",data_bank.left_time.get(0) + "");
        donation_list.setAdapter(ad);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                data_bank.position=position;
                donation_model current_swipe = data_bank.donations.get(position);
                ad.addItem(position, current_swipe);
                ft.replace(R.id.donor_donation_frm,new confirm_donation());
                ad.notifyItemChanged(position);
                ft.commit();


            }
        });
        itemTouchHelper.attachToRecyclerView(donation_list);
    }

    void fetchdata() {
        Call<ArrayList<donation_model>> c = apIinterface.fetch_donation_list();
        c.enqueue(new Callback<ArrayList<donation_model>>() {
            @Override
            public void onResponse(Call<ArrayList<donation_model>> call, Response<ArrayList<donation_model>> response) {
                if (response.code() == 200) {
                    data_bank.donations = response.body();

                    for (donation_model m : response.body()) {
                        String time = m.getTime();
                        //Log.d("DTAGSEC",time);
                        data_bank.cur_req_end_time.add(Long.parseLong(time));
                    }
                    if(data_bank.left_time == null) {
                        for (long d : data_bank.cur_req_end_time) {
                            long cur_time = System.currentTimeMillis() / 1000;
                            data_bank.left_time.add(d + data_bank.finish_time - cur_time);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<donation_model>> call, Throwable t) {
                Toast.makeText(getActivity() ,"Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}