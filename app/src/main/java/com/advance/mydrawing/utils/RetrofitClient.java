package com.advance.mydrawing.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zulfikar on 30 Dec 2023 at 22:13.
 */
public class RetrofitClient {
   private static final String BASE_URL = "http://codewithbhai.com/kidsara/kidsara_apis/";

   private static Retrofit retrofit = new Retrofit.Builder()
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build();

   public static ApiService getApiService() {
      return retrofit.create(ApiService.class);
   }
}
