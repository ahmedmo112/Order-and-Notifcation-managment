package com.main.Notification.BSL;

import com.main.Notification.Database.NotificationDB;
import com.main.Notification.model.Notification;

import java.util.List;

import com.main.Notification.model.NotificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
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

    @Override
    public void removeNotification(int userId, int notificationId) {

            notificationDB.removeNotification(userId, notificationId);
    }

    @Autowired
public NotificationBSLImpl(@Qualifier("notificationInMemoryDB") NotificationDB notificationDB) {

        this.notificationDB = notificationDB;
    }
}
