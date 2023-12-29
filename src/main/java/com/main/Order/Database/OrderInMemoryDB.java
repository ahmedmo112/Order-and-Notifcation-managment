package com.main.Order.Database;

import com.main.Order.model.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderInMemoryDB implements OrderDB {

    private List<Order> orders;

    public OrderInMemoryDB() {
        orders = new ArrayList<>();
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Order getOrder(int orderId) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId)
                return order;
        }
        return null;
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void removeOrder(int orderId) {
        orders.removeIf(order -> order.getOrderId() == orderId);
    }
}
