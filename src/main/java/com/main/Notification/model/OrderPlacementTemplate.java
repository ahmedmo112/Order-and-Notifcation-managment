package com.main.Notification.model;

import com.main.Order.model.UserOrder;
import com.main.product.model.Product;

import java.util.List;

public class OrderPlacementTemplate extends NotificationTemplate{

    private UserOrder userOrders;

    public OrderPlacementTemplate(String userName , UserOrder userOrders ) {
        super(userName);
        this.userOrders = userOrders;
    }

    @Override
    public String applyEnglishTemplate() {
        String items = "";

        for (Product arg : userOrders.getProducts()) {
            items += arg.getName() + " ";
        }

        return "Dear, " + userName + " your order has been placed successfully, your order items are: " + items + "and the total price " + userOrders.getTotalPrice()+" Thank you for using our service ❤️ ";
    }

    @Override
    public String applyArabicTemplate() {
        String items = "";
        for (Product arg : userOrders.getProducts()) {
            items += arg.getName() + " ";
        }
        return "عزيزي " + userName + " تم تأكيد طلبك بنجاح، قائمة الطلبات الخاصة بك هي: " + items + "والسعر الإجمالي " + userOrders.getTotalPrice()+" شكرا لاستخدامك لخدماتنا ❤️ ";
    }
}
