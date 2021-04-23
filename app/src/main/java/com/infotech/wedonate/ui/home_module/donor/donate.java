package com.infotech.wedonate.ui.home_module.donor;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.R;
import com.infotech.wedonate.adapter.RecylerAdapter;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.donation_model;
import com.infotech.wedonate.util.GetDonations;
import com.infotech.wedonate.util.Retroclient;

public class donate extends Fragment {


    View view;
    RecyclerView donation_list;
    APIinterface apIinterface;
    LinearLayout search_linear;
    FloatingActionButton floatingActionButton;
    FragmentManager fm;
    FragmentTransaction ft;
    GetDonations getDonations = new GetDonations("donor");
    Toolbar toolbar;
    Drawable dr1, dr2, dr3, dr4, dr5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_donate, container, false);
        initalization();
        if(data_bank.donations.size()==0){
            getDonations.fetchdata();
            setdonationlist();
        }
        else {
            setdonationlist();
        }

        return view;
    }


    void initalization() {
        donation_list = view.findViewById(R.id.donate_donationlist);
        search_linear = view.findViewById(R.id.search_linear_back);
        apIinterface = Retroclient.retroinit();
        floatingActionButton = view.findViewById(R.id.floating_donation_list);
        fm = getActivity().getSupportFragmentManager();
        ft = fm.beginTransaction();
        toolbar = getActivity().findViewById(R.id.toolbar);

        dr1 = getActivity().getDrawable(R.drawable.category_all_back);
        dr2 = getActivity().getDrawable(R.drawable.category_health_back);
        dr3 = getActivity().getDrawable(R.drawable.category_education_back);
        dr4 = getActivity().getDrawable(R.drawable.category_nature_back);
        dr5 = getActivity().getDrawable(R.drawable.category_amenities_back);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDonations.fetchdata();
                setdonationlist();
            }
        });
    }

    void setdonationlist() {
        donation_list.setLayoutManager(new GridLayoutManager(getContext(), 1));
        RecylerAdapter ad;
        ad = new RecylerAdapter(data_bank.donations, getContext(), dr1);

        if (data_bank.flag_donor_category == 0) {

            if (data_bank.donations.size() != 0) {
                toolbar.setBackgroundColor(Color.rgb(255, 217, 29));
                ad = new RecylerAdapter(data_bank.donations, getContext(), dr1);
                donation_list.setAdapter(ad);
            }
        } else if (data_bank.flag_donor_category == 1) {

            if (data_bank.donations.size() != 0) {
                toolbar.setBackgroundColor(Color.rgb(98, 216, 241));
                search_linear.setBackgroundColor(Color.rgb(98, 216, 241));
                ad = new RecylerAdapter(data_bank.donations_health, getContext(), dr2);
                donation_list.setAdapter(ad);
            }
        } else if (data_bank.flag_donor_category == 2) {

            if (data_bank.donations.size() != 0) {
                toolbar.setBackgroundColor(Color.rgb(243, 102, 216));
                search_linear.setBackgroundColor(Color.rgb(243, 102, 216));
                ad = new RecylerAdapter(data_bank.donations_education, getContext(), dr3);
                donation_list.setAdapter(ad);
            }
        } else if (data_bank.flag_donor_category == 3) {

            if (data_bank.donations.size() != 0) {
                toolbar.setBackgroundColor(Color.rgb(240, 149, 97));
                search_linear.setBackgroundColor(Color.rgb(240, 149, 97));
                ad = new RecylerAdapter(data_bank.donations_amenities, getContext(), dr5);
                donation_list.setAdapter(ad);
            }
        } else if (data_bank.flag_donor_category == 4) {

            if (data_bank.donations.size() != 0) {
                toolbar.setBackgroundColor(Color.rgb(92, 212, 150));
                search_linear.setBackgroundColor(Color.rgb(92, 212, 150));
                ad = new RecylerAdapter(data_bank.donations_nature, getContext(), dr4);
                donation_list.setAdapter(ad);
            }
        }

        RecylerAdapter finalAd = ad;
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                data_bank.position = position;

                //finalAd.addItem(position, current_swipe);
                ft.replace(R.id.donor_donation_frm, new confirm_donation());
                finalAd.notifyItemChanged(position);
                ft.commit();
            }
        });
        itemTouchHelper.attachToRecyclerView(donation_list);
    }

}