package com.main.product.Database;

import com.main.product.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryInMemoryDB implements CategoryDB{
    private List<Category> categories= List.of(
            new Category(1,"Electronics",2),
            new Category(2,"Grocery",3),
            new Category(3,"Books",1)
    );


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
