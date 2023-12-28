package com.main.product.Database;

import com.main.product.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public interface CategoryDB {
    public List<Category> getAllCategories();
    public Category getCategory(int id);
    public void addCategory(Category category);
    public void removeCategory(Category category);
}
