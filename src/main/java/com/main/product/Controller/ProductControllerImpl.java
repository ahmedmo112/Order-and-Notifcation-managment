package com.main.product.Controller;

import com.main.APISchemas.*;
import com.main.product.BSL.CategoryBSL;
import com.main.product.BSL.CategoryBSLImpl;
import com.main.product.BSL.ProductBSL;
import com.main.product.BSL.ProductBSLImpl;
import com.main.product.Database.CategoryInMemoryDB;
import com.main.product.Database.ProductDB;
import com.main.product.Database.ProductsInMemoryDB;
import com.main.product.model.Category;
import com.main.product.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;



@RestController
public class ProductControllerImpl implements ProductController{

    private final ProductBSL productBSL;
    private final CategoryBSL categoryBSL;

    public ProductControllerImpl() {
        ProductDB productDB = new ProductsInMemoryDB();
        this.productBSL = new ProductBSLImpl(
//                productDB
        );
        this.categoryBSL = new CategoryBSLImpl(
                new CategoryInMemoryDB(),
                productDB
        );
    }

    @GetMapping("/categories/count")
    @Override
    public CategoryCountSchema getCategoryCount() {
        CategoryCountSchema response = new CategoryCountSchema();
        response.categories = categoryBSL.getCategoryCount();
        return response;
    }

    @GetMapping("/categories")
    @Override
    public CategorySchema getAllCategories() {
        CategorySchema response = new CategorySchema();
        response.categories = categoryBSL.getAllCategories();
        return response;
    }

    @GetMapping("/categories/{id}")
    @Override
    public Object getCategory(@PathVariable("id") int id) {
        Category category = categoryBSL.getCategory(id);
        if (Objects.isNull(category)) {
            NotFoundSchema response = new NotFoundSchema("Category");

            return response;
        }
        return category;
    }

    @GetMapping("/products/{serialNumber}")
    @Override
    public Object getProduct(@PathVariable("serialNumber") String serialNumber) {
        Product product = productBSL.getProduct(serialNumber);
        if (Objects.isNull(product)) {
            NotFoundSchema response = new NotFoundSchema("Product");

            return response;
        }
        return product;
    }

    @GetMapping("/products")
    @Override
    public ProductsSchema getProducts() {
        ProductsSchema response = new ProductsSchema();
        response.products = productBSL.getProducts();
        return response;
    }

    @PostMapping("categories/create")
    @Override
    public Object addCategory(@RequestBody Category category) {
        List<Category> categories = categoryBSL.getAllCategories();
        for (Category category1 : categories) {
            if (category1.getName().equals(category.getName())) {
                AlreadyExistSchema response = new AlreadyExistSchema("Category");


                return response;
            }
        }
        categoryBSL.addCategory(category);
        SuccessSchema response = new SuccessSchema("Category");
        return response;
    }

    @PostMapping("/products/create")
    @Override
    public Object addProduct(@RequestBody Product product) {
        System.out.println(product.getName());
        List<Product> products = productBSL.getProducts();
        List<Category> categories = categoryBSL.getAllCategories();
        for (Product product1 : products) {
            if (product1.getSerialNumber().equals(product.getSerialNumber())) {
                AlreadyExistSchema response = new AlreadyExistSchema("Product");
                return response;
            }
        }
        boolean found = false;
        for (Category category : categories) {
            if (category.getId() == product.getCategoryID()) {
                found = true;
                break;
            }
        }
        if (!found) {
            NotFoundSchema response = new NotFoundSchema("Category");
            return response;
        }

        productBSL.addProduct(product);
        SuccessSchema response = new SuccessSchema("Product");

        return response;
    }
}

