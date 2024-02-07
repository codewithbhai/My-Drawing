package com.advance.mydrawing;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.advance.mydrawing.databinding.TestActivityBinding;

import java.util.ArrayList;

/**
 * Created by Zulfikar on 12 Jan 2024 at 14:53.
 */
public class TestClassActivity extends AppCompatActivity {
    private TestActivityBinding mBinding;
    int delay = 2000; //milliseconds
    Handler handler;
    ArrayList<String> arrMessages = new ArrayList<>();
    int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = TestActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        arrMessages.add("Hello, Zulfikar!");
        arrMessages.add("Hello, Best!");
        arrMessages.add("Codewithbhai.com");
        arrMessages.add("By ZULFIKAR ANSARI");
        arrMessages.add("@Jaadu");
        arrMessages.add("Coding Ninza");
        arrMessages.add("Support Us");

        mBinding.textView.animateText(arrMessages.get(position));
        mBinding.textViewScale.animateText(arrMessages.get(position));
        mBinding.textViewRainBow.animateText(arrMessages.get(position));
        mBinding.textViewTyper.animateText(arrMessages.get(position));
        mBinding.textViewFade.animateText(arrMessages.get(position));
        mBinding.textViewLine.animateText(arrMessages.get(position));
        position++;

        /* Change Messages every 2 Seconds */
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                handler.postDelayed(this, delay);
                if (position >= arrMessages.size())
                    position = 0;
                mBinding.textView.animateText(arrMessages.get(position));
                mBinding.textViewScale.animateText(arrMessages.get(position));
                mBinding.textViewRainBow.animateText(arrMessages.get(position));
                mBinding.textViewTyper.animateText(arrMessages.get(position));
                mBinding.textViewFade.animateText(arrMessages.get(position));
                mBinding.textViewLine.animateText(arrMessages.get(position));
                position++;
            }
        }, delay);

    }
}
