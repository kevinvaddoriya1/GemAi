package com.techbuddys.appui.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.techbuddys.appui.R;

public class LoadDialog {

    private Activity activity;
    private AlertDialog dialog;

    // constructor of dialog class
    // with parameter activity
    public LoadDialog(Activity myActivity) {

        activity = myActivity;
    }

    @SuppressLint("InflateParams")
    public void startLoadingdialog() {

        // adding ALERT Dialog builder object and passing activity as parameter
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // layoutinflater object and use activity to get layout inflater
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    // dismiss method
   public void dismissdialog() {
        dialog.dismiss();
    }

//    public LoadDialog(@NonNull Context context) {
//        super(context);
//
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.gravity = Gravity.CENTER_HORIZONTAL;
//        getWindow().setAttributes(params);
//
//        setTitle(null);
//        setCancelable(false);
//        setOnCancelListener(null);
//        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog,null);
//        setContentView(view);
//    }
}
