package com.main.product.BSL;

import com.main.product.Database.ProductDB;
import com.main.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductBSLImpl implements ProductBSL{
    private ProductDB productDB;

    @Autowired
    public ProductBSLImpl(@Qualifier("productsInMemoryDB") ProductDB productDB) {
        this.productDB = productDB;

    }

    @Override
    public List<Product> getProducts() {
        return productDB.getProducts();
    }

    @Override
    public Product getProduct(String serialNumber) {
        return productDB.getProduct(serialNumber);
    }

    @Override
    public void addProduct(Product product) {
        productDB.addProduct(product);
    }
}
