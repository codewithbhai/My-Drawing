package com.advance.mydrawing.number_know;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.advance.mydrawing.databinding.NumberKnowActivityBinding;
import com.advance.mydrawing.number_know.models.NumberIdentifierViewModel;

import java.io.IOException;

/**
 * Created by zulfikar on 28 Dec 2023 at 11:26.
 */
public class NumberIdentifierActivity extends AppCompatActivity {
   private NumberKnowActivityBinding mBinding;
   private NumberIdentifierViewModel mViewModel;
   private DigitsClassifier digitsClassifier;


   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mBinding = NumberKnowActivityBinding.inflate(getLayoutInflater());
      setContentView(mBinding.getRoot());

      mViewModel = new ViewModelProvider(this).get(NumberIdentifierViewModel.class);
      mBinding.setViewModel(mViewModel);
      mBinding.setLifecycleOwner(this);

      //now you can do anything

      if (!hasPermission()){
         requestPermission();
      }

      try {
         digitsClassifier = DigitsClassifier.create(getAssets(), getApplicationContext());
         Log.i(this.getClass().getName(), "Model Initiated successfully.");
         Toast.makeText(getApplicationContext(), "DigitsClassifier created", Toast.LENGTH_SHORT).show();
      }catch (IOException e){
         e.printStackTrace();
      }

      mBinding.submitBtn.setOnClickListener((view) -> {
         mBinding.drawView.clear();
      });

      mBinding.drawView.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_UP){
               classifyDigit();
               return true;
            }
            return false;
         }
      });


      mViewModel.updateText("you are best. you are doing some issue");

   }

   private void classifyDigit() {
      Bitmap image = Bitmap.createBitmap(mBinding.drawView.getWidth(), mBinding.drawView.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(image);
      canvas.drawColor(Color.BLACK);
      mBinding.drawView.draw(canvas);

      String digit = digitsClassifier.classifyDigits(image);
      Toast.makeText(getApplicationContext(), "Your Digit: " + digit, Toast.LENGTH_LONG).show();
   }

   private boolean hasPermission() {
      if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
         return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                 && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
      }else {
         return true;
      }
   }

   private void requestPermission(){
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
      }
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();
   }
}
