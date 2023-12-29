package com.main.Order.BSL;


import com.main.APISchemas.CompoundOrderSchema;
import com.main.APISchemas.SimpleOrderSchema;
import com.main.Order.model.Order;

import java.util.List;

public interface OrderBSL {
    public void createOrder(Order order);
    public void createSimpleOrder(SimpleOrderSchema userOrderSchema);
    public void createCompoundOrder(CompoundOrderSchema userOrderSchema);
    public void notifyUsers();
    public void cancelOrder(int orderId);

    public void deducateBalance(Order order);
    public List<Order> getAllOrders();
}
