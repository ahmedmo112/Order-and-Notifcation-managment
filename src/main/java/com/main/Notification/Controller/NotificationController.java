package com.main.Notification.Controller;

import com.main.Notification.model.Notification;
import com.main.Notification.model.NotificationChannels;

import java.util.List;
import java.util.Map;

public interface NotificationController {
//    public Object addNotification(Map<String,String> notify);
    public Object retrieveNotification(String language);
    public Object retrieveNotificationQueue();
}
