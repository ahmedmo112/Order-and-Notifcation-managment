package com.main.product.Controller;

import com.main.APISchemas.ProductsSchema;
import com.main.product.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ProductController {
    public ProductsSchema getProducts();
}
