package com.main.UserAccount.Database;

import com.main.UserAccount.model.AccountManger;

import java.util.List;

public interface AccountMangerDB {
    
    public Double getBalance(int accountId);
    public void setBalance(int accountId, double balance);
    public AccountManger getAccount(int accountId);
    public void addAccountManger (AccountManger accountManger);
    public void removeAccountManger (AccountManger accountManger);

}
