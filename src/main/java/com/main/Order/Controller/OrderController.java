package com.main.Order.Controller;

import com.main.APISchemas.AllOrdersSchema;
import com.main.APISchemas.CompoundOrderSchema;
import com.main.APISchemas.SimpleOrderSchema;
import com.main.Order.model.Order;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OrderController {
    public AllOrdersSchema getOrders();
    public Object getOrdersByUserId();
    public Object cancelOrder(int orderId);

    public Object createSimpleOrder( SimpleOrderSchema compoundOrderSchema);
    public Object createCompoundOrder( CompoundOrderSchema compoundOrderSchema);
}
