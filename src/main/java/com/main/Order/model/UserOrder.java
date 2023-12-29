package com.main.Order.model;

import com.main.product.model.Product;

import java.util.ArrayList;
import java.util.List;

public class UserOrder {

    private int userId;
    private String address;
    private double totalPrice;
    private List<Product> products;


    public UserOrder(){

    }
    public UserOrder(int userId, String address, double totalPrice, List<Product> products) {
        this.userId = userId;
        this.address = address;
        this.totalPrice = totalPrice;
        this.products = new ArrayList<>(products); // Creating a new ArrayList to avoid sharing the reference
    }

    public UserOrder(UserOrder otherUserOrder) {
        this.userId = otherUserOrder.userId;
        this.address = otherUserOrder.address;
        this.totalPrice = otherUserOrder.totalPrice;

        // Creating a deep copy of the products list
        this.products = new ArrayList<>();
        for (Product product : otherUserOrder.products) {
            this.products.add(new Product(product));
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
