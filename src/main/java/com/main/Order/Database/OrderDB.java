package com.main.Order.Database;

import com.main.Order.model.Order;

import java.util.List;

public interface OrderDB {
    public List<Order> getOrders();

    public Order getOrder(int orderId);
    public void addOrder(Order order);

    public void removeOrder(int orderId);
}
