package com.techbuddys.appui.model;

import android.graphics.Bitmap;

public class ImageToTextModel {
    private String text;
    private boolean isSentByCurrentUser;
    private Bitmap bitmap;

    public ImageToTextModel(String text, boolean isSentByCurrentUser, Bitmap bitmap) {
        this.text = text;
        this.isSentByCurrentUser = isSentByCurrentUser;
        this.bitmap = bitmap;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSentByCurrentUser(boolean sentByCurrentUser) {
        isSentByCurrentUser = sentByCurrentUser;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getText() {
        return text;
    }

    public boolean isSentByCurrentUser() {
        return isSentByCurrentUser;
    }
}
