package com.infotech.wedonate.ui.infopager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.infotech.wedonate.R;
import com.infotech.wedonate.adapter.GifImageView;

public class infopage2 extends Fragment {

    GifImageView info2_img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_infopage2, container, false);
        info2_img=view.findViewById(R.id.img_info2);
        info2_img.setGifImageResource(R.drawable.info2);
        return view;
    }
}