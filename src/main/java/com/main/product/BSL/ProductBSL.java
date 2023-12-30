package com.main.product.BSL;

import com.main.product.model.Product;

import java.util.List;

public interface ProductBSL {
    public List<Product> getProducts();
    public Product getProduct(String serialNumber);
    public void addProduct(Product product);
}
