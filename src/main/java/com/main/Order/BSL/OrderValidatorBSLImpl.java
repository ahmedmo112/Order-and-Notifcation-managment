package com.main.Order.BSL;

import com.main.Order.model.Order;
import com.main.Order.model.UserOrder;
import com.main.UserAccount.BSL.AccountMangerBSL;
import com.main.UserAccount.BSL.AccountMangerBSLImpl;
import com.main.UserAccount.Database.AccountMangerInMemoryDB;
import com.main.product.BSL.ProductBSL;
import com.main.product.BSL.ProductBSLImpl;
import com.main.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderValidatorBSLImpl implements OrderValidatorBSL {

    ProductBSL productBSL;
    AccountMangerBSL accountMangerBSL;

    @Autowired
    public OrderValidatorBSLImpl(@Qualifier("productBSLImpl") ProductBSL productBSL, @Qualifier("accountMangerBSLImpl") AccountMangerBSL accountMangerBSL) {
        this.productBSL = productBSL;
        this.accountMangerBSL = accountMangerBSL;

    }




    @Override
    public Boolean checkUserBalance(UserOrder order, double shippingFee) {
        double userBalanceInDB = accountMangerBSL.getBalance(order.getUserId());
        if (userBalanceInDB < order.getTotalPrice() + shippingFee) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean checkProductsAvailability(Order order) {
        List<UserOrder> userOrders = order.getOrderList();

        for (UserOrder userOrder : userOrders) {
            for (Product product : userOrder.getProducts()) {
                Product productInDB = productBSL.getProduct(product.getSerialNumber());
                if (productInDB == null || productInDB.getCount() == 0)
                    return false;
            }
        }
        return true;
    }

    @Override
    public Boolean checkProductsAmount(Order order) {
        List<UserOrder> userOrders = order.getOrderList();
        for (UserOrder userOrder : userOrders) {
            for (Product product : userOrder.getProducts()) {
                Product productInDB = productBSL.getProduct(product.getSerialNumber());
                if (product.getCount() > productInDB.getCount()) {
                    return false;
                }
            }
        }
        return true;
    }
}
