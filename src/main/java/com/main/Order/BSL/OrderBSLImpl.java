package com.main.Order.BSL;


import com.main.APISchemas.CompoundOrderSchema;
import com.main.APISchemas.SimpleOrderSchema;
import com.main.Notification.BSL.NotificationBSL;
import com.main.Order.Database.OrderDB;
import com.main.Order.model.Order;
import com.main.Order.model.UserOrder;
import com.main.UserAccount.Database.AccountMangerDB;
import com.main.UserAccount.model.AccountManger;

import java.util.List;

public class OrderBSLImpl implements OrderBSL {

    private OrderDB orderDB;
    private AccountMangerDB accountMangerDB;
    private NotificationBSL notificationBSL;

    public OrderBSLImpl(OrderDB orderDB, AccountMangerDB accountMangerDB, NotificationBSL notificationBSL) {
        this.orderDB = orderDB;
        this.accountMangerDB = accountMangerDB;
        this.notificationBSL = notificationBSL;
    }

    @Override
    public void createOrder(Order order) {

        OrderCreationBSL orderCreationBSL = new OrderCreationBSLImp();

        orderCreationBSL.createID(order);
        orderCreationBSL.generateStatus(order);
        orderCreationBSL.generateType(order);
        orderCreationBSL.calculateShippingFees(order);

        deducateBalance(order);
        orderDB.addOrder(order);
    }

    @Override
    public void createSimpleOrder(SimpleOrderSchema userOrderSchema) {
        Order order = new Order() ;

        UserOrder userOrder = new UserOrder(userOrderSchema.id, userOrderSchema.address, 0.0, userOrderSchema.products);
        order.getOrderList().add(userOrder);

        createOrder(order);
    }

    @Override
    public void createCompoundOrder( CompoundOrderSchema userOrderSchema) {
        Order order = new Order() ;

        List<SimpleOrderSchema> ordersList = userOrderSchema.orders ;
        for (SimpleOrderSchema simpleOrderSchema: ordersList) {
            UserOrder userOrder = new UserOrder(simpleOrderSchema.id, simpleOrderSchema.address, 0.0, simpleOrderSchema.products);
            order.getOrderList().add(userOrder);
        }

        createOrder(order);

    }


    @Override
    public void cancelOrder(int orderId) {

        orderDB.removeOrder(orderId);
    }

    @Override
    public void notifyUsers() {
        // TODO
    }

    @Override
    public void deducateBalance(Order order) {
        List<UserOrder> userOrders = order.getOrderList();

        for (UserOrder userOrder : userOrders) {
            AccountManger user = accountMangerDB.getAccount(userOrder.getUserId());

            double currentBalance = user.getBalance();
            currentBalance -= userOrder.getTotalPrice();

            // // Shipping costs for compound orders are split among all users.
            currentBalance -= (order.getShippingFees() / userOrders.size());
            user.setBalance(currentBalance);
        }
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDB.getOrders();
    }

}
