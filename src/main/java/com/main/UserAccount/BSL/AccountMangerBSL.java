package com.main.UserAccount.BSL;

public interface AccountMangerBSL {
    public boolean deduct(int accountId, double amount);
    public double getBalance(int accountId) ;
}
