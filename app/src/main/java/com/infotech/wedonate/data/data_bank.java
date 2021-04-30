package com.infotech.wedonate.data;

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
    public static int flag_mem_category;
    public static ArrayList<curLocation> member_location_array = new ArrayList<>();
    public static curLocation nearest_user = new curLocation();
    public static Token_model noti_token = new Token_model();
    public static donation_model current_donation = new donation_model();
    public static ArrayList<data_model> ch_mem_list = new ArrayList<>();
    public static int trak_mem_flag;
    public static int menu_flag;
}


