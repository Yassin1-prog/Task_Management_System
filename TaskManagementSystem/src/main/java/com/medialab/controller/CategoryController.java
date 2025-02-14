package com.medialab.controller;

import com.medialab.model.Category;
import java.util.List;
import java.util.ArrayList;

public class CategoryController {
    private List<Category> categories;

    public CategoryController() {
        categories = new ArrayList<>();
    }

    public void initializeCategories(List<Category> loadedCategories) {
        categories.clear();
        categories.addAll(loadedCategories);
    }

    public void createCategory(String name) {
        categories.add(new Category(name));
    }

    public void updateCategory(Category category, String newName) {
        category.setName(newName);
    }

    public void deleteCategory(Category category) {
        category.removeAllTasks();
        categories.remove(category);
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }
}