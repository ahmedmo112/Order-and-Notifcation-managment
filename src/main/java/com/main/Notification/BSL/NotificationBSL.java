package com.main.Notification.BSL;

import com.main.Notification.model.Notification;

import java.util.List;
import java.util.Queue;

public interface NotificationBSL {
    public void pushNotification(Notification notification, int userId);
    public void addToNotificationQueue(Notification notification, int userId);
    public Queue<Notification> getNotificationQueue();
    public List<Notification> retrieveNotification(int userId);

    public void removeNotification(int userId, int notificationId);

}
