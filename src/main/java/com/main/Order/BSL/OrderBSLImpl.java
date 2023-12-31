package com.main.Order.BSL;


import com.main.APISchemas.*;
import com.main.Notification.BSL.NotificationBSL;
import com.main.Notification.BSL.NotificationTemplateFactory;
import com.main.Notification.model.Notification;
import com.main.Notification.model.NotificationTemplate;
import com.main.Order.Database.OrderDB;
import com.main.Order.model.NotificationTask;
import com.main.Order.model.Order;
import com.main.Order.model.OrderStatus;
import com.main.Order.model.UserOrder;
import com.main.UserAccount.BSL.AccountMangerBSL;
import com.main.UserAccount.model.UserAccount;
import com.main.product.BSL.ProductBSL;
import com.main.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderBSLImpl implements OrderBSL {

    private OrderDB orderDB;

    private AccountMangerBSL accountMangerBSL;
    private ProductBSL productBSL;
    private OrderCreationBSL orderCreationBSL;
    private NotificationBSL notificationBSL;

    private List<NotificationTask> notificationsTask;



    @Autowired
    public OrderBSLImpl(@Qualifier("orderInMemoryDB") OrderDB orderDB,
                        @Qualifier("accountMangerBSLImpl") AccountMangerBSL accountMangerBSL,
                        @Qualifier("productBSLImpl") ProductBSL productBSL,
                        @Qualifier("orderCreationBSLImp") OrderCreationBSL orderCreationBSL,
                        @Qualifier("notificationBSLImpl") NotificationBSL notificationBSL) {
        notificationsTask = new ArrayList<>();
        this.orderDB = orderDB;
        this.accountMangerBSL = accountMangerBSL;
        this.productBSL = productBSL;
        this.orderCreationBSL = orderCreationBSL;
        this.notificationBSL = notificationBSL;
    }

    @Override
    public Object createOrder(Order order) {


//         if successful creation it would return "order" object ,
//         else it would return a warn message...
        Object o = orderCreationBSL.create(order);
        if (o instanceof Order) {

            // Deducate total order price from user balance .
            deducateBalance(order);

            // Add order database
            orderDB.addOrder(order);

            updateProductsAmount(order);
            notifyUsers(order.getOrderId(), order.getOrderList(), "Your Order Has Been Placed", "order");

            // Change status to PRE_SHIPPING status after 20 seconds, and notify user by the current status .
            changeStatusAfterTime(order, 20000, OrderStatus.PRE_SHIPPING, "", "pre-shipping");

            // Change status to SHIPPING status after 40 seconds, and notify user by the current status .
            changeStatusAfterTime(order, 40000, OrderStatus.SHIPPING, "Your Order is being shipped", "shipping");

            // Change status to SHIPPED status after 80 seconds , and notify user by the current status .
            changeStatusAfterTime(order, 80000, OrderStatus.SHIPPED, "Your order shipped successfully", "shipped");

        }


        return o;
    }

    private void updateProductsAmount(Order order) {
        // Iterate on each user order on the simple or compound order
        for (UserOrder userOrder : order.getOrderList()) {

            // iterate on each product in user order .
            for (Product userProduct : userOrder.getProducts()) {

                // Retrieve the original product from products database by product serial number.
                Product productInDB = productBSL.getProduct(userProduct.getSerialNumber());


                int userAmount = userProduct.getCount();
                int currentProductAmount = productInDB.getCount() - userAmount;

                // update the product amount ( count ) with the new amount ( old amount - amount in user order)
                productInDB.setCount(currentProductAmount);
            }

        }
    }

    @Override
    public Object createSimpleOrder(SimpleOrderSchema userOrderSchema) {
        Order order = new Order();

        UserOrder userOrder = new UserOrder(userOrderSchema.userId, userOrderSchema.address, 0.0, getProducts(userOrderSchema.products));
        order.getOrderList().add(userOrder);

        return createOrder(order);
    }

    // Convert the product details schema object to a product object .
    private List<Product> getProducts(List<ProductDetailsSchema> products) {
        List<Product> productsList = new ArrayList<>();

        for (ProductDetailsSchema pds : products) {

            // Use the serial number on schema to retrieve the product from products database.
            Product product = productBSL.getProduct(pds.serialNumber);

            Product userProduct = new Product();
            if (product != null) {
                userProduct = new Product(product);
            }
            userProduct.setCount(pds.count);
            productsList.add(userProduct);
        }
        return productsList;
    }

    // Simulate the time gap between different status of order (SHIPPING, SHIPPED, ... )
    private void changeStatusAfterTime(Order order, int milliseconds, OrderStatus orderStatus, String subject, String temp) {


        Timer timer = new Timer();
        // Wait "milliseconds" the change the state of order .
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                order.setOrderStatus(orderStatus);
                notifyUsers(order.getOrderId(), order.getOrderList(), subject, temp);
            }
        }, milliseconds);

        notificationsTask.add(new NotificationTask(order.getOrderId(), timer));

    }

    @Override
    public Object createCompoundOrder(CompoundOrderSchema userOrderSchema) {
        Order order = new Order();

        List<SimpleOrderSchema> ordersList = userOrderSchema.orders;
        for (SimpleOrderSchema simpleOrderSchema : ordersList) {
            UserOrder userOrder = new UserOrder(simpleOrderSchema.userId, simpleOrderSchema.address, 0.0, getProducts(simpleOrderSchema.products));
            order.getOrderList().add(userOrder);
        }

        return createOrder(order);

    }


    @Override
    public Object cancelOrder(int orderId) {
        Order o = orderDB.getOrder(orderId);

        if (o == null)
            return new ErrorMessageSchema("Order not found");

        // user cannot cancel order in SHIPPING or SHIPPED status .
        if (o.getOrderStatus() == OrderStatus.SHIPPING || o.getOrderStatus() == OrderStatus.SHIPPED)
            return new ErrorMessageSchema("Order can't being cancelled");

        for (int i = 0; i < o.getOrderList().size(); i++) {
            UserOrder userOrder = o.getOrderList().get(i);

            for (int j = 0; j < userOrder.getProducts().size(); j++) {

                String serialNumber = userOrder.getProducts().get(j).getSerialNumber();

                int count = userOrder.getProducts().get(j).getCount();
                Integer fullCount = productBSL.getProduct(serialNumber).getCount() + count;

                // Retrieve the product from database and add the order amount that was in the cancelled order.
                productBSL.getProduct(serialNumber).setCount(fullCount);

            }
        }

        // Retrieve the order price to user balance .
        for (UserOrder userOrder : o.getOrderList()) {
            // Get total price and shipping fee paid by user .
            double totalPrice = userOrder.getTotalPrice();
            double shippingFee = o.getShippingFees() / o.getOrderList().size();

            totalPrice += shippingFee;

            // Adds the order price to user balance ( subtract (- total price))
            accountMangerBSL.deduct(userOrder.getUserId(), -totalPrice);


            // Remove the notification of user from notification database .
            notificationBSL.removeNotification(userOrder.getUserId(), orderId);
        }

        // Remove the three notification (three order status) from notification queue.
        int count = 0;
        while (count < 3) {
            for (NotificationTask notificationTask : notificationsTask) {
                if (notificationTask.getNotificationId() == orderId) {
                    notificationTask.getTimer().cancel();
                    notificationsTask.remove(notificationTask);
                    count++;
                    break;
                }
            }
        }

        orderDB.removeOrder(orderId);
        return new SuccessSchema("Order removed Successfully");
    }

    @Override
    public void notifyUsers(int notificationId, List<UserOrder> userOrders, String subject, String template) {

        for (UserOrder userOrder : userOrders) {

            Notification notification = new Notification();
            notification.setNotificationId(notificationId);
            notification.setSubject(subject);
            notification.setChannel(accountMangerBSL.getNotificationChannel(userOrder.getUserId()));

            UserAccount user = accountMangerBSL.getUserAccount(userOrder.getUserId());

            // Retrieve the wanted template from template factory .
            NotificationTemplateFactory notificationTemplateFactory = new NotificationTemplateFactory();
            NotificationTemplate notificationTemplate = notificationTemplateFactory.makeTemplate(user.getName(), template, userOrder);

            if (notificationTemplate == null)
                continue;
            notification.setTemplate(notificationTemplate);

            notificationBSL.addToNotificationQueue(notification, userOrder.getUserId());
            System.out.println(notificationBSL.getNotificationQueue().size());
        }
    }



    @Override
    public void deducateBalance(Order order) {
        List<UserOrder> userOrders = order.getOrderList();
        for (UserOrder userOrder : userOrders) {

            double amount = 0;
            amount += userOrder.getTotalPrice();
            // Shipping costs for compound orders are split among all users.
            amount += (order.getShippingFees() / userOrders.size());

            accountMangerBSL.deduct(userOrder.getUserId(), amount);
        }


    }


    @Override
    public List<Order> getAllOrders() {
        return orderDB.getOrders();
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = orderDB.getOrders();
        List<Order> userOrders = new ArrayList<>();

        for (Order order : orders) {
            for (UserOrder userOrder : order.getOrderList()) {
                if (userOrder.getUserId() == userId) {
                    userOrders.add(order);
                    break;
                }
            }
        }
        return userOrders;
    }

}
