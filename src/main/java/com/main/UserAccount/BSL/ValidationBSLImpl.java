package com.main.UserAccount.BSL;

import org.springframework.stereotype.Service;

@Service
public class ValidationBSLImpl implements ValidationBSL{
    @Override
    public boolean isValidEmail(String email) {
         String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
            return email.matches(EMAIL_REGEX);

    }

    @Override
    public boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidBalance(double balance) {
        if (balance < 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidPhone(String phone) {
       if (phone.length() == 11) {
            return true;
        }
        return false;
    }
}
