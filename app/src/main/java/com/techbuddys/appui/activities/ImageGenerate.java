package com.techbuddys.appui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.techbuddys.appui.R;
import com.techbuddys.appui.manager.ApiManager;
import com.techbuddys.appui.manager.ImageGenrateApi;
import com.techbuddys.appui.manager.SharedPrefManager;
import com.techbuddys.appui.model.Image;
import com.techbuddys.appui.model.LikeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ImageGenerate extends AppCompatActivity {

    EditText edtPrompt;

    ImageView send,result,like;
    boolean isLiked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_to_pdf);

        edtPrompt = findViewById(R.id.edt_prompt);
        send = findViewById(R.id.btnSendPromp);
        result = findViewById(R.id.result);
        like = findViewById(R.id.like);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like.setVisibility(View.GONE);
                like.setImageResource(R.drawable.like_white);
                ImageGenrateApi  imageGenrateApi = new ImageGenrateApi();
                String prompt = edtPrompt.getText().toString().trim();
                imageGenrateApi.sendPrompt(prompt, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected response code: " + response);
                        }

                        String responseData = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonResponse = new JSONObject(responseData);
                                    JSONArray dataArray = jsonResponse.getJSONArray("data");
                                    JSONObject imageData = dataArray.getJSONObject(0); // Assuming there's only one image in the response
                                    String imageUrl = imageData.getString("url");
                                    // Load image into ImageView using Picasso
                                    retrofit2.Call<LikeModel> call = ApiManager.getInstance().apiInterface.saveImage(imageUrl, SharedPrefManager.getUser().getU_id(),0);
                                    call.enqueue(new retrofit2.Callback<LikeModel>() {
                                        @Override
                                        public void onResponse(retrofit2.Call<LikeModel> call, retrofit2.Response<LikeModel> response) {

                                        }

                                        @Override
                                        public void onFailure(retrofit2.Call<LikeModel> call, Throwable t) {

                                        }
                                    });
                                    Picasso.get().load(imageUrl).into(result, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            like.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onError(Exception e) {

                                        }
                                    });

                                    like.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
//                                            retrofit2.Call<LikeModel> call = ApiManager.getInstance().apiInterface.updateLikes(imageUrl,SharedPrefManager.getUser().getU_id(), 1);
                                            if (!isLiked) {
                                                // Perform task on the first click
                                                retrofit2.Call<LikeModel> call = ApiManager.getInstance().apiInterface.updateLikes(imageUrl, SharedPrefManager.getUser().getU_id(), 1);
                                                call.enqueue(new retrofit2.Callback<LikeModel>() {
                                                    @Override
                                                    public void onResponse(retrofit2.Call<LikeModel> call, retrofit2.Response<LikeModel> response) {

                                                    }

                                                    @Override
                                                    public void onFailure(retrofit2.Call<LikeModel> call, Throwable t) {

                                                    }
                                                });
                                                isLiked = true;
                                                like.setImageResource(R.drawable.like);
                                            } else {
                                                // Perform task on the second click
                                                retrofit2.Call<LikeModel> call = ApiManager.getInstance().apiInterface.updateLikes(imageUrl, SharedPrefManager.getUser().getU_id(), 0);
                                                call.enqueue(new retrofit2.Callback<LikeModel>() {
                                                    @Override
                                                    public void onResponse(retrofit2.Call<LikeModel> call, retrofit2.Response<LikeModel> response) {

                                                    }

                                                    @Override
                                                    public void onFailure(retrofit2.Call<LikeModel> call, Throwable t) {

                                                    }
                                                });
                                                like.setImageResource(R.drawable.like_white);
                                                isLiked = false;

                                            }
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }

        });
    }
}