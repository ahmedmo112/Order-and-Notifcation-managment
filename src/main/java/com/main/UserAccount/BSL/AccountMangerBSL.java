package com.main.UserAccount.BSL;

import com.main.Notification.model.NotificationChannels;
import com.main.UserAccount.model.UserAccount;
import org.apache.catalina.User;

public interface AccountMangerBSL {

    public UserAccount getUserAccount(int accountId);
    public boolean deduct(int accountId, double amount);
    public double getBalance(int accountId) ;
    public NotificationChannels getNotificationChannel(int accountId);
}
