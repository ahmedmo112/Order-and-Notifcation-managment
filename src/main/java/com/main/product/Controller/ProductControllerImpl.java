package com.main.product.Controller;

import com.main.APISchemas.ProductsSchema;
import com.main.product.BSL.ProductBSL;
import com.main.product.BSL.ProductBSLImpl;
import com.main.product.Database.ProductInMemoryDB;
import com.main.product.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



@RestController
public class ProductControllerImpl implements ProductController{

    private final ProductBSL productBSL;

    public ProductControllerImpl() {
        this.productBSL = new ProductBSLImpl(
                new ProductInMemoryDB()
        );
    }

    @GetMapping("/products")
    @Override
    public ProductsSchema getProducts() {
        ProductsSchema response = new ProductsSchema();
        response.products = productBSL.getProducts();
        return response;
    }
}

