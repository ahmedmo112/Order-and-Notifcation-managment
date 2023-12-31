package com.main.Order.BSL;


import com.main.APISchemas.CompoundOrderSchema;
import com.main.APISchemas.SimpleOrderSchema;
import com.main.Order.model.Order;
import com.main.Order.model.UserOrder;

import java.util.List;

public interface OrderBSL {
    public Object createOrder(Order order);
    public Object createSimpleOrder(SimpleOrderSchema userOrderSchema);
    public Object createCompoundOrder(CompoundOrderSchema userOrderSchema);
    public void notifyUsers(int notificationId,List<UserOrder> userOrders,String subject,String template);
    public Object cancelOrder(int orderId);

    public void deducateBalance(Order order);
    public List<Order> getAllOrders();

    public List<Order> getOrdersByUserId(int userId);
}
