package com.main.Order.model;

import java.util.Timer;

public class NotificationTask {
    int notificationId;
    Timer timer;

    public NotificationTask(int notificationId, Timer timer) {
        this.notificationId = notificationId;
        this.timer = timer;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

}
