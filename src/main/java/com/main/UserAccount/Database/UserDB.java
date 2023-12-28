package com.main.UserAccount.Database;


public interface UserDB {

    public UserAccount getAccountByEmail(String email) ;
    public UserAccount getAccountByID(String ID);
    public void addUserAccount(UserAccount newUserAccount);
    public void removeUserAccount(UserAccount userAccountToRemove);
    public boolean checkIsExist(String email) ;

    public  List<UserAccount> getAccounts();
}
