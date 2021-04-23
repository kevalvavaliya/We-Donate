package com.infotech.wedonate.API;

import com.infotech.wedonate.data.curLocation;
import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.data.donation_model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIinterface {
   /* @POST("/connect")
    Call<String> connect();*/

    @POST("/signup_donor")
    Call<response> signup_donor(@Body data_model donor);

    @POST("/signup_member")
    Call<response> signup_member(@Body data_model member);

    @POST("/signup_charity")
    Call<response> signup_charity(@Body data_model charity);

    @POST("/otp_verification")
    Call<response> otp_verification(@Body data_model user);

    @POST("/login")
    Call<data_model> login(@Body data_model user);

    @POST("/find_accnt")
    Call<response> find_accnt(@Body data_model user);

    @POST("/check_profile")
    Call<data_model> check_profile(@Body data_model user);

    @POST("/setaddress")
    Call<String> setaddress(@Body data_model user);

    @POST("/request_donation")
    Call<String> request_donation(@Body data_model req_donation);

    @POST("/fetch_donation_list")
    Call<ArrayList<donation_model>> fetch_donation_list();

    @POST("/locations")
    Call<ArrayList<curLocation>> locations(@Body curLocation loc);

    @POST("/member_location")
    Call<String> member_locations(@Body curLocation loc);

    @POST("/map_member_location")
    Call<ArrayList<curLocation>> map_member_location(@Body String loc);
}
