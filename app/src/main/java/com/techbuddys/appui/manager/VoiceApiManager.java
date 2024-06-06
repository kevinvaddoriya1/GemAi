package com.techbuddys.appui.manager;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class VoiceApiManager {
    private static final String API_KEY = Constants.getApi();
    private static final String BASE_URL = "https://api.openai.com/v1/audio/speech";
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public void sendPrompt(String Prompt, Callback callback){
        JSONObject requestBody = new JSONObject();
        try{
            requestBody.put("model","tts-1");
            requestBody.put("input",Prompt);
            requestBody.put("voice","alloy");

        } catch (JSONException e) {
            throw new RuntimeException(e);
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
