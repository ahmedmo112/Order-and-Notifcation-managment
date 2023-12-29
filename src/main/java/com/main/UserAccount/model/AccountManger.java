package com.main.UserAccount.model;

public class AccountManger {
     private int userAccountID ;
     private double balance ;

    public AccountManger() {

    }
    public AccountManger(int userAccountID, double balance) {
        this.userAccountID = userAccountID;
        this.balance = balance;
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
}
