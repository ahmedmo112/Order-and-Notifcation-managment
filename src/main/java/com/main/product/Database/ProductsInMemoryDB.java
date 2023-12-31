package com.main.product.Database;

import com.main.product.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductsInMemoryDB implements ProductDB{
    private List<Product> products ;

    public ProductsInMemoryDB(){
        products = new ArrayList<>();

        products.add(new Product("iPhone", "2020", "Apple", 1000.0, 4, 1));
        products.add(new Product("Laptop", "2660", "Dell", 5000.0, 2, 1));
        products.add(new Product("Clean Code", "1010", "DarMasr", 100.0, 5, 3));

        products.add(new Product("LED TV", "5500", "Samsung", 1500.0, 3, 1));
        products.add(new Product("Chocolates", "789", "Hershey's", 10.0, 20, 2));
        products.add(new Product("Java Programming Book", "2022", "O'Reilly", 50.0, 3, 3));

        products.add(new Product("T-shirt", "123", "Nike", 30.0, 8, 4));
        products.add(new Product("Home Speaker", "890", "Bose", 300.0, 3, 5));
        products.add(new Product("Running Shoes", "456", "Adidas", 80.0, 10, 6));
        products.add(new Product("Gaming Console", "678", "Sony", 400.0, 5, 6));
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
