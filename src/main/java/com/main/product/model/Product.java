package com.main.product.model;

public class Product {
    private String name;
    private String serialNumber;
    private String vendor;
    private Double price;
    private int count;
    private int categoryID;


    public Product() {
        this.name = "name";
        this.serialNumber = "serialNumber";
        this.vendor = "vendor";
        this.price = 0.0;
        this.count = 0;
        this.categoryID = -1;
    }

    public Product(String name, String serialNumber, String vendor, Double price, Integer count, int categoryId) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.vendor = vendor;
        this.price = price;
        this.count = count;
        this.categoryID = categoryId;
    }

    public Product(Product other) {
        this.name = other.name;
        this.serialNumber = other.serialNumber;
        this.vendor = other.vendor;
        this.price = other.price;
        this.count = other.count;
        this.categoryID = other.categoryID;
    }

    public String getName() {
        return name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getVendor() {
        return vendor;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getCount() {
        return count;
    }

    public int getCategoryID() {
        return categoryID ;
    }

    public void setName(String name) {
        this.name= name;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setCategory(int category) {
        this.categoryID = category;
    }



}
