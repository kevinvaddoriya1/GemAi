package com.techbuddys.appui.model;

public class TopicModel {
    private String topic_id;
    private String topic_name;
    public TopicModel(String topic_id, String topic_name) {
        this.topic_id = topic_id;
        this.topic_name = topic_name;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getId() {
        return topic_id;
    }

    public String getName() {
        return topic_name;
    }
}
