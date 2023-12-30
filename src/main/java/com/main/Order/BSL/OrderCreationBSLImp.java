package com.main.Order.BSL;

import com.main.APISchemas.ErrorMessageSchema;
import com.main.APISchemas.NotFoundSchema;
import com.main.APISchemas.SuccessSchema;
import com.main.Order.Database.OrderDB;
import com.main.Order.Database.OrderInMemoryDB;
import com.main.Order.model.Order;
import com.main.Order.model.OrderStatus;
import com.main.Order.model.OrderType;
import com.main.Order.model.UserOrder;
import com.main.UserAccount.BSL.AccountMangerBSLImpl;
import com.main.UserAccount.Database.AccountMangerInMemoryDB;
import com.main.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderCreationBSLImp implements OrderCreationBSL {

    OrderValidatorBSL orderValidatorBSL;
    OrderDB orderDB;

    public OrderCreationBSLImp(OrderValidatorBSL orderValidatorBSL, OrderDB orderDB) {
        this.orderValidatorBSL = orderValidatorBSL;
        this.orderDB = orderDB;
    }

    @Override
    public Object create(Order order) {
        createID(order);
        generateStatus(order);
        generateType(order);

        if (!orderValidatorBSL.checkProductsAvailability(order))
            return new NotFoundSchema("Product");

        if (!orderValidatorBSL.checkProductsAmount(order))
            return new NotFoundSchema("Product amount");

        calculateTotalPrice(order);


        List<UserOrder> orders = order.getOrderList();
        double singleUserShippingFee = (order.getShippingFees()/ order.getOrderList().size());

        for (UserOrder userOrder : orders){

            if (!orderValidatorBSL.checkUserBalance(userOrder , singleUserShippingFee))
            {
                return new ErrorMessageSchema("Not enough balance.");

            }

        }

        return order;
    }

    @Override
    public void createID(Order order) {
        int orderID = getLastOrderID() + 1;
        order.setOrderId(50 + orderID);
    }


    @Override
    public void generateType(Order order) {
        order.setOrderType(order.getOrderList().size() > 1 ?
                OrderType.COMPOUND_ORDER : OrderType.SIMPLE_ORDER);
    }

    @Override
    public void generateStatus(Order order) {
        order.setOrderStatus(OrderStatus.IN_PLACEMENT);
    }

    @Override
    public double calculateTotalPrice(Order order) {
        List<UserOrder> orders = order.getOrderList();
        double totalPrice = 0;
        for (UserOrder userOrder : orders) {
            double singleOrderPrice = 0;
            for (Product product : userOrder.getProducts()) {
                singleOrderPrice += product.getPrice() * product.getCount();
            }
            totalPrice += singleOrderPrice;

            userOrder.setTotalPrice(singleOrderPrice);
        }
        order.setShippingFees(totalPrice * 0.1);
        return totalPrice + order.getShippingFees();
    }

    private int getLastOrderID() {
        List<Order> orders = orderDB.getOrders();

        System.out.println(orders.size());
        if (orders.isEmpty()) return 0;


        System.out.println("IAM HERE ....");

        int lastIdx = orders.size() - 1;
        Order lastOrder = orders.get(lastIdx);

        return lastOrder.getOrderId();
    }
}
