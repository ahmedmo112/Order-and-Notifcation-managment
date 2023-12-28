package com.main.UserAccount.Database;

public interface AccountMangerDB {
    
    public List<AccountManger> getPayment();
    public AccountManger getAccount(String email);
    public AccountManger getAccount(int id);
    public void addAccountManger (AccountManger accountManger);
    public void removeAccountManger (AccountManger accountManger);

}
