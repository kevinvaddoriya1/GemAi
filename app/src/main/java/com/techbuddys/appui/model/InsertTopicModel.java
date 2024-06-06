package com.techbuddys.appui.model;

import com.google.gson.annotations.SerializedName;

public class InsertTopicModel {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("topic_id")
    private int topicId;

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

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public InsertTopicModel(boolean error, String message, int topicId) {
        this.error = error;
        this.message = message;
        this.topicId = topicId;
    }
}
