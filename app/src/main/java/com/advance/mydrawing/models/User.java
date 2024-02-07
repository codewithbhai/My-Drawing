package com.advance.mydrawing.models;

/**
 * Created by zulfikar on 30 Dec 2023 at 22:11.
 */
public class User {
   private int id;
   private String name;
   private String image;
   private String audio;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public String getAudio() {
      return audio;
   }

   public void setAudio(String audio) {
      this.audio = audio;
   }
}
