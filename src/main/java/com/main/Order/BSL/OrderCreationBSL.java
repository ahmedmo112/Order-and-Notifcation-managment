package com.main.Order.BSL;

import com.main.Order.model.Order;

public interface OrderCreationBSL {

    public void createID(Order order);
    public void calculateShippingFees(Order order);

    public void generateType(Order order);
    public void generateStatus(Order order);


}
