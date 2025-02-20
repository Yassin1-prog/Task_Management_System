package com.medialab.view;

import com.medialab.controller.CategoryController;
import com.medialab.controller.ReminderController;
import com.medialab.controller.TaskController;
import com.medialab.model.Category;
import com.medialab.view.components.CategoryView;
import com.medialab.view.dialogs.CategoryDialog;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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

        categoryListView.getStyleClass().add("category-list");
        // Load categories into the list
        loadCategories();

        // Create styled buttons with icons
        Button addButton = createStyledButton("➕ Add Category", "add-button");
        Button editButton = createStyledButton("✏️ Edit Category", "edit-button");
        Button deleteButton = createStyledButton("🚮 Delete Category", "delete-button");

        addButton.setOnAction(e -> addCategory());
        editButton.setOnAction(e -> editCategory());
        deleteButton.setOnAction(e -> deleteCategory());

        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        buttonBox.getStyleClass().add("category-button-box");

        this.setTop(buttonBox);
        this.setCenter(categoryListView);
        
        // Add padding to the main container
        this.setPadding(new Insets(10));
    }

    private Button createStyledButton(String text, String styleClass) {
        Button button = new Button(text);
        button.getStyleClass().add(styleClass);
        return button;
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
            if(categoryController.getCategoryByName(category.getName()) == null) {
                categoryController.createCategory(category.getName());
                loadCategories(); // Refresh the category list
            } else {
                showAlert("A category with the same name arleady exists.");
            }
        });
    }

    private void editCategory() {
        // Open CategoryDialog for editing the selected category
        CategoryView selectedCategoryView = categoryListView.getSelectionModel().getSelectedItem();
        if (selectedCategoryView != null) {
            CategoryDialog dialog = new CategoryDialog();
            dialog.setCategory(selectedCategoryView.getCategory());
            dialog.showAndWait().ifPresent(category -> {
                if(categoryController.getCategoryByName(category.getName()) == null || category.getName().equals(selectedCategoryView.getCategory().getName())) {
                    categoryController.updateCategory(selectedCategoryView.getCategory(), category.getName());
                    loadCategories(); // Refresh the category list
                } else {
                    showAlert("A category with the same name arleady exists.");
                }
               
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Task Name");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}