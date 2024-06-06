package com.techbuddys.appui.model;

public class ConversationModel {
    String content;
    String bot_response;

    public ConversationModel(String content, String bot_response) {
        this.content = content;
        this.bot_response = bot_response;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBot_response() {
        return bot_response;
    }

    public void setBot_response(String bot_response) {
        this.bot_response = bot_response;
    }
}
