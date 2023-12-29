package com.main.UserAccount.Database;

import com.main.UserAccount.model.AccountManger;
import com.main.UserAccount.model.UserAccount;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMangerInMemoryDB implements AccountMangerDB {
    List<AccountManger> accountMangerList;

    public AccountMangerInMemoryDB() {
        this.accountMangerList = new ArrayList<>() ;
        accountMangerList.add(new AccountManger(20210038, 1000));
        accountMangerList.add(new AccountManger(20211024, 2000));

    }

    @Override
    public Double getBalance(int accountId) {
        for (AccountManger accountManger : accountMangerList) {
            if (accountManger.getUserAccountID() == accountId) {
                return accountManger.getBalance();
            }
        }
        return null;
    }

    @Override
    public void setBalance(int accountId, double balance) {
        for (AccountManger accountManger : accountMangerList) {
            if (accountManger.getUserAccountID() == accountId) {
                accountManger.setBalance(balance);
            }
        }
    }

    @Override
    public AccountManger getAccount(int accountId) {
        for (AccountManger accountManger : accountMangerList) {
            if (accountManger.getUserAccountID() == accountId) {
                return accountManger;
            }
        }
        return null;
    }



    @Override
    public void addAccountManger(AccountManger accountManger) {
        accountMangerList.add(accountManger);
    }

    @Override
    public void removeAccountManger(AccountManger accountManger) {
        accountMangerList.remove(accountManger);
    }
}
