package com.main.product.Database;

import com.main.product.model.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryInMemoryDB implements CategoryDB{
    private List<Category> categories;

    public CategoryInMemoryDB(){
        categories = new ArrayList<>();
        categories.add(new Category(1,"Electronics"));
        categories.add(new Category(2,"Candies"));
        categories.add(new Category(3,"Books"));

    }


    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public Category getCategory(int id) {
        for (Category category:categories){
            if (category.getId()==id){
                return category;
            }
        }
        return null;
    }

    @Override
    public void addCategory(Category category) {
            categories.add(category);
    }

    @Override
    public void removeCategory(Category category) {
        categories.remove(category);
    }
}
