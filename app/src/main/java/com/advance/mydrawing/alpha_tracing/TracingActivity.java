package com.advance.mydrawing.alpha_tracing;

import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.advance.mydrawing.databinding.TracingActivityBinding;

import pk.farimarwat.abckids.AbcdkidsListener;

/**
 * Created by Zulfikar on 29 Jan 2024 at 11:39.
 */
public class TracingActivity extends AppCompatActivity {
    private TracingActivityBinding mBinding;
    int width = 500;
    int height = 500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = TracingActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.tlview.setLetter("B", width, height);
        Path path = new Path();
        path.moveTo(width * 0.19f, height * 0.9f);
        path.lineTo(width * 0.45f, height * 0.08f);

        path.moveTo(width * 0.46f, height * 0.08f);
        path.lineTo(width * 0.76f, height * 0.9f);

        path.moveTo(width * 0.32f, height * 0.62f);
        path.lineTo(width * 0.62f, height * 0.62f);

        mBinding.tlview.setLetter(path);
        mBinding.tlview.addListener(new AbcdkidsListener() {
            @Override
            public void onDotTouched(float progress) {
                Toast.makeText(getApplicationContext(), "progress" + progress, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSegmentFinished() {
                Toast.makeText(getApplicationContext(), "Segment Finished", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTraceFinished() {
                Toast.makeText(getApplicationContext(), "Tracing completed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
