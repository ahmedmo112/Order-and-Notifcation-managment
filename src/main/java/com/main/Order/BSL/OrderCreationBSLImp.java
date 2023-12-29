package com.main.Order.BSL;

import com.main.Order.Database.OrderDB;
import com.main.Order.model.Order;
import com.main.Order.model.OrderStatus;
import com.main.Order.model.OrderType;
import com.main.Order.model.UserOrder;
import com.main.product.model.Product;

import java.util.List;

public class OrderCreationBSLImp implements OrderCreationBSL {
    @Override
    public void createID(Order order) {
        int orderID = getLastUserID(order);
        order.setOrderId(50 + orderID);
    }

    @Override
    public void calculateShippingFees(Order order) {
        int totalPrice = calculateTotalPrice(order);
        order.setShippingFees(0.1 * totalPrice);
    }

    @Override
    public void generateType(Order order) {
        order.setOrderType(order.getOrderList().size() > 1 ?
                OrderType.COMPOUND_ORDER : OrderType.SIMPLE_ORDER);
    }

    @Override
    public void generateStatus(Order order) {
        order.setOrderStatus(OrderStatus.IN_PLACEMENT);
    }

    private int calculateTotalPrice(Order order) {
        List<UserOrder> orders = order.getOrderList();
        int totalPrice = 0;
        for (UserOrder userOrder : orders) {
            int singleOrderPrice = 0;
            for (Product product : userOrder.getProducts()) {
                singleOrderPrice += product.getPrice();
            }
            totalPrice += singleOrderPrice;
            userOrder.setTotalPrice(singleOrderPrice);
        }
        return totalPrice;
    }

    private int getLastUserID(Order order) {
        List<UserOrder> orders = order.getOrderList();
        int lastIdx = orders.size() - 1;
        int lastUserID = orders.get(lastIdx).getUserId();

        return lastUserID;
    }
}
