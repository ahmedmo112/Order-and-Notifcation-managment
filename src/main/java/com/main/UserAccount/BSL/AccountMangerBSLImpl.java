package com.main.UserAccount.BSL;

import com.main.Notification.model.NotificationChannels;
import com.main.UserAccount.Database.AccountMangerDB;
import com.main.UserAccount.Database.UserDB;
import com.main.UserAccount.model.AccountManger;
import com.main.UserAccount.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountMangerBSLImpl implements AccountMangerBSL {
    AccountMangerDB accountMangerDB;
    UserDB userDB;

    @Autowired
    public AccountMangerBSLImpl(@Qualifier("accountMangerInMemoryDB") AccountMangerDB accountMangerDB, @Qualifier("userInMemoryDB") UserDB userDB) {

        this.accountMangerDB = accountMangerDB;
        this.userDB = userDB;
    }

    @Override
    public UserAccount getUserAccount(int accountId) {

        for(UserAccount userAccount : userDB.getAccounts()){
            if(userAccount.getId() == accountId){
                return userAccount;
            }
        }
        return null;
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

    @Override
    public NotificationChannels getNotificationChannel(int accountId) {
        return accountMangerDB.getNotificationChannel(accountId);
    }
}
