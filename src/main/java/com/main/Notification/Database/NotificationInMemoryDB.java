package com.main.Notification.Database;

import com.main.Notification.model.Notification;
import com.main.Notification.model.NotificationManagement;
import com.main.UserAccount.model.UserAccount;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationInMemoryDB implements NotificationDB {
    private List<NotificationManagement> notifications;

    public NotificationInMemoryDB() {

        this.notifications = new ArrayList<NotificationManagement>();
    }

    public List<Notification> getNotification(int userId) {
        for (NotificationManagement notificationManagement : notifications) {
            if (notificationManagement.getUserId() == userId) {
                return notificationManagement.getNotifications();
            }
        }
        return null;
    }

    public void addNotification(int userId, Notification notification) {
        for (NotificationManagement notificationManagement : notifications) {
            if (notificationManagement.getUserId() == userId) {
                notificationManagement.getNotifications().add(notification);
                return;
            }
        }
        List<Notification> notificationList = new ArrayList<Notification>();
        notificationList.add(notification);
        notifications.add(new NotificationManagement(notificationList, userId));
    }

    public void removeNotification(int userId, Notification notification) {
        for (NotificationManagement notificationManagement : notifications) {
            if (notificationManagement.getUserId() == userId) {
                notificationManagement.getNotifications().remove(notification);
                return;
            }
        }
    }

}
