package com.advance.mydrawing.custom_drawing;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.advance.mydrawing.MainActivity;
import com.advance.mydrawing.R;
import com.advance.mydrawing.databinding.CustomDrawingActivityBinding;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.colorpicker.ColorPickerDialog;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.mrudultora.colorpicker.ColorPickerBottomSheetDialog;
import com.mrudultora.colorpicker.listeners.OnSelectColorListener;
import com.mrudultora.colorpicker.util.ColorItemShape;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zulfikar on 28 Dec 2023 at 17:27.
 */
public class CustomDrawingActivity extends AppCompatActivity {
    private CustomDrawingActivityBinding mBinding;
    private static final float MIN_SCALE = 0.1f;
    private static final float MAX_SCALE = 5.0f;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = CustomDrawingActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        String imageUrl = getIntent().getStringExtra("drawableId");

        mBinding.ImagesDrawing.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)  // Optional placeholder image while loading
                .error(R.drawable.ic_launcher_background)
                .into(mBinding.ImagesDrawing);
       // Toast.makeText(getApplicationContext(), "imag" + imageUrl, Toast.LENGTH_LONG).show();

        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListner());
        mBinding.screen.setOnClickListener(v -> captureScreen());

        mBinding.colorPick.setOnClickListener(v -> {
            showColorPickerDialog();
        });
        mBinding.eraseButton.setOnClickListener(v -> eraseDrawing());
        mBinding.drawingView.setPaintSize(10);

        mBinding.clearButton.setOnClickListener(v -> {
            if (mBinding.drawingView != null) {
                mBinding.drawingView.clearDrawing();
            }
        });
        mBinding.changeSizeButton.setOnClickListener(v -> showPaintSizeDialog());

        // Restore drawing state if available
        if (savedInstanceState != null) {
            mBinding.drawingView.onRestoreInstanceState(savedInstanceState);
        }
    }

 /*   private void showColorPickerDialog() {
        int defaultColor = 0x0000FF;
        new ColorPickerDialog
                .Builder(CustomDrawingActivity.this)
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

    private void showColorPickerDialog() {
        int defaultColor = 0x0000FF;
        ColorPickerBottomSheetDialog bottomSheetDialog = new ColorPickerBottomSheetDialog(this);
        int[] defaultColorsArray = getResources().getIntArray(R.array.default_colors);

        bottomSheetDialog.setColumns(8)
                .setColors(defaultColorsArray)
                .setDefaultSelectedColor(defaultColor)
                .setColorItemShape(ColorItemShape.SQUARE)
                .setOnSelectColorListener(new OnSelectColorListener() {
                    @Override
                    public void onColorSelected(int color, int position) {
                        setDrawingView(color);
                      //  Toast.makeText(getApplicationContext(), "Selected Color: " + color + "\n" + position, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void cancel() {
                        bottomSheetDialog.dismissDialog();     // Dismiss the dialog.
                    }
                })
                .show();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(mBinding.drawingView.onSaveInstanceState());
    }

    private String repeatText(String text, int n) {
        StringBuilder repeatedText = new StringBuilder();
        for (int i = 0; i < n; i++) {
            repeatedText.append(text);
        }
        return repeatedText.toString();
    }

    private class ScaleListner extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(MIN_SCALE, Math.min(scaleFactor, MAX_SCALE));
            mBinding.drawingView.setScaleX(scaleFactor);
            mBinding.drawingView.setScaleY(scaleFactor);

            return true;
        }
    }

    private void captureScreen() {
        Bitmap screenshot = takeScreenshot();
        showScreenShortDialog(screenshot);
    }

    private Bitmap takeScreenshot() {
        View rootView = getWindow().getDecorView().getRootView();
        rootView.setDrawingCacheEnabled(true);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        Bitmap screenshot = Bitmap.createBitmap(rootView.getDrawingCache(), 0, 0, width, height);
        rootView.setDrawingCacheEnabled(false);
        return screenshot;
    }

    private void showScreenShortDialog(Bitmap screenshotFile) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.share_screen_short_layout);
        ImageView imageView = dialog.findViewById(R.id.shareImg);
        Button shareBtn = dialog.findViewById(R.id.shareImgBtn);
        Button cancelBtn = dialog.findViewById(R.id.cancel);
        //TextView imageDetails = dialog.findViewById(R.id.imageDetails);

        imageView.setImageBitmap(screenshotFile);

        // Set the full path in the imageDetails TextView
        File screenshotsDir = new File(Environment.getExternalStorageDirectory(), "Screenshots");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String filename = "Screenshot_" + timestamp + ".png";
        File screenshotFileName = new File(screenshotsDir, filename);
       // imageDetails.setText("Path: " + screenshotFileName.getAbsolutePath());

        shareBtn.setOnClickListener(view -> {
            shareScreenShot(screenshotFile);
            dialog.dismiss();
        });
        cancelBtn.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    private void shareScreenShot(Bitmap screenshot) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        screenshot.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), screenshot, "Screenshot", null);
        Uri screenshotUri = Uri.parse(path);
        shareIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        startActivity(Intent.createChooser(shareIntent, "Share Screenshot via..."));
    }

    private void showPaintSizeDialog() {
        final int[] sizes = new int[81];
        for (int i = 0; i < 81; i++) {
            sizes[i] = 1 + i;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Paint Size");
        builder.setItems(intArrayToStringArray(sizes), (dialog, which) -> {
            int newSize = sizes[which];
            mBinding.drawingView.setPaintSize(newSize); // Set the selected paint size
        });
        builder.show();
    }

    private String[] intArrayToStringArray(int[] intArray) {
        String[] stringArray = new String[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            stringArray[i] = String.valueOf(intArray[i]);
        }
        return stringArray;
    }

    private void eraseDrawing() {
        // Set the color to white for erasing
        setDrawingView(Color.WHITE);
    }

    private void setDrawingView(int color) {
        mBinding.drawingView.setColor(color);
    }
}