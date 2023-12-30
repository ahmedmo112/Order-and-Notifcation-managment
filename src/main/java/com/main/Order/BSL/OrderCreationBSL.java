package com.main.Order.BSL;

import com.main.Order.model.Order;

public interface OrderCreationBSL {


    public Object create(Order order);
    public void createID(Order order);
    public double calculateTotalPrice(Order order);

    public void generateType(Order order);
    public void generateStatus(Order order);


}
