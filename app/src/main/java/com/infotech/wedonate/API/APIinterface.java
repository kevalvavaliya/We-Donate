package com.infotech.wedonate.API;

import com.infotech.wedonate.data.data_model;
import com.infotech.wedonate.data.user_model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIinterface {
    @POST("/connect")
    Call<String> connect();

    @POST("/signup_donor")
    Call<response> signup_donor(@Body data_model donor);

    @POST("/signup_member")
    Call<response> signup_member(@Body data_model member);

    @POST("/signup_charity")
    Call<response> signup_charity(@Body data_model charity);

    @POST("/otp_verification")
    Call<response> otp_verification(@Body data_model user);

    @POST("/login")
    Call<user_model> login(@Body data_model user);

    @POST("/find_accnt")
    Call<response> find_accnt(@Body data_model user);
}
