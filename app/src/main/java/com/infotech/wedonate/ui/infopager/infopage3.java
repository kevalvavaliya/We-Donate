package com.infotech.wedonate.ui.infopager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infotech.wedonate.R;
import com.infotech.wedonate.adapter.GifImageView;


public class infopage3 extends Fragment {
    GifImageView info3_img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_infopage3, container, false);
        info3_img=view.findViewById(R.id.img_info3);
        info3_img.setGifImageResource(R.drawable.info3);
        return view;

    }
}