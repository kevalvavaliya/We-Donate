package com.infotech.wedonate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.data.donation_model;

import java.util.ArrayList;

public class Charity_member_list_adapter extends RecyclerView.Adapter<Charity_member_list_adapter.MyHolder3> {

    ArrayList<data_model> list = new ArrayList<>();

    public Charity_member_list_adapter(ArrayList<data_model> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.member_list_recyler,null);
        MyHolder3 holder = new MyHolder3(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder3 holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.email.setText(list.get(position).getEmail());
        holder.mobile.setText(list.get(position).getMobile());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder3 extends RecyclerView.ViewHolder {
        TextView name,email,mobile;
        public MyHolder3(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.ch_member_name);
            email=itemView.findViewById(R.id.ch_member_email);
            mobile=itemView.findViewById(R.id.ch_member_mobile);
        }
    }
}
