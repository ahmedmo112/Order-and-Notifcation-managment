package com.main.Order.Controller;

import com.main.APISchemas.*;
import com.main.ApplicationContextProvider;
import com.main.Notification.BSL.NotificationBSLImpl;
import com.main.Notification.Database.NotificationInMemoryDB;
import com.main.Order.BSL.OrderBSL;
import com.main.Order.BSL.OrderBSLImpl;
import com.main.Order.BSL.OrderCreationBSLImp;
import com.main.Order.BSL.OrderValidatorBSLImpl;
import com.main.Order.Database.OrderInMemoryDB;
import com.main.Order.model.Order;
import com.main.UserAccount.BSL.AccountMangerBSLImpl;
import com.main.UserAccount.BSL.AuthenticationBSL;
import com.main.UserAccount.Database.AccountMangerInMemoryDB;
import com.main.UserAccount.model.UserAccount;
import com.main.product.BSL.ProductBSLImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderControllerImpl implements OrderController  {
    OrderBSL orderBSL ;
    AuthenticationBSL authenticationBSL;

    @Autowired
    OrderControllerImpl(@Qualifier("orderBSLImpl") OrderBSL orderBSL , @Qualifier("authenticationBSLImpl") AuthenticationBSL authenticationBSL) {
        this.orderBSL =orderBSL;
        this.authenticationBSL = authenticationBSL;

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
        UserAccount user = authenticationBSL.getCurrUserAccount();
        if (user== null){
            return new ErrorMessageSchema("User not logged in");
        }
        return  orderBSL.createSimpleOrder(simpleOrderSchema);
    }

    @PostMapping("/orders/create/compound")
    public Object createCompoundOrder(@RequestBody CompoundOrderSchema compoundOrderSchema){
        UserAccount user = authenticationBSL.getCurrUserAccount();
        if (user== null){
            return new ErrorMessageSchema("User not logged in");
        }
        return  orderBSL.createCompoundOrder(compoundOrderSchema);
    }

    @PostMapping("/orders/cancel/{id}")
    public Object cancelOrder(@PathVariable("id") int orderId){
        UserAccount user = authenticationBSL.getCurrUserAccount();
        if (user== null){
            return new ErrorMessageSchema("User not logged in");
        }
        return orderBSL.cancelOrder(orderId);
    }


}
