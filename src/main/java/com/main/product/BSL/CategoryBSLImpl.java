package com.main.product.BSL;

import com.main.product.Database.CategoryDB;
import com.main.product.Database.ProductDB;
import com.main.product.model.Category;
import com.main.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryBSLImpl implements CategoryBSL{
    private CategoryDB categoryDB;
    private ProductDB productDB;

    public CategoryBSLImpl(CategoryDB categoryDB, ProductDB productDB) {
        this.categoryDB = categoryDB;
        this.productDB = productDB;
    }
    @Override
    public List<Category> getAllCategories() {
        return categoryDB.getAllCategories();
    }

    @Override
    public Category getCategory(int id) {
        return categoryDB.getCategory(id);
    }

    @Override
    public Map<String, Integer> getCategoryCount() {
        List<Category>  categories = categoryDB.getAllCategories();
        List<Product> products = productDB.getProducts();
        Map<String,Integer> categoryCount= new HashMap<>();
        for (Category category:categories){
            int count=0;
            for (Product product:products){
                if (product.getCategoryID() == category.getId()){
                    count++;
                }
            }
            categoryCount.put(category.getName(),count);
        }
        return categoryCount;
    }
    @Override
    public void addCategory(Category category){
        categoryDB.addCategory(category);
    }

}
