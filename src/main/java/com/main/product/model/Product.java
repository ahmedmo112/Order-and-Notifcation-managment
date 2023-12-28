package com.main.product.model;

public class Product {
    private String name;
    private String serialNumber;
    private String vendor;
    private Double price;
    private Integer count;
    private int categoryId;


    public Product() {
    }

    public Product(String name, String serialNumber, String vendor, Double price, Integer count, int category) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.vendor = vendor;
        this.price = price;
        this.count = count;
        this.categoryId = category;
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
        return categoryId ;
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
        this.categoryId = category;
    }



}
