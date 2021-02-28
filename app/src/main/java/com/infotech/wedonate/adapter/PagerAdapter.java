package com.infotech.wedonate.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> ar_fragment = new ArrayList<Fragment>();
    ArrayList<String> ar_title = new ArrayList<String>();
    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    public void add(Fragment fragment,String title)
    {
        ar_fragment.add(fragment);
        ar_title.add(title);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ar_fragment.get(position);
    }

    @Override
    public int getCount() {
        return ar_fragment.size();
    }


}
