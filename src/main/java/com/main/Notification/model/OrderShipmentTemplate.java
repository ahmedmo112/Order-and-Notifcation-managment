package com.main.Notification.model;

public class OrderShipmentTemplate extends NotificationTemplate{

    public OrderShipmentTemplate(String userName, Object[] args) {
        super(userName, args);
    }
    public OrderShipmentTemplate(String userName) {
        super(userName);
    }
    @Override
    public String applyEnglishTemplate() {
        return "Dear " + userName + ",\n your order in his way to you, Thank you for using our service ❤️ ";
    }

    @Override
    public String applyArabicTemplate() {
        return "عزيزي " + userName + ", اوردرك في الطريق ليك شكرا لاستخدمك خدمتنا ❤️";
    }
}
