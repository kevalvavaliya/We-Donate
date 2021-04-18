package com.infotech.wedonate.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_bank;
import com.infotech.wedonate.data.donation_model;

import java.util.ArrayList;

public class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.MyHolder> {

    ArrayList<donation_model> list = new ArrayList<>();
    ArrayList<Long> left_time = new ArrayList<>();
    ArrayList<TextView> tvs = new ArrayList<>();
    Drawable dr ;

    Context context;
    public RecylerAdapter(ArrayList<donation_model> list,Context context, Drawable dr) {
        this.list = list;
        this.context = context;
        this.dr=dr;

    }
    @NonNull
    @Override
    public RecylerAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recylerview_design,null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerAdapter.MyHolder holder, int position) {
        holder.charity_name.setText(list.get(position).getCharity_name());
        holder.charity_address.setText(list.get(position).getAddress());
        holder.item_name.setText(list.get(position).getItem_name());
        holder.item_category.setText(list.get(position).getItem_category());
        holder.item_desc.setText(list.get(position).getItem_desc());
        holder.swipe.setBackground(dr);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView charity_name,charity_address,item_category,item_name,item_desc,swipe;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            charity_name= itemView.findViewById(R.id.rec_charity_name);
            charity_address= itemView.findViewById(R.id.rec_charity_address);
            item_category=itemView.findViewById(R.id.rec_category);
            item_name=itemView.findViewById(R.id.rec_item_name);
            item_desc=itemView.findViewById(R.id.rec_item_desc);
            swipe=itemView.findViewById(R.id.text_view_countdown);
            swipe.setBackground(dr);

        }
    }
}
