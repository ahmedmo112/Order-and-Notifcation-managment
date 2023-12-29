package com.main.UserAccount.Database;

import com.main.UserAccount.model.UserAccount;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserInMemoryDB implements UserDB{
     List<UserAccount> users ;

     public UserInMemoryDB(){
          users = new ArrayList<>();
          users.add(new UserAccount("Ahmed Mohamed","amhk11827@gmail.com","Ahany@123","17 Mahmoud Ramadan","01009934222",20210038));
          users.add(new UserAccount("Tawfik Mohamed","tawfik040@gmail.com","Tawfik@123","Pyramides Gardern","01270028189",20211024));

     }

     @Override
     public UserAccount getAccountByEmail(String email) {
            for (UserAccount user : users) {
                 if (user.getEmail().equals(email)) {
                        return user;
                 }
            }
            return null;
     }

     @Override
     public UserAccount getAccountByID(String ID) {
            for (UserAccount user : users) {
                 if (user.getId().equals(ID)) {
                        return user;
                 }
            }
            return null;
     }

     @Override
     public void addUserAccount(UserAccount newUserAccount) {
          users.add(newUserAccount);
     }

     @Override
     public void removeUserAccount(UserAccount userAccountToRemove) {
            users.remove(userAccountToRemove);
     }

     @Override
     public boolean checkIsExist(String email) {
            for (UserAccount user : users) {
                 if (user.getEmail().equals(email)) {
                        return true;
                 }
            }
            return false;
     }

     @Override
     public List<UserAccount> getAccounts() {
            return users;
     }
}
