package com.main.APISchemas;

import com.main.product.model.Product;

import java.util.List;


public class SimpleOrderSchema {
    public int id;
    public String address ;
    public List<Product> products;

}
