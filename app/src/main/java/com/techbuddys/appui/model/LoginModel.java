package com.techbuddys.appui.model;

public class LoginModel {
    boolean error;
    String message;
    UserModel user;

    public LoginModel(boolean error, String message, UserModel user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public LoginModel() {}

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        user = user;
    }
}

