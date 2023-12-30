package com.main.Notification.model;

public class Notification {
    private NotificationTemplate template;
    private NotificationChannels channel;
    private String subject;


    public void setTemplate(NotificationTemplate template) {
        this.template = template;
    }

    public void setChannel(NotificationChannels channel) {
        this.channel = channel;
    }



    public void setSubject(String subject) {
        this.subject = subject;
    }



    public NotificationTemplate getTemplate() {
        return template;
    }

    public NotificationChannels getChannel() {
        return channel;
    }


    public String getSubject() {
        return subject;
    }

}
