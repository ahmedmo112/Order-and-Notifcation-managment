package com.main.UserAccount.Database;

import com.main.UserAccount.model.AccountManger;

import java.util.List;

public interface AccountMangerDB {
    
    public List<AccountManger> getPayment();
    public AccountManger getAccount(String email);
    public AccountManger getAccount(int id);
    public void addAccountManger (AccountManger accountManger);
    public void removeAccountManger (AccountManger accountManger);

}
