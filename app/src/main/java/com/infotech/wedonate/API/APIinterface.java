package com.infotech.wedonate.API;

import android.util.Log;

import com.infotech.wedonate.data.signup_data_model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIinterface {
    @POST("/signup_donor")
    Call<signup_response> signup_donor(@Body signup_data_model donor);

    @POST("/signup_member")
    Call<signup_response> signup_member(@Body signup_data_model member);

    @POST("/signup_charity")
    Call<signup_response> signup_charity(@Body signup_data_model charity);
}
