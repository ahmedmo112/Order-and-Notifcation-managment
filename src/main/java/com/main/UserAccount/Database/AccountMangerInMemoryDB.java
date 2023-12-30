package com.main.UserAccount.Database;

import com.main.UserAccount.model.AccountManager;

import java.util.List;

public class AccountMangerInMemoryDB implements AccountMangerDB {
    List<AccountManager> accountMangerList;

    @Override
    public List<AccountManager> getPayment() {
        return null;
    }

    @Override
    public AccountManager getAccount(String email) {
        return null;
    }

    @Override
    public AccountManager getAccount(int id) {
        return null;
    }

    @Override
    public void addAccountManger(AccountManager accountManger) {

    }

    @Override
    public void removeAccountManger(AccountManager accountManger) {

    }
}
