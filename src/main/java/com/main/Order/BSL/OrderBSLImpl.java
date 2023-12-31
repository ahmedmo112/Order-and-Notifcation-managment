package com.main.Order.BSL;


import com.main.APISchemas.*;
import com.main.Notification.BSL.NotificationBSL;
import com.main.Notification.BSL.NotificationTemplateFactory;
import com.main.Notification.model.*;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;

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
        notificationsTask  = new ArrayList<>();
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

            deducateBalance(order);
            orderDB.addOrder(order);
            updateProductsAmount(order);
            notifyUsers(order.getOrderId(),order.getOrderList(),"Your Order Has Been Placed","order");
//            System.out.println();
            // Change status to PRE_SHIPPING status after 20 seconds.
            changeStatusAfterTime(order, 20000, OrderStatus.PRE_SHIPPING,"","pre-shipping");

            // Change status to SHIPPING status after 40 seconds.
            changeStatusAfterTime(order, 40000, OrderStatus.SHIPPING,"Your Order is being shipped","shipping");

            changeStatusAfterTime(order, 80000, OrderStatus.SHIPPED, "Your order shipped successfully", "shipped");

            // TODO "notification user with a message of current order status"


        }



        return o;
    }

    private void updateProductsAmount(Order order) {
        for (UserOrder userOrder : order.getOrderList()) {
            for(Product userProduct : userOrder.getProducts()){

                Product productInDB = productBSL.getProduct(userProduct.getSerialNumber());

                int userAmount = userProduct.getCount();
                System.out.println("user amount : " + userAmount);
                int currentProductAmount = productInDB.getCount() - userAmount;
                System.out.println("curr amount "+currentProductAmount);
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

    private List<Product> getProducts(List<ProductDetailsSchema> products) {
        List<Product> productsList = new ArrayList<>();

        for (ProductDetailsSchema pds : products) {

            Product product = productBSL.getProduct(pds.serialNumber);
            System.out.println("schema count : " + pds.count);

            Product userProduct = new Product();
            if (product != null) {
                userProduct = new Product(product);
            }
            userProduct.setCount(pds.count);
            System.out.println("user product count : " + userProduct.getCount());

            productsList.add(userProduct);
        }
        return productsList;
    }

    private void changeStatusAfterTime(Order order, int milliseconds, OrderStatus orderStatus,String subject, String temp) {
        Timer timer = new Timer();
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                order.setOrderStatus(orderStatus);
                notifyUsers(order.getOrderId(),order.getOrderList(),subject,temp);
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

        if (o.getOrderStatus() == OrderStatus.SHIPPING || o.getOrderStatus() == OrderStatus.SHIPPED)
            return new ErrorMessageSchema("Order can't being cancelled");

        for (int i = 0; i < o.getOrderList().size() ; i++) {
            UserOrder userOrder=o.getOrderList().get(i);

            for (int j = 0; j < userOrder.getProducts().size(); j++) {
                String serialNumber=userOrder.getProducts().get(j).getSerialNumber();
                int count=userOrder.getProducts().get(j).getCount();
                Integer fullCount=productBSL.getProduct(serialNumber).getCount()+count;

                productBSL.getProduct(serialNumber).setCount(fullCount);

            }
        }


        for (UserOrder userOrder : o.getOrderList()) {
            double totalPrice = userOrder.getTotalPrice();
            double shippingFee = o.getShippingFees() / o.getOrderList().size();

            totalPrice += shippingFee;

          accountMangerBSL.deduct(userOrder.getUserId(), -totalPrice);

          notificationBSL.removeNotification(userOrder.getUserId(), orderId);
        }
        int count = 0;
        while (count <3){
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
    public void notifyUsers(int notificationId,List<UserOrder> userOrders,String subject,String template) {

        for (UserOrder userOrder : userOrders) {

            Notification notification = new Notification();
            notification.setNotificationId(notificationId);
            notification.setSubject(subject);
            notification.setChannel(accountMangerBSL.getNotificationChannel(userOrder.getUserId()));
            UserAccount user = accountMangerBSL.getUserAccount(userOrder.getUserId());
            NotificationTemplateFactory notificationTemplateFactory = new NotificationTemplateFactory();
            NotificationTemplate notificationTemplate = notificationTemplateFactory.makeTemplate(user.getName(), template, userOrder);
            if (notificationTemplate == null)
                continue;
            notification.setTemplate(notificationTemplate);
            notificationBSL.pushNotification(notification, userOrder.getUserId());
        }
    }

    @Override
    public void deducateBalance(Order order) {
        List<UserOrder> userOrders = order.getOrderList();
        for (UserOrder userOrder : userOrders) {

            double amount = 0;
            System.out.println("total price : " + userOrder.getTotalPrice());
            amount += userOrder.getTotalPrice();
            // Shipping costs for compound orders are split among all users.
            amount += (order.getShippingFees() / userOrders.size());
            System.out.println("shipping price : " + (order.getShippingFees() / userOrders.size()));

            System.out.println("current amount :" + amount );
            accountMangerBSL.deduct(userOrder.getUserId(), amount);
            System.out.println("current balance : " + accountMangerBSL.getBalance(userOrder.getUserId()));
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
