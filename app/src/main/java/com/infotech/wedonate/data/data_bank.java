package com.infotech.wedonate.data;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class data_bank {

        public static data_model curUser;
        public static ArrayList<donation_model> charity_donations = new ArrayList<>();
        public static ArrayList<donation_model> donations = new ArrayList<>();
        public static ArrayList<donation_model> donations_amenities = new ArrayList<>();
        public static ArrayList<donation_model> donations_health = new ArrayList<>();
        public static ArrayList<donation_model> donations_education = new ArrayList<>();
        public static ArrayList<donation_model> donations_nature = new ArrayList<>();
        public static int position;
        public static curLocation current_location;
        public static int flag_donor_category;
        public static int flag_charity_category;
}


