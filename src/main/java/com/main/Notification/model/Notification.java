package com.main.Notification.model;

public class Notification {
    private NotificationTemplate template;
    private NotificationChannels channel;
    private String subject;
    private int notificationId;


    public void setTemplate(NotificationTemplate template) {
        this.template = template;
    }

    public void setChannel(NotificationChannels channel) {
        this.channel = channel;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
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
