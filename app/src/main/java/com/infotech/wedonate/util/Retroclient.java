package com.infotech.wedonate.util;

import com.infotech.wedonate.API.APIinterface;
import com.infotech.wedonate.API.MyRetrofit;

import retrofit2.Retrofit;

public class Retroclient {

   private static String url="http://192.168.43.83:5000";
 //  private static String url="https://wedonate.herokuapp.com/";
   public static APIinterface retroinit()
    {
      Retrofit retrofit= MyRetrofit.getRetrofit(url);
      APIinterface apIinterface = retrofit.create(APIinterface.class);
      return apIinterface;
    }

}
