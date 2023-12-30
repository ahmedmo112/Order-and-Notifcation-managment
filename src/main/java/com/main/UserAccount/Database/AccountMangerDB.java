package com.main.UserAccount.Database;

import com.main.UserAccount.model.AccountManager;

import java.util.List;

public interface AccountMangerDB {
    
    public List<AccountManager> getPayment();
    public AccountManager getAccount(String email);
    public AccountManager getAccount(int id);
    public void addAccountManger (AccountManager accountManger);
    public void removeAccountManger (AccountManager accountManger);

}
