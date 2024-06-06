package com.techbuddys.appui.manager;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.http.POST;

public class ImageGenrateApi {
    private static final String API_KEY = Constants.getApi();
    private static final String BASE_URL = "https://api.openai.com/v1/images/generations";
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public void sendPrompt(String prompt, Callback callback){
        JSONObject requestBody = new JSONObject();
        try{
            requestBody.put("model", "dall-e-2");
            requestBody.put("prompt", prompt);
            requestBody.put("n", 1);
            requestBody.put("size", "512x512");
        } catch (Exception e) {
            Log.e("JSONError", "Failed to create JSON request body: " + e.getMessage());
            return;
        }
        RequestBody body = RequestBody.create(requestBody.toString(),JSON);

        Request request = new Request.Builder()
                .url(BASE_URL)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+API_KEY) // Replace YOUR_API_KEY with your actual API key
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
