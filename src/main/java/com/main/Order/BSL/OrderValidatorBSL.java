package com.main.Order.BSL;

import com.main.Order.model.Order;
import com.main.Order.model.UserOrder;


public interface OrderValidatorBSL {

    public Boolean checkUserBalance(UserOrder order, double shippingFee);
    public Boolean checkProductsAvailability(Order order);
    public Boolean checkProductsAmount(Order order);

}
