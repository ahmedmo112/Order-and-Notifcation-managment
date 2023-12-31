package com.main.UserAccount.Controller;

import com.main.APISchemas.*;
import com.main.UserAccount.BSL.AccountMangerBSL;
import com.main.UserAccount.BSL.AuthenticationBSL;
import com.main.UserAccount.BSL.AuthenticationBSLImpl;
import com.main.UserAccount.BSL.ValidationBSLImpl;
import com.main.UserAccount.Database.AccountMangerDB;
import com.main.UserAccount.Database.AccountMangerInMemoryDB;
import com.main.UserAccount.Database.UserInMemoryDB;
import com.main.UserAccount.model.AccountManger;
import com.main.UserAccount.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthenticationControllerImpl implements AuthController {
    AuthenticationBSL authenticationBSL;
    AccountMangerBSL accountMangerBSL;

    @Autowired
    public AuthenticationControllerImpl(@Qualifier("authenticationBSLImpl") AuthenticationBSL authenticationBSL,
                                        @Qualifier("accountMangerBSLImpl") AccountMangerBSL accountMangerBSL) {
        this.authenticationBSL = authenticationBSL;
        this.accountMangerBSL = accountMangerBSL;
    }

    @PostMapping("/login")
    @Override
    public Object login(@RequestBody LoginSchema loginSchema) {
        boolean isLogin = authenticationBSL.login(loginSchema.getEmail(), loginSchema.getPassword());
        if(isLogin){
            return authenticationBSL.getCurrUserAccount();
        }
        return new NotFoundSchema("User not found");
    }

    @PostMapping("/register")
    @Override
    public Object createAccount(@RequestBody CreateAccountSchema createAccountSchema) {
       String message = authenticationBSL.validateRegister(createAccountSchema);
       if(message.equals("Valid")){
           authenticationBSL.createAccount(createAccountSchema);
              return authenticationBSL.getCurrUserAccount();
        }
       return new ErrorMessageSchema(message);
    }

    @PostMapping("/users/check")
    @Override
    public Object checkIsExist(@RequestBody Map<String,String> email) {
        boolean isExist = authenticationBSL.checkIsExist(email.get("email"));
        if(isExist){
            return new ErrorMessageSchema("Email already exist");
        }
        return new ErrorMessageSchema("Email is available");
    }

    @PostMapping("/logout")
    @Override
    public Object logout() {
        if(authenticationBSL.getCurrUserAccount() == null){
            return new ErrorMessageSchema("You are not logged in");
        }
        authenticationBSL.logout();
        return new SuccessSchema("Logout ");
    }

    @GetMapping ("/profile")
    @Override
    public Object getProfile() {
        UserAccount userAccount = authenticationBSL.getCurrUserAccount();
        if(userAccount == null){
            return new ErrorMessageSchema("You are not logged in");
        }
        ProfileSchema profile = new ProfileSchema();
        profile.setName(userAccount.getName());
        profile.setEmail(userAccount.getEmail());
        profile.setAddress(userAccount.getAddress());
        profile.setId(userAccount.getId());
        profile.setPhone(userAccount.getPhone());

        profile.setBalance(accountMangerBSL.getBalance(userAccount.getId()));
        profile.setChannel(accountMangerBSL.getNotificationChannel(userAccount.getId()));

        return profile;




    }
}
