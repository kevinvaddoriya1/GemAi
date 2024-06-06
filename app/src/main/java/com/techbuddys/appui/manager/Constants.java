package com.techbuddys.appui.manager;

public class Constants {
    static {
        System.loadLibrary("keys");
    }

    public static native String getApi();
    public static native String getGeminiApi();
}
