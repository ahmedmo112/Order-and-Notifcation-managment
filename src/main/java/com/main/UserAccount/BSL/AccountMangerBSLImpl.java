package com.main.UserAccount.BSL;

import com.main.UserAccount.Database.AccountMangerDB;
import org.springframework.stereotype.Service;

@Service
public class AccountMangerBSLImpl implements AccountMangerBSL {
    AccountMangerDB accountMangerDB;

    public AccountMangerBSLImpl(AccountMangerDB accountmangerDB) {
        this.accountMangerDB = accountmangerDB;
    }
    @Override
    public boolean deduct(int accountId, double amount) {
        if (accountMangerDB.getBalance(accountId) < amount) {
            return false;
        }
        accountMangerDB.setBalance(accountId, accountMangerDB.getBalance(accountId) - amount);
        return  true;
    }

    @Override
    public double getBalance(int accountId) {
        if(accountMangerDB.getAccount(accountId)!=null)
            return accountMangerDB.getBalance(accountId);

        return -1.0;
    }
}
