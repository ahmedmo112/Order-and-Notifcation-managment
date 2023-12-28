package com.main.UserAccount.Database;

import com.main.UserAccount.model.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class UserInMemoryDB implements UserDB{
     List<UserAccount> users ;



     @Override
     public UserAccount getAccountByEmail(String email) {
          return null;
     }

     @Override
     public UserAccount getAccountByID(String ID) {
          return null;
     }

     @Override
     public void addUserAccount(UserAccount newUserAccount) {

     }

     @Override
     public void removeUserAccount(UserAccount userAccountToRemove) {

     }

     @Override
     public boolean checkIsExist(String email) {
          return false;
     }

     @Override
     public List<UserAccount> getAccounts() {
          return null;
     }
}
