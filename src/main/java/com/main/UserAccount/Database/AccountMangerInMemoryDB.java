package com.main.UserAccount.Database;

import com.main.UserAccount.model.AccountManger;

import java.util.List;

public class AccountMangerInMemoryDB implements AccountMangerDB {
    List<AccountManger> accountMangerList;

    @Override
    public List<AccountManger> getPayment() {
        return null;
    }

    @Override
    public AccountManger getAccount(String email) {
        return null;
    }

    @Override
    public AccountManger getAccount(int id) {
        return null;
    }

    @Override
    public void addAccountManger(AccountManger accountManger) {

    }

    @Override
    public void removeAccountManger(AccountManger accountManger) {

    }
}
