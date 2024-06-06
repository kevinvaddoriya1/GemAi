package com.techbuddys.appui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.techbuddys.appui.R;
import com.techbuddys.appui.activities.LikedActivity;
import com.techbuddys.appui.activities.LoginActivity;
import com.techbuddys.appui.manager.SharedPrefManager;

public class SettingsFragment extends Fragment {


    RelativeLayout logoutlayout, sharewithfriendlayout, modelayout,like;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        logoutlayout = view.findViewById(R.id.logoutlayout);
        sharewithfriendlayout = view.findViewById(R.id.sharewithfriendlayout);
        modelayout = view.findViewById(R.id.modelayout);
        like = view.findViewById(R.id.likedimgs);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LikedActivity.class));
                getActivity().finish();;
            }
        });


        modelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        sharewithfriendlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appPackageName = getContext().getPackageName();
                String playStoreLink = "https://play.google.com/store/apps/details?id=" + appPackageName;

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my awesome app: " + playStoreLink);

                startActivity(Intent.createChooser(shareIntent, null));

            }
        });
        logoutlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.logout();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }
}