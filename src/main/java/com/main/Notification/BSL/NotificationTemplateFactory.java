package com.main.Notification.BSL;

import com.main.Notification.model.NotificationTemplate;
import com.main.Notification.model.OrderPlacementTemplate;
import com.main.Notification.model.OrderShipmentTemplate;
import com.main.Notification.model.OrderShippedTemplete;
import com.main.Order.model.UserOrder;
import com.main.product.model.Product;

import java.util.List;

public class NotificationTemplateFactory {

    public NotificationTemplate makeTemplate(String  username, String template, Object args){
        NotificationTemplate temp = switch (template) {
            case "order" -> new OrderPlacementTemplate(username, args instanceof UserOrder? (UserOrder) args: null);
            case "shipping" -> new OrderShipmentTemplate(username);
            case "shipped" -> new OrderShippedTemplete(username);
            default -> null;
        };

        return temp;
    }
}
