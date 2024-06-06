package com.techbuddys.appui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.techbuddys.appui.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", SplashActivity.this.MODE_PRIVATE);
        boolean isIntroductionCompleted = sharedPreferences.getBoolean("introFinish", false);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (isIntroductionCompleted){
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }else {
                    intent = new Intent(SplashActivity.this, IntroducationActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}