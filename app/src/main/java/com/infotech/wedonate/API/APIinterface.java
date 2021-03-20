package com.infotech.wedonate.API;

import com.infotech.wedonate.data.data_model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIinterface {
    @POST("/connect")
    Call<String> connect();

    @POST("/signup_donor")
    Call<signup_response> signup_donor(@Body data_model donor);

    @POST("/signup_member")
    Call<signup_response> signup_member(@Body data_model member);

    @POST("/signup_charity")
    Call<signup_response> signup_charity(@Body data_model charity);

    @POST("/otp_verification")
    Call<signup_response> otp_verification(@Body data_model user);

    @POST("/login")
    Call<signup_response> login(@Body data_model user);
}
