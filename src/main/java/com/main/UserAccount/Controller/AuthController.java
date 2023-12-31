package com.main.UserAccount.Controller;

import com.main.APISchemas.CreateAccountSchema;
import com.main.APISchemas.LoginSchema;
import com.main.APISchemas.ProfileSchema;
import com.main.UserAccount.model.UserAccount;

import java.util.Map;

public interface AuthController {
    public Object login(LoginSchema loginSchema);
    public Object createAccount(CreateAccountSchema createAccountSchema);
    public Object checkIsExist(Map<String,String> email);
    public Object logout();

    public Object getProfile();

}
