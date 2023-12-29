package com.main.UserAccount.BSL;

import com.main.APISchemas.CreateAccountSchema;
import com.main.APISchemas.ErrorMessageSchema;
import com.main.UserAccount.model.UserAccount;

public interface AuthenticationBSL {
    public boolean login(String email, String password);
    public String validateRegister(CreateAccountSchema createAccountSchema);
    public boolean createAccount(CreateAccountSchema createAccountSchema);
    public boolean checkIsExist(String email);
    public boolean logout();
    public UserAccount getCurrUserAccount();
    public void setCurrUserAccount(UserAccount userAccount);
}
