package com.infotech.wedonate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.donation_model;

import java.util.ArrayList;

public class Charity_recieved_adapter extends RecyclerView.Adapter<Charity_recieved_adapter.MyHolder4> {

    ArrayList<donation_model> list = new ArrayList<>();

    public Charity_recieved_adapter(ArrayList<donation_model> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.charity_recieved_recylerview,null);
        MyHolder4 holder =  new MyHolder4(view);
        return holder;    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder4 holder, int position) {
        holder.donor_name.setText(list.get(position).getDonor_name());
        holder.mem_email.setText(list.get(position).getMem_email());
        holder.charity_email.setText(list.get(position).getCharity_email());
        holder.donor_email.setText("+91 "+list.get(position).getDonor_email());
        holder.item_name.setText(list.get(position).getItem_name());
        holder.item_cat.setText(list.get(position).getItem_category());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder4 extends RecyclerView.ViewHolder {
        TextView donor_name,donor_email,mem_email,charity_email,item_cat,item_name;
        public MyHolder4(@NonNull View itemView) {
            super(itemView);

          donor_name = itemView.findViewById(R.id.recieved_donor_name);
          donor_email = itemView.findViewById(R.id.recieved_donor_email);
          mem_email = itemView.findViewById(R.id.recieved_mem_email);
          charity_email = itemView.findViewById(R.id.recieved_charity_email);
          item_cat = itemView.findViewById(R.id.recieved_category);
          item_name= itemView.findViewById(R.id.recieved_item_name);
        }
    }
}
