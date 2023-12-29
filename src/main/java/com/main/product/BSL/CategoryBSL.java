package com.main.product.BSL;

import com.main.product.model.Category;

import java.util.List;
import java.util.Map;

public interface CategoryBSL {
    public List<Category> getAllCategories();
    public Category getCategory(int id);
    public Map<String, Integer> getCategoryCount();
    public void addCategory(Category category);
}
