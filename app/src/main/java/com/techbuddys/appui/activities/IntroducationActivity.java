package com.techbuddys.appui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.techbuddys.appui.adapters.IntroSliderAdapter;
import com.techbuddys.appui.R;

public class IntroducationActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    IntroSliderAdapter sliderAdapter;
    Button btnNext, btnStart;
    TextView btnSkip;
    LinearLayout indicatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducation);
        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
        btnStart = findViewById(R.id.btnStart);
        indicatorLayout = findViewById(R.id.indicatorLayout);

        int[] slideImages = {R.drawable.firstimg, R.drawable.secoundimg, R.drawable.thirdimg};
        String[] slideTexts = {"Image Inside Aliments in \n a Text Converter","Speak To Generator Text", "Chat With Bot"};


        sliderAdapter = new IntroSliderAdapter(slideImages, slideTexts);
        viewPager.setAdapter(sliderAdapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() + 1 < sliderAdapter.getItemCount()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishIntroduction();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishIntroduction();
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == sliderAdapter.getItemCount() - 1) {
                    btnNext.setVisibility(View.GONE);
                    btnSkip.setVisibility(View.GONE);
                    btnStart.setVisibility(View.VISIBLE);
                } else {
                    btnNext.setVisibility(View.VISIBLE);
                    btnSkip.setVisibility(View.VISIBLE);
                    btnStart.setVisibility(View.GONE);
                }
            }
        });

        for (int i = 0; i < sliderAdapter.getItemCount(); i++) {
            ImageView dot = new ImageView(this);
            dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.indicatoredot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);

            indicatorLayout.addView(dot);
        }

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < indicatorLayout.getChildCount(); i++) {
                    indicatorLayout.getChildAt(i).setSelected(i == position);
                }
            }
        });
    }

    private void finishIntroduction() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", IntroducationActivity.this.MODE_PRIVATE);
        boolean isIntroductionCompleted = sharedPreferences.getBoolean("introFinish", false);

        if (!isIntroductionCompleted) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("introFinish", true);
            editor.apply();
        }

        Intent intent = new Intent(IntroducationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}