
package com.medialab.view;

import com.medialab.controller.CategoryController;
import com.medialab.controller.TaskController;
import com.medialab.model.Task;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class SearchView extends BorderPane {
    private TaskController taskController;
    private CategoryController categoryController;
    private ListView<String> resultListView;

    public SearchView(TaskController taskController, CategoryController categoryController) {
        this.taskController = taskController;
        this.categoryController = categoryController;
        resultListView = new ListView<>();

        // Create search fields
        TextField titleField = new TextField();
        ComboBox<String> categoryComboBox = new ComboBox<>();
        ComboBox<String> priorityComboBox = new ComboBox<>();

        // Populate category and priority combo boxes
        categoryComboBox.getItems().add("All Categories"); // Null option for categories
        priorityComboBox.getItems().add("All Priorities"); // Null option for priorities
        categoryController.getAllCategories().forEach(category -> categoryComboBox.getItems().add(category.getName()));
        taskController.getAllPriorities().forEach(priority -> priorityComboBox.getItems().add(priority.getName()));

        // Create search button
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            String title = titleField.getText();
            String category = categoryComboBox.getValue();
            String priority = priorityComboBox.getValue();

            // Treat "All Categories" and "All Priorities" as null
            if ("All Categories".equals(category)) {
                category = null;
            }
            if ("All Priorities".equals(priority)) {
                priority = null;
            }

            // Perform search
            List<Task> results = taskController.searchTasks(title, category, priority);
            resultListView.getItems().clear();
            results.stream()
            .map(Task::getTitle)
            .forEach(titlee -> resultListView.getItems().add(titlee));
        });

        // Create search form layout
        GridPane searchForm = new GridPane();
        searchForm.setHgap(10);
        searchForm.setVgap(10);
        searchForm.add(new Label("Title:"), 0, 0);
        searchForm.add(titleField, 1, 0);
        searchForm.add(new Label("Category:"), 0, 1);
        searchForm.add(categoryComboBox, 1, 1);
        searchForm.add(new Label("Priority:"), 0, 2);
        searchForm.add(priorityComboBox, 1, 2);
        searchForm.add(searchButton, 1, 3);

        // Add search form and results to the view
        VBox topSection = new VBox(10, searchForm);
        this.setTop(topSection);
        this.setCenter(resultListView);
    }
}