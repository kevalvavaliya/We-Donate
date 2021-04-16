package com.infotech.wedonate.data;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class data_bank {

        public static data_model curUser;
        public static  ArrayList<donation_model> donations = new ArrayList<>();

        public static ArrayList<Long> cur_req_end_time = new ArrayList<>();
        public static ArrayList<Long> left_time = new ArrayList<>();
        public static long finish_time = 7200;
        public static int position;
        public static curLocation current_location;
}


