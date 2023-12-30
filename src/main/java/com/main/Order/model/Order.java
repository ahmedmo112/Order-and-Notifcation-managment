package com.main.Order.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private int orderId;
    private double shippingFees;
    private OrderType orderType;
    private OrderStatus orderStatus;

    private List<UserOrder> orderList;


    public Order() {
        orderList = new ArrayList<>();
    }

    public Order(int orderId, double shippingFees, OrderType orderType, OrderStatus orderStatus, List<UserOrder> orderList) {
        this.orderId = orderId;
        this.shippingFees = shippingFees;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.orderList = orderList;
    }

    public Order(Order otherOrder) {
        this.orderId = otherOrder.orderId;
        this.shippingFees = otherOrder.shippingFees;
        this.orderType = otherOrder.orderType;
        this.orderStatus = otherOrder.orderStatus;

        // Creating a deep copy of the orderList
        this.orderList = new ArrayList<>();
        for (UserOrder userOrder : otherOrder.orderList) {
            this.orderList.add(new UserOrder(userOrder));
        }
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getShippingFees() {
        return shippingFees;
    }

    public void setShippingFees(double shippingFees) {
        this.shippingFees = shippingFees;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<UserOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<UserOrder> orderList) {
        this.orderList = orderList;
    }
}
