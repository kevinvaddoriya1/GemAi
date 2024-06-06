package com.techbuddys.appui.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.techbuddys.appui.model.UserModel;

public class SharedPrefManager {

    public static SharedPreferences.Editor editor;
    public static SharedPreferences sharedPreferences;

    public static void init() {
        SharedPreferences sharedPreferences;
        SharedPrefManager.sharedPreferences = sharedPreferences = AppControl.getContext().getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private static SharedPreferences sharedPreferences() {
        return AppControl.getContext().getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
    }

    public static void editor(String string2, int n) {
        editor.putInt(string2, n);
        editor.apply();
    }

    public static void editor(String string2, long l) {
        editor.putLong(string2, l);
        editor.apply();
    }

    public static void editor(String string2, String string3) {
        editor.putString(string2, string3);
        editor.apply();
    }

    public static void editor(String string2, boolean bl) {
        editor.putBoolean(string2, bl);
        editor.apply();
    }


    public static void saveUserCredentials(UserModel userModel) {
        editor("u_id", userModel.getU_id());
        editor("u_name", userModel.getU_name());
        editor("u_email", userModel.getU_email());
        editor("u_password", userModel.getU_password());
        editor("logged", true);
    }

    public static boolean isLoggedIn() {
        return sharedPreferences.getBoolean("logged", false);
    }
//    public static boolean isHistoryTopicClicked() {
//        return sharedPreferences.getBoolean("checkTopicClicked", false);
//    }
    public static int getTempTopicID() {
        return sharedPreferences.getInt("tempTopicID", -1);
    }


    public static UserModel getUser() {
        return new UserModel(sharedPreferences.getString("u_id", ""),
                sharedPreferences.getString("u_name", ""),
                sharedPreferences.getString("u_email", ""),
                sharedPreferences.getString("u_password", ""));
    }

    public static void logout() {
        editor.clear();
        editor.apply();
    }

    public static void clearTempTopicID() {
        editor.remove("tempTopicID");
        editor.apply();
    }
}
