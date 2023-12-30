package com.main.product.Database;

import com.main.product.model.Category;
import com.main.product.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductInMemoryDB implements ProductDB {
    private List<Product> products ;

    public ProductInMemoryDB(){
        products = new ArrayList<>();
        products.add(new Product("iphone","2020","Aplle",1000.0,4,1));
        products.add(new Product("Laptop","2660","Dell",5000.0,2,1));
        products.add(new Product("Clean Code","1010","DarMasr",100.0,5,3));
    }

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
        System.out.println(product.getName());
        products.add(product);
    }

    @Override
    public void removeProduct(Product product) {
        products.remove(product);
    }


}
