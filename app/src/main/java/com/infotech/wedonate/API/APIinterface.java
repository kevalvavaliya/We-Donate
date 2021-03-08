package com.infotech.wedonate.API;

import android.util.Log;

import com.infotech.wedonate.data.signup_data_model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIinterface {
    @POST("/signup")
    Call<signup_response> signup(@Body signup_data_model user);
}
