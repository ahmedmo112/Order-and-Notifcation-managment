package com.main.Notification.BSL;

import com.main.Notification.Database.NotificationDB;
import com.main.Notification.model.Notification;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.main.Notification.model.NotificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class NotificationBSLImpl implements NotificationBSL {
    private NotificationDB notificationDB;
    private Queue<CustomPair> notificationQueue;

    class CustomPair {
        public Notification notification;
        public Integer id;

    }


    @Override
    public void pushNotification(Notification notification, int userId) {

        notificationDB.addNotification(userId, notification);
    }


    @Override
    public void addToNotificationQueue(Notification notification, int userId) {
        CustomPair customPair = new CustomPair();
        customPair.notification = notification;
        customPair.id = userId;

        // Add notification and user id to notification queue
        notificationQueue.add(customPair);

    }

    @Override
    public Queue<Notification> getNotificationQueue() {
        Queue<Notification> notifications = new LinkedList<>();

        for (CustomPair pair : notificationQueue) {
            notifications.add(pair.notification);
        }
        return notifications;
    }

    // this function runs every 10 seconds and push the top of notification queue into notification database.
    @Scheduled(fixedRate = 10000)
    private void sendNotifications() {
//        while (!notificationQueue.isEmpty()) {
        CustomPair customPair = notificationQueue.poll();
        System.out.println("I AM HERE >>>>> " + notificationQueue.size());
        pushNotification(customPair.notification, customPair.id);
//        }
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
        this.notificationQueue = new LinkedList<>();
        this.notificationDB = notificationDB;
    }
}
