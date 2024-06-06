package com.techbuddys.appui.manager;


import com.techbuddys.appui.interfaces.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static final String url = "http://192.168.29.112/GemAi/";
    private static ApiManager clientObj;
    Retrofit retrofit;
    public ApiInterface apiInterface;

    ApiManager() {

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiManager getInstance() {
        if (clientObj == null) {
            clientObj = new ApiManager();
        }
        return clientObj;
    }
}
