package com.infotech.wedonate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.wedonate.R;
import com.infotech.wedonate.data.donation_model;

import java.util.ArrayList;

public class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.MyHolder> {

    ArrayList<donation_model> list = new ArrayList<>();
    ArrayList<Long> left_time = new ArrayList<>();
    ArrayList<TextView> tvs = new ArrayList<>();

    Context context;
    public RecylerAdapter(ArrayList<donation_model> list,ArrayList<Long> left_time,Context context) {
        this.list = list;
        this.left_time = left_time;
        this.context = context;
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
        //setTimer(holder,left_time.get(position),position);
    }

//    private void setTimer(MyHolder holder, Long aLong,int pos) {
//        //TimerService.setTextview(holder.timer,aLong);
//        if(pos == 1){
//            context.startService(new Intent(context,TimerService.class));
//        }
//    }

    public  void addItem(int pos,donation_model d){
        list.add(pos,d);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView charity_name,charity_address,item_category,item_name,item_desc,timer;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            charity_name= itemView.findViewById(R.id.rec_charity_name);
            charity_address= itemView.findViewById(R.id.rec_charity_address);
            item_category=itemView.findViewById(R.id.rec_category);
            item_name=itemView.findViewById(R.id.rec_item_name);
            item_desc=itemView.findViewById(R.id.rec_item_desc);
            timer=itemView.findViewById(R.id.text_view_countdown);

        }
    }
}
