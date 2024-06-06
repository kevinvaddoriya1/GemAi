package com.techbuddys.appui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.techbuddys.appui.fragments.HistoryFragment;
import com.techbuddys.appui.fragments.HomeFragment;
import com.techbuddys.appui.R;
import com.techbuddys.appui.fragments.SettingsFragment;
import com.techbuddys.appui.adapters.ViewPagerAdapter;


public class DashboardActivity extends AppCompatActivity {

    RelativeLayout homeLayout, historyLayout, settingsLayout;
    ImageView homeimg, historyimg, settingimg;
    ViewPager fragmentViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        settingsLayout = findViewById(R.id.settingsLayout);
        homeLayout = findViewById(R.id.homeLayout);
        historyLayout = findViewById(R.id.historyLayout);

        settingimg = findViewById(R.id.settingimg);
        homeimg = findViewById(R.id.homeimg);
        historyimg = findViewById(R.id.historyimg);
        fragmentViewPager = findViewById(R.id.fragmentViewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new HistoryFragment());
        adapter.addFragment(new SettingsFragment());

        homeClicked();

        fragmentViewPager.setAdapter(adapter);

        fragmentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    homeClicked();
                } else if (position == 1) {
                    historyClicked();
                    HistoryFragment historyFragment = (HistoryFragment) adapter.getItem(position);
                    if (historyFragment != null) {
                        historyFragment.refreshFragment();
                    }
                } else {
                    settingsClicked();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change to Home Fragment
                fragmentViewPager.setCurrentItem(0);
                homeClicked();
            }
        });
        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change to History Fragment
                fragmentViewPager.setCurrentItem(1);
                historyClicked();
            }
        });
        settingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change to Settings Fragment
                fragmentViewPager.setCurrentItem(2);
                settingsClicked();
            }
        });

    }

    void homeClicked() {
        homeimg.setBackground(DashboardActivity.this.getDrawable(R.drawable.btnbg));
        historyimg.setBackground(DashboardActivity.this.getDrawable(R.color.Backgroundcolor));
        settingimg.setBackground(DashboardActivity.this.getDrawable(R.color.Backgroundcolor));
    }

    void historyClicked() {
        historyimg.setBackground(DashboardActivity.this.getDrawable(R.drawable.btnbg));
        homeimg.setBackground(DashboardActivity.this.getDrawable(R.color.Backgroundcolor));
        settingimg.setBackground(DashboardActivity.this.getDrawable(R.color.Backgroundcolor));
    }

    void settingsClicked() {
        settingimg.setBackground(DashboardActivity.this.getDrawable(R.drawable.btnbg));
        historyimg.setBackground(DashboardActivity.this.getDrawable(R.color.Backgroundcolor));
        homeimg.setBackground(DashboardActivity.this.getDrawable(R.color.Backgroundcolor));
    }

}