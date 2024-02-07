package com.advance.mydrawing.models;

import com.advance.mydrawing.utils.ApiService;
import com.advance.mydrawing.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zulfikar on 30 Dec 2023 at 22:13.
 */
public class UserRepository {
   private ApiService apiService;

   public UserRepository() {
      apiService = RetrofitClient.getApiService();
   }

   public void getUsers(final OnUsersFetchedListener listener) {
      Call<List<User>> call = apiService.getUsers();
      call.enqueue(new Callback<List<User>>() {
         @Override
         public void onResponse(Call<List<User>> call, Response<List<User>> response) {
            if (response.isSuccessful()) {
               listener.onUsersFetched(response.body());
            }
         }

         @Override
         public void onFailure(Call<List<User>> call, Throwable t) {
            // Handle failure, e.g., show an error message
         }
      });
   }

   public interface OnUsersFetchedListener {
      void onUsersFetched(List<User> users);
   }
}

