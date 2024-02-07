package com.advance.mydrawing.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by zulfikar on 30 Dec 2023 at 22:12.
 */
public class UserViewModel extends ViewModel {
   private MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();
   private MutableLiveData<String> audioUrlToPlay = new MutableLiveData<>();

   private UserRepository userRepository;

   public UserViewModel() {
      userRepository = new UserRepository();
      fetchUsers();
   }

   public LiveData<String> getAudioUrlToPlay() {
      return audioUrlToPlay;
   }


   public LiveData<List<User>> getUsers() {
      return usersLiveData;
   }

   private void fetchUsers() {
      userRepository.getUsers(users -> usersLiveData.setValue(users));
   }

   public void onAudioClick(User user) {
      audioUrlToPlay.setValue(user.getAudio());
   }
}
