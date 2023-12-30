package com.main.Order.Controller;

import com.main.APISchemas.AllOrdersSchema;
import com.main.APISchemas.CompoundOrderSchema;
import com.main.APISchemas.SimpleOrderSchema;
import com.main.Order.BSL.OrderBSL;
import com.main.Order.BSL.OrderBSLImpl;
import com.main.Order.BSL.OrderCreationBSLImp;
import com.main.Order.BSL.OrderValidatorBSLImpl;
import com.main.Order.Database.OrderDB;
import com.main.Order.Database.OrderInMemoryDB;
import com.main.UserAccount.BSL.AccountMangerBSLImpl;
import com.main.UserAccount.Database.AccountMangerInMemoryDB;
import com.main.product.BSL.ProductBSLImpl;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderControllerImpl implements OrderController {
    OrderBSL orderBSL;

    OrderControllerImpl() {
        OrderDB orderDB = new OrderInMemoryDB();

        this.orderBSL = new OrderBSLImpl(
                orderDB,

                new AccountMangerBSLImpl(
                        new AccountMangerInMemoryDB()
                ),
                new ProductBSLImpl(
//                        new ProductInMemoryDB()
                ),
                new OrderCreationBSLImp(
                        new OrderValidatorBSLImpl(),
                        orderDB
                )
//                new NotificationBSLImpl()
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
    public Object createSimpleOrder(@RequestBody SimpleOrderSchema simpleOrderSchema) {
        return orderBSL.createSimpleOrder(simpleOrderSchema);
//        return new SuccessSchema("Order creation");
    }

    @PostMapping("/orders/create/compound")
    public Object createCompoundOrder(@RequestBody CompoundOrderSchema compoundOrderSchema) {
        return orderBSL.createCompoundOrder(compoundOrderSchema);
//        return new SuccessSchema("Order creation");
    }

    @PostMapping("/orders/cancel/{id}")
    public Object cancelOrder(@PathVariable("id") int orderId) {
       return orderBSL.cancelOrder(orderId);
//        return new SuccessSchema("Order cancellation");
    }


}
