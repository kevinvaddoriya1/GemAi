package com.techbuddys.appui.manager;

import android.app.Application;

public class AppControl extends Application {
    public static AppControl context;
    public static AppControl getContext() {
        return context;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        SharedPrefManager.init();
    }
}
