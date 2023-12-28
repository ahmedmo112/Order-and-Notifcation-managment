package com.main.product.model;

public class Category {
    private int id;
    private String name;
    private int numOfProducts;

    public Category() {
    }

    public Category(int id, String name, int numOfProducts) {
        this.id = id;
        this.name = name;
        this.numOfProducts = numOfProducts;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumOfProducts() {
        return numOfProducts;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name= name;
    }

    public void setNumOfProducts(int numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

}
