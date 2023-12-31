package com.main.Notification.Database;

import com.main.Notification.model.Notification;
import com.main.Notification.model.NotificationChannels;
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
            if (notificationManagement.getUserId()  == (userId)) {
                return notificationManagement.getNotifications();
            }
        }
        return null;
    }

    public void addNotification(int userId, Notification notification) {
        for (NotificationManagement notificationManagement : notifications) {
            if (notificationManagement.getUserId() == userId) {
                if (notification.getChannel().equals(NotificationChannels.ALL)){
                    for (NotificationChannels channel : NotificationChannels.values()) {
                        if (channel.equals(NotificationChannels.ALL)){
                            continue;
                        }
                        Notification notification1 = new Notification();
                        notification1.setSubject(notification.getSubject());
                        notification1.setTemplate(notification.getTemplate());
                        notification1.setChannel(channel);
                        notificationManagement.getNotifications().add(notification1);
                    }
                }else{
                    notificationManagement.getNotifications().add(notification);
                }

                return;
            }
        }
        List<Notification> notificationList = new ArrayList<Notification>();
        if (notification.getChannel().equals(NotificationChannels.ALL)){
            for (NotificationChannels channel : NotificationChannels.values()) {

                if (channel.equals(NotificationChannels.ALL)){
                    continue;
                }
                Notification notification1 = new Notification();
                notification1.setSubject(notification.getSubject());
                notification1.setTemplate(notification.getTemplate());
                notification1.setChannel(channel);

                notificationList.add(notification1);
            }
        }else{
            notificationList.add(notification);
        }
        notifications.add(new NotificationManagement(notificationList, userId));
    }

    public void removeNotification(int userId, int notificationId) {
        for (NotificationManagement notificationManagement : notifications) {
            if (notificationManagement.getUserId() == userId) {
                notificationManagement.removeNotification(notificationId);
                return;
            }
        }
    }

}
