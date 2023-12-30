package com.main.Notification.model;

import com.main.UserAccount.model.UserAccount;

import java.util.List;

public class NotificationManagement {
    private List<Notification> notifications;
    private int userId;

    public NotificationManagement(List<Notification> notifications, int userId) {
        this.notifications = notifications;
        this.userId = userId;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

}
