package com.advance.mydrawing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.advance.mydrawing.custom_drawing.CustomDrawingActivity;
import com.advance.mydrawing.databinding.ActivityMainBinding;
import com.advance.mydrawing.models.UserAdapter;
import com.advance.mydrawing.models.UserViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.hanks.htextview.base.HTextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    int defaultColor = 0x0000FF;
    private UserAdapter userAdapter;
    int delay = 2000; //milliseconds
    Handler handler;
    ArrayList<String> arrMessages = new ArrayList<>();
    int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        userAdapter = new UserAdapter(this, imageView -> {
            Intent intent = new Intent(MainActivity.this, CustomDrawingActivity.class);
            intent.putExtra("drawableId", imageView);
            startActivity(intent);
        });
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mBinding.recyclerView.setAdapter(userAdapter);

        mBinding.setUserViewModel(new ViewModelProvider(this).get(UserViewModel.class));

        arrMessages.add("Hi Kids!");
        arrMessages.add("Your Drawing Book");
        arrMessages.add("Drawing Hub");
        arrMessages.add("Get Ready ");
        arrMessages.add("@start");
        arrMessages.add("Drawing");
        arrMessages.add("Creating");

        mBinding.textViewLine.animateText(arrMessages.get(position));
        position++;

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                handler.postDelayed(this, delay);
                if (position >= arrMessages.size())
                    position = 0;
                mBinding.textViewLine.animateText(arrMessages.get(position));
                position++;
            }
        }, delay);

        mBinding.getUserViewModel().getUsers().observe(this, users -> {
            userAdapter.setUsers(users);
        });

        mBinding.getUserViewModel().getAudioUrlToPlay().observe(this, audioUrl -> {
            // playAudio(audioUrl);
            // Toast.makeText(getApplicationContext(), "1.) your audio url: " + audioUrl, Toast.LENGTH_LONG).show();
        });
    }

    public void playAudio(String audioUrl) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/* mBinding.colorPick.setOnClickListener(v -> {
            showColorPickerDialog();
        });*/

/*    private void showColorPickerDialog() {
        ColorPickerBottomSheetDialog bottomSheetDialog = new ColorPickerBottomSheetDialog(this);
        int[] defaultColorsArray = getResources().getIntArray(R.array.default_colors);

        bottomSheetDialog.setColumns(8)
                .setColors(defaultColorsArray)
                .setDefaultSelectedColor(defaultColor)
                .setColorItemShape(ColorItemShape.SQUARE)
                .setOnSelectColorListener(new OnSelectColorListener() {
                    @Override
                    public void onColorSelected(int color, int position) {
                        Toast.makeText(getApplicationContext(), "Selected Color: " + color+"\n"+position, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void cancel() {
                        bottomSheetDialog.dismissDialog();     // Dismiss the dialog.
                    }
                })
                .show();
    }*/

/*
  //library of "com.mrudultora"
  
  private void showColorPickerDialog() {
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(MainActivity.this);
        colorPickerDialog.setColors()
                .setColumns(8)                        		// Default number of columns is 5.
                .setDefaultSelectedColor(defaultColor)		// By default no color is used.
                .setColorItemShape(ColorItemShape.SQUARE)
                .setOnSelectColorListener(new OnSelectColorListener() {
                    @Override
                    public void onColorSelected(int color, int position) {
                        Toast.makeText(getApplicationContext(), "Selected Color: " + color, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void cancel() {
                        colorPickerDialog.dismissDialog();
                    }
                })
                .show();
    }*/

/* private void showColorPickerDialog() {

        new ColorPickerDialog
                .Builder(MainActivity.this)
                .setTitle("Pick Color")
                .setColorShape(ColorShape.CIRCLE)
                .setDefaultColor(defaultColor)
                .setPositiveButton("Select Color")
                .setColorListener((selected_color, colorHex) -> {
                    {
                        if (colorHex == null) {
                            Toast.makeText(getApplicationContext(), "Color selection canceled", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Selected Color: " + colorHex, Toast.LENGTH_LONG).show();

                        }
                    }

                })
                .show();
    }*/
