package com.main.Notification.Database;

import com.main.Notification.model.Notification;
import com.main.Notification.model.NotificationManagement;
import org.springframework.stereotype.Component;

import java.util.List;


public interface NotificationDB {
    public List<Notification> getNotification(int userId);
    public void addNotification(int userId, Notification notification);
    public void removeNotification(int userId, Notification notification);
}
