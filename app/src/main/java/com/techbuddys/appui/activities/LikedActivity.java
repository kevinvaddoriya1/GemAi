package com.techbuddys.appui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.techbuddys.appui.R;
import com.techbuddys.appui.adapters.LikedImagesAdapter;
import com.techbuddys.appui.manager.ApiManager;
import com.techbuddys.appui.manager.SharedPrefManager;
import com.techbuddys.appui.model.Image;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikedActivity extends AppCompatActivity {

    ImageButton likebackarrow;
    private RecyclerView recyclerView;
    private LikedImagesAdapter adapter;

    @SuppressLint("MissingInflatedId")

    @Override
    public void onBackPressed() {
        // Start DashboardActivity when back button is pressed
        super.onBackPressed();
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish(); // Finish current activity to prevent going back to it when back button is pressed again
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_liked);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        likebackarrow = findViewById(R.id.likedHistory);
        likebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recycler_view_likes);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        fetchLikedImages();
    }

    private void fetchLikedImages() {
        Call<List<Image>> call = ApiManager.getInstance().apiInterface.getLikedImages(SharedPrefManager.getUser().getU_id(), 1);
        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    List<Image> likedImages = response.body();
                    if (likedImages != null) {
                        // Process the list of liked images
                        adapter = new LikedImagesAdapter(LikedActivity.this,likedImages);
                        recyclerView.setAdapter(adapter);
                        for (Image image : likedImages) {
                            Log.d("@@@@@@", "Image URL: " + image.getUrl());
                            Log.d("@@@@@@", "Image Path: " + image.getImg_path());
                            // You can access other properties of the image here
                        }
                    }
                } else {
                    // Handle unsuccessful response
                    Log.d("@@@@@@","nai");

                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {

            }
        });

    }


}