package com.main.UserAccount.Database;


import com.main.UserAccount.model.UserAccount;

import java.util.List;

public interface UserDB {
    public UserAccount getAccountByEmail(String email) ;
    public UserAccount getAccountByID(int ID);
    public void addUserAccount(UserAccount newUserAccount);
    public void removeUserAccount(UserAccount userAccountToRemove);
    public boolean checkIsExist(String email) ;
    public List<UserAccount> getAccounts();
}
