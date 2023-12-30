package com.main.UserAccount.model;

public class AccountManager {
     private UserAccount userAccount ;
     private double balance ;


    public AccountManager(UserAccount userAccount, double balance) {
        this.userAccount = userAccount;
        this.balance = balance;
    }

    public AccountManager(AccountManager accountManger){
        this.userAccount = accountManger.userAccount;
        this.balance = accountManger.balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public double getBalance() {
        return balance;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }
}
