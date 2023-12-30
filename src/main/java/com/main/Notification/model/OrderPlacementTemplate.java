package com.main.Notification.model;

public class OrderPlacementTemplate extends NotificationTemplate{

    public OrderPlacementTemplate(String userName, Object[] args) {
        super(userName, args);
    }

    public OrderPlacementTemplate(String userName ) {
        super(userName);
    }

    @Override
    public String applyEnglishTemplate() {
        String items = "";
        for (Object arg : args) {
            items += arg + "\n";
        }
        return "Dear, " + userName + "\n your order has been placed successfully, your order items are: \n" + items + "Thank you for using our service ❤️ ";
    }

    @Override
    public String applyArabicTemplate() {
        String items = "";
        for (Object arg : args) {
            items += arg + "\n";
        }
        return "عزيزي " + userName + "\n تم تأكيد طلبك بنجاح، قائمة الطلبات الخاصة بك هي: \n" + items + "شكرا لاستخدامك لخدماتنا ❤️ ";
    }
}
