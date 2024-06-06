package com.techbuddys.appui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.techbuddys.appui.R;
import com.techbuddys.appui.activities.ImageGenerate;
import com.techbuddys.appui.activities.TextToVoice;
import com.techbuddys.appui.activities.BotChatActivity;
import com.techbuddys.appui.activities.DocToPdfActivity;
import com.techbuddys.appui.activities.ImageToTextActivity;

public class HomeFragment extends Fragment {

    RelativeLayout chatWithBotLayout, imagetotxtLayout, voicetotxtLayout,imagetopdfLayout,doctopdflayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        chatWithBotLayout = view.findViewById(R.id.chatWithBotLayout);
        imagetotxtLayout = view.findViewById(R.id.imagetotxtLayout);
        voicetotxtLayout = view.findViewById(R.id.voicetotxtLayout);
        imagetopdfLayout = view.findViewById(R.id.imagetopdfLayout);
        doctopdflayout = view.findViewById(R.id.doctopdflayout);

        chatWithBotLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BotChatActivity.class);
                startActivity(intent);
            }
        });
        imagetotxtLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ImageToTextActivity.class);
                startActivity(intent);
            }
        });

        voicetotxtLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TextToVoice.class);
                startActivity(intent);
            }
        });

        imagetopdfLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ImageGenerate.class);
                startActivity(intent);
            }
        });

        doctopdflayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DocToPdfActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }
}