package com.main.product.Database;

import com.main.product.model.Category;


import java.util.List;


public interface CategoryDB {
    public List<Category> getAllCategories();
    public Category getCategory(int id);
    public void addCategory(Category category);
    public void removeCategory(Category category);
}
