package com.infotech.wedonate.adapter;

import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.donation_model;

import java.util.ArrayList;

public class Charity_Recycler_adapter extends RecyclerView.Adapter<Charity_Recycler_adapter.MyHolder2>{

    ArrayList<donation_model> list = new ArrayList<>();

    public Charity_Recycler_adapter(ArrayList<donation_model> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.charity_recyler_design,null);
        MyHolder2 holder = new MyHolder2(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder2 holder, int position) {
        holder.charity_name.setText(list.get(position).getCharity_name());
        holder.charity_address.setText(list.get(position).getAddress());
        holder.item_name.setText(list.get(position).getItem_name());
        holder.item_category.setText(list.get(position).getItem_category());
        holder.item_desc.setText(list.get(position).getItem_desc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder2 extends RecyclerView.ViewHolder{
        TextView charity_name,charity_address,item_category,item_name,item_desc;
        public MyHolder2(@NonNull View itemView) {
            super(itemView);
            charity_name= itemView.findViewById(R.id.ch_rec_charity_name);
            charity_address= itemView.findViewById(R.id.ch_rec_charity_address);
            item_category=itemView.findViewById(R.id.ch_rec_category);
            item_name=itemView.findViewById(R.id.ch_rec_item_name);
            item_desc=itemView.findViewById(R.id.ch_rec_item_desc);
        }
    }
}
