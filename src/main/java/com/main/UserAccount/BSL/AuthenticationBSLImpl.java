package com.main.UserAccount.BSL;

import com.main.APISchemas.CreateAccountSchema;
import com.main.APISchemas.ErrorMessageSchema;
import com.main.Notification.model.NotificationChannels;
import com.main.UserAccount.Database.AccountMangerDB;
import com.main.UserAccount.Database.UserDB;
import com.main.UserAccount.model.AccountManger;
import com.main.UserAccount.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationBSLImpl implements AuthenticationBSL{
    private UserDB userDB;
    private AccountMangerDB accountMangerDB;
    private static UserAccount currUserAccount;
    private ValidationBSL validationBSL;


    @Autowired
    public AuthenticationBSLImpl(@Qualifier("userInMemoryDB") UserDB userDB, @Qualifier("accountMangerInMemoryDB") AccountMangerDB accountMangerDB, ValidationBSL validationBSL){
        this.userDB = userDB;
        this.validationBSL = validationBSL;
        this.accountMangerDB = accountMangerDB;

    }
    @Override
    public boolean login(String email, String password) {
        UserAccount userAccount = userDB.getAccountByEmail(email);
        if(userAccount == null){
            return false;
        }
        if(userAccount.getPassword().equals(password)){
            currUserAccount = userAccount;
            return true;
        }
        return false;
    }

    @Override
    public String validateRegister(CreateAccountSchema createAccountSchema) {
        if (!validationBSL.isValidEmail(createAccountSchema.getEmail())) {
            return "Invalid email";
        }
        if (!validationBSL.isValidPassword(createAccountSchema.getPassword())) {
            return "Invalid password";
        }
        if (!validationBSL.isValidBalance(createAccountSchema.getBalance())) {
            return "Invalid balance";
        }
        if (checkIsExist(createAccountSchema.getEmail()) ) {
            return "Email already exist";
        }
        return "Valid";
    }

    @Override
    public boolean createAccount(CreateAccountSchema createAccountSchema) {
       int id = 20211040 + userDB.getAccounts().size() + 1;
        UserAccount userAccount = new UserAccount(
                createAccountSchema.getName(),
                createAccountSchema.getEmail(),
                createAccountSchema.getPassword(),
                createAccountSchema.getAddress(),
                createAccountSchema.getPhone(),
                id
        );
        userDB.addUserAccount(userAccount);
        currUserAccount = userAccount;
        AccountManger accountManger = new AccountManger();
        accountManger.setBalance(createAccountSchema.getBalance());
        accountManger.setUserAccount(id);
        accountManger.setChannel(createAccountSchema.getChannel());
        accountMangerDB.addAccountManger(accountManger);
        return true;
    }

    @Override
    public boolean checkIsExist(String email) {
        if(userDB.getAccountByEmail(email)!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean logout() {
        if(currUserAccount == null){
            return false;
        }
        currUserAccount = null;
        return true;
    }

    @Override
    public UserAccount getCurrUserAccount() {
        return currUserAccount;
    }

    @Override
    public void setCurrUserAccount(UserAccount userAccount) {
        currUserAccount = userAccount;
    }


}
