package com.main.UserAccount.BSL;

public interface ValidationBSL {
        public  boolean isValidEmail(String email);
        public  boolean isValidPassword(String password);
        public  boolean isValidBalance(double balance);

        public boolean isValidPhone(String phone);
}
