package com.techbuddys.appui.model;

public class RegisterModel {
    String message;
    boolean error;

    public RegisterModel(String message, boolean error) {
        this.message = message;
        this.error = error;
    }

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
}
