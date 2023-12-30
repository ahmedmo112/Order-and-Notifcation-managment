package com.main.Order.BSL;


import com.main.APISchemas.*;
import com.main.Order.Database.OrderDB;
import com.main.Order.model.Order;
import com.main.Order.model.OrderStatus;
import com.main.Order.model.UserOrder;
import com.main.UserAccount.BSL.AccountMangerBSL;
import com.main.product.BSL.ProductBSL;
import com.main.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderBSLImpl implements OrderBSL {

    private OrderDB orderDB;

    private AccountMangerBSL accountMangerBSL;
    private ProductBSL productBSL;
    private OrderCreationBSL orderCreationBSL;
//    private NotificationBSL notificationBSL;


    public OrderBSLImpl(OrderDB orderDB, AccountMangerBSL accountMangerBSL, ProductBSL productBSL, OrderCreationBSL orderCreationBSL) {
        this.orderDB = orderDB;
        this.accountMangerBSL = accountMangerBSL;
        this.productBSL = productBSL;
        this.orderCreationBSL = orderCreationBSL;
    }

    @Override
    public Object createOrder(Order order) {


        // if successful creation it would return "order" object ,
        // else it would return a warn message...
        Object o = orderCreationBSL.create(order);
        if (o instanceof Order) {

            deducateBalance(order);
            orderDB.addOrder(order);
            updateProductsAmount(order);

//            System.out.println();
            // Change status to PRE_SHIPPING status after 20 seconds.
            changeStatusAfterTime(order, 20000, OrderStatus.PRE_SHIPPING);

            // Change status to SHIPPING status after 40 seconds.
            changeStatusAfterTime(order, 40000, OrderStatus.SHIPPING);

            // TODO "notification user with a message of current order status"


        }



        return o;
    }

    private void updateProductsAmount(Order order) {
        for (UserOrder userOrder : order.getOrderList()) {
            for(Product userProduct : userOrder.getProducts()){

                Product productInDB = productBSL.getProduct(userProduct.getSerialNumber());

                int userAmount = userProduct.getCount();
                int currentProductAmount = productInDB.getCount() - userAmount;

                productInDB.setCount(currentProductAmount);
            }

        }
    }

    @Override
    public Object createSimpleOrder(SimpleOrderSchema userOrderSchema) {
        Order order = new Order();

        UserOrder userOrder = new UserOrder(userOrderSchema.id, userOrderSchema.address, 0.0, getProducts(userOrderSchema.products));
        order.getOrderList().add(userOrder);

        return createOrder(order);
    }

    private List<Product> getProducts(List<ProductDetailsSchema> products) {
        List<Product> productsList = new ArrayList<>();

        for (ProductDetailsSchema pds : products) {
            Product product = productBSL.getProduct(pds.serialNumber);
            product.setCount(pds.count);

            productsList.add(product);
        }
        return productsList;
    }

    private void changeStatusAfterTime(Order order, int milliseconds, OrderStatus orderStatus) {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        order.setOrderStatus(orderStatus);
                    }
                },
                milliseconds
        );

    }

    @Override
    public Object createCompoundOrder(CompoundOrderSchema userOrderSchema) {
        Order order = new Order();

        List<SimpleOrderSchema> ordersList = userOrderSchema.orders;
        for (SimpleOrderSchema simpleOrderSchema : ordersList) {
            UserOrder userOrder = new UserOrder(simpleOrderSchema.id, simpleOrderSchema.address, 0.0, getProducts(simpleOrderSchema.products));
            order.getOrderList().add(userOrder);
        }

        return createOrder(order);

    }


    @Override
    public Object cancelOrder(int orderId) {
        Order o = orderDB.getOrder(orderId);
        if (o == null || o.getOrderStatus() == OrderStatus.SHIPPING || o.getOrderStatus() == OrderStatus.SHIPPED)
            return new NotFoundSchema("Order ID");

        orderDB.removeOrder(orderId);
        return new SuccessSchema("Order removed");
    }

    @Override
    public void notifyUsers() {
        // TODO
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

}
