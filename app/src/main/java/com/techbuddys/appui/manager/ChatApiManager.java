package com.techbuddys.appui.manager;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ChatApiManager {
    private static final String API_KEY = Constants.getApi();

    private static final String BASE_URL = "https://api.openai.com/v1/chat/completions";
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public void sendMessage(String message, Callback callback) {

        JSONObject requestBody = new JSONObject();
        JSONArray messagesArray = new JSONArray();
        try {
            // System message
            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a helpful assistant.");
            messagesArray.put(systemMessage);

            // User message
            JSONObject userMessageObj = new JSONObject();
            userMessageObj.put("role", "user");
            userMessageObj.put("content", message);
            messagesArray.put(userMessageObj);

            // Add messages array to the request body
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("messages", messagesArray);

        } catch (JSONException e) {
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
