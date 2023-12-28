package com.main.product.Database;

import com.main.product.Database.ProductDB;
import com.main.product.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductInMemoryDB implements ProductDB {
    private List<Product> products = List.of(
            new Product("Mobile","2020","Dell",1000.0,4,1),
            new Product("Laptop","2050","Dell",13333.0,3,1),
            new Product("Book","2020","Dell",100.0,2,3)

    );
    @Override
    public Product getProduct(String serialNumber) {

        for (Product product:products){
            if (product.getSerialNumber().equals(serialNumber)){
                return product;
            }
        }
        return null;
    }

    @Override
    public List<Product> getProducts() {

        return  products;
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public void removeProduct(Product product) {
        products.remove(product);
    }


}
