package com.main.product.Controller;

import com.main.APISchemas.CategoryCountSchema;
import com.main.APISchemas.CategorySchema;
import com.main.APISchemas.ProductsSchema;
import com.main.product.model.Category;
import com.main.product.model.Product;

import java.util.List;

public interface ProductController {
    public CategoryCountSchema getCategoryCount();
    public CategorySchema getAllCategories();
    public Object getCategory(int id);
    public Object getProduct(String serialNumber);
    public ProductsSchema getProducts();

    public Object addCategory(Category category);

    public Object addProduct(Product product);

}
