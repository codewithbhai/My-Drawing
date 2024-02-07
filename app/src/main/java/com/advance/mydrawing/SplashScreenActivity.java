package com.advance.mydrawing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.advance.mydrawing.databinding.SplashScreenActivityBinding;
import com.bumptech.glide.Glide;

/**
 * Created by Zulfikar on 10 Jan 2024 at 21:35.
 */
public class SplashScreenActivity extends AppCompatActivity {
    SplashScreenActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SplashScreenActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Glide.with(this).asGif().load(R.drawable.giphy).into(binding.imgGif);


        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        }, 4000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
