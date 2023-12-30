package com.main.Notification.BSL;

import com.main.Notification.Database.NotificationDB;
import com.main.Notification.model.Notification;

import java.util.List;

public class NotificationBSLImpl implements NotificationBSL{
    private NotificationDB notificationDB;

    @Override
    public void pushNotification(Notification notification, int userId) {

        notificationDB.addNotification(userId, notification);
    }

    @Override
    public List<Notification> retrieveNotification(int userId) {

        return notificationDB.getNotification(userId);

    }

public NotificationBSLImpl(NotificationDB notificationDB) {

        this.notificationDB = notificationDB;
    }
}
