package com.main.APISchemas;

public class NotificationSchema {
    private String channel;
    private String language;

    public NotificationSchema(String channel, String language) {
        this.channel = channel;
        this.language = language;

    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getChannel() {
        return channel;
    }

    public String getLanguage() {
        return language;
    }

}
