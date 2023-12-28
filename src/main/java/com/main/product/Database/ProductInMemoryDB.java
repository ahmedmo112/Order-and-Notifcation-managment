package com.main.product.Database;

import com.main.product.Database.ProductDB;
import com.main.product.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductInMemoryDB implements ProductDB {
    private List<Product> products = new ArrayList<>();
    @Override
    public Product getProduct(String serialNumber) {
        return null;
    }

    @Override
    public ArrayList<Product> getProducts() {
        return null;
    }

    @Override
    public void addProduct(Product product) {

    }

    @Override
    public void removeProduct(Product product) {

    }


}
