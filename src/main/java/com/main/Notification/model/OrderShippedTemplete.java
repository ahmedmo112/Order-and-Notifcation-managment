package com.main.Notification.model;

public class OrderShippedTemplete extends NotificationTemplate{


    public OrderShippedTemplete(String userName) {
        super(userName);
    }

    @Override
    String applyEnglishTemplate() {
        return "Dear " + this.userName + ", your order has been shipped.";
    }

    @Override
    String applyArabicTemplate() {
        return "عزيزي " + this.userName + ", تم شحن طلبك.";
    }
}
