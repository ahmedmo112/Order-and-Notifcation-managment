package com.main.UserAccount.model;

import com.main.Notification.model.NotificationChannels;

public class AccountManger {
     private int userAccountID ;
     private double balance ;

     private NotificationChannels channel;

    public AccountManger() {

    }
    public AccountManger(int userAccountID, double balance,NotificationChannels channel) {
        this.userAccountID = userAccountID;
        this.balance = balance;
        this.channel = channel;
    }

    public AccountManger(AccountManger accountManger){
        this.userAccountID = accountManger.userAccountID;
        this.balance = accountManger.balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setUserAccount(int userAccountID) {
        this.userAccountID = userAccountID;
    }

    public double getBalance() {
        return balance;
    }

    public int getUserAccountID() {
        return userAccountID;
    }

    public void setChannel(NotificationChannels channel) {
        this.channel = channel;
    }

    public NotificationChannels getChannel() {
        return channel;
    }
}
