package com.main.UserAccount.model;

public class UserAccount {

    private int id;
    private String name;
    private String email;
    private String password;
    private String address ;
    private String phone;

    public UserAccount() {
    }
    public UserAccount(String name, String email, String password, String address,String phone ,int id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.id = id;
        this.phone = phone;
    }

    public UserAccount(UserAccount userAccount){
        this.name = userAccount.name;
        this.email = userAccount.email;
        this.password = userAccount.password;
        this.address = userAccount.address;
        this.id = userAccount.id;


    }
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
