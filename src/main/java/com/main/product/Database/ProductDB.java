package com.main.product.Database;
import com.main.product.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public interface ProductDB {
    public Product getProduct(String serialNumber);
    public List<Product> getProducts();
    public void addProduct(Product product);
    public void removeProduct(Product product);
}
