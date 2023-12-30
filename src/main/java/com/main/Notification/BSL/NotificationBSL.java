package com.main.Notification.BSL;

import com.main.Notification.model.Notification;

import java.util.List;

public interface NotificationBSL {
    public void pushNotification(Notification notification, int userId);

    public List<Notification> retrieveNotification(int userId);

}
