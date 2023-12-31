package com.main.APISchemas;

import com.main.Notification.model.NotificationChannels;

public class ProfileSchema {
    private int id;
    private String name;
    private String email;
    private String address ;
    private String phone;
    private double balance ;

    private NotificationChannels channel;

    public ProfileSchema() {
    }

    public ProfileSchema(String name, String email, String address,String phone ,int id) {
        this.name = name;
        this.email = email;

        this.address = address;
        this.id = id;
        this.phone = phone;
    }

    public ProfileSchema(ProfileSchema profileSchema){
        this.name = profileSchema.name;
        this.email = profileSchema.email;

        this.address = profileSchema.address;
        this.id = profileSchema.id;
        this.phone = profileSchema.phone;
        this.balance = profileSchema.balance;
        this.channel = profileSchema.channel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getEmail() {
        return email;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public NotificationChannels getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannels channel) {
        this.channel = channel;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }




}
