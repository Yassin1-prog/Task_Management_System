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

    public List<Category> getAllCategories() {
        return categories;
    }

    // Delete category (including associated tasks)
    public void deleteCategory(Category category, TaskController taskController) {
        taskController.deleteTasksByCategory(category); // Delete associated tasks
        categories.remove(category); // Delete the category
    }

    // Check if category exists
    public boolean categoryExists(String name) {
        return categories.stream().anyMatch(category -> category.getName().equals(name));
    }

    // Get category by name
    public Category getCategoryByName(String name) {
        return categories.stream()
            .filter(category -> category.getName().equals(name))
            .findFirst()
            .orElse(null);
    }
}