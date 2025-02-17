package com.medialab.view;

import com.medialab.controller.CategoryController;
import com.medialab.controller.ReminderController;
import com.medialab.controller.TaskController;
import com.medialab.model.Category;
import com.medialab.view.components.CategoryView;
import com.medialab.view.dialogs.CategoryDialog;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class CategoryListView extends BorderPane {
    private CategoryController categoryController;
    private TaskController taskController; // Add TaskController
    private ListView<CategoryView> categoryListView;
    private ReminderController reminderController;
    private MainView mainView;

    public CategoryListView(CategoryController categoryController, TaskController taskController, ReminderController reminderController, MainView mainView) {
        this.categoryController = categoryController;
        this.taskController = taskController; // Initialize TaskController
        this.reminderController = reminderController;
        categoryListView = new ListView<>();
        this.mainView = mainView;

        // Load categories into the list
        loadCategories();

        // Add buttons for adding/editing/deleting categories
        Button addButton = new Button("Add Category");
        addButton.setOnAction(e -> addCategory());

        Button editButton = new Button("Edit Category");
        editButton.setOnAction(e -> editCategory());

        Button deleteButton = new Button("Delete Category");
        deleteButton.setOnAction(e -> deleteCategory());

        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        this.setTop(buttonBox);
        this.setCenter(categoryListView);
    }

    private void loadCategories() {
        categoryListView.getItems().clear();
        for (Category category : categoryController.getAllCategories()) {
            categoryListView.getItems().add(new CategoryView(category));
        }
    }

    private void addCategory() {
        // Open CategoryDialog for adding a new category
        CategoryDialog dialog = new CategoryDialog();
        dialog.showAndWait().ifPresent(category -> {
            categoryController.createCategory(category.getName());
            loadCategories(); // Refresh the category list
        });
    }

    private void editCategory() {
        // Open CategoryDialog for editing the selected category
        CategoryView selectedCategoryView = categoryListView.getSelectionModel().getSelectedItem();
        if (selectedCategoryView != null) {
            CategoryDialog dialog = new CategoryDialog();
            dialog.setCategory(selectedCategoryView.getCategory());
            dialog.showAndWait().ifPresent(category -> {
                categoryController.updateCategory(selectedCategoryView.getCategory(), category.getName());
                loadCategories(); // Refresh the category list
            });
        }
    }

    private void deleteCategory() {
        // Delete the selected category and its associated tasks
        CategoryView selectedCategoryView = categoryListView.getSelectionModel().getSelectedItem();
        if (selectedCategoryView != null) {
            String deletedCategory = selectedCategoryView.getCategory().getName();
            // Find and delete reminders for  tasks linked to the deleted category
            taskController.getAllTasks().stream()
                .filter(task -> deletedCategory.equals(task.getCategoryName()))
                .forEach(task->reminderController.deleteRemindersForTask(task));
            // Call the backend method to delete the category and its tasks
            categoryController.deleteCategory(selectedCategoryView.getCategory(), taskController);

            loadCategories(); // Refresh the category list
            mainView.refreshStatistics(); // when a category is deleted tasks get deleted as well
        }
    }
}