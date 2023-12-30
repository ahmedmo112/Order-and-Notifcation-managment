package com.main.Order.Controller;

import com.main.APISchemas.AllOrdersSchema;
import com.main.APISchemas.CompoundOrderSchema;
import com.main.APISchemas.SimpleOrderSchema;
import com.main.APISchemas.SuccessSchema;
import com.main.Notification.BSL.NotificationBSLImpl;
import com.main.Notification.Database.NotificationInMemoryDB;
import com.main.Order.BSL.OrderBSL;
import com.main.Order.BSL.OrderBSLImpl;
import com.main.Order.Database.OrderInMemoryDB;
import com.main.Order.model.Order;
import com.main.UserAccount.Database.AccountMangerInMemoryDB;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderControllerImpl implements OrderController {
    OrderBSL orderBSL ;

    OrderControllerImpl() {
        this.orderBSL = new OrderBSLImpl(
                new OrderInMemoryDB(),
                new AccountMangerInMemoryDB(),
                new NotificationBSLImpl(
                        new NotificationInMemoryDB()
                )
        );
    }


    @GetMapping("/orders")
    @Override
    public AllOrdersSchema getOrders() {
        AllOrdersSchema allOrdersSchema = new AllOrdersSchema();
        allOrdersSchema.orders = orderBSL.getAllOrders();
        return allOrdersSchema;
    }


    @PostMapping("/orders/create/simple")
    public Object createSimpleOrder(@RequestBody SimpleOrderSchema simpleOrderSchema){
        orderBSL.createSimpleOrder(simpleOrderSchema);
        return new SuccessSchema("Order creation");
    }

    @PostMapping("/orders/create/compound")
    public Object createCompoundOrder(@RequestBody CompoundOrderSchema compoundOrderSchema){
        orderBSL.createCompoundOrder(compoundOrderSchema);
        return new SuccessSchema("Order creation");
    }

    @PostMapping("/orders/cancel/{id}")
    public Object cancelOrder(@PathVariable("id") int orderId){
        orderBSL.cancelOrder(orderId);
        return new SuccessSchema("Order cancellation");
    }


}
