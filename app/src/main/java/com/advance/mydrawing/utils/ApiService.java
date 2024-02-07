package com.advance.mydrawing.utils;

import com.advance.mydrawing.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by zulfikar on 30 Dec 2023 at 22:08.
 */
public interface ApiService {
 @GET("My_drawing/data_list.php")
 Call<List<User>> getUsers();
}