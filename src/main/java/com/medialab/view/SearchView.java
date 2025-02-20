package com.medialab.view;

import com.medialab.controller.CategoryController;
import com.medialab.controller.TaskController;
import com.medialab.model.Task;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class SearchView extends BorderPane {
    private TaskController taskController;
    private CategoryController categoryController;
    private ListView<HBox> resultListView;
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public SearchView(TaskController taskController, CategoryController categoryController) {
        this.taskController = taskController;
        this.categoryController = categoryController;
        this.getStyleClass().add("task-search-view");
        resultListView = new ListView<>();

        // Create search fields
        TextField titleField = new TextField();
        titleField.getStyleClass().add("search-field");

        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getStyleClass().add("search-combo-box");

        ComboBox<String> priorityComboBox = new ComboBox<>();
        priorityComboBox.getStyleClass().add("search-combo-box");

        // Populate category and priority combo boxes
        categoryComboBox.getItems().add("All Categories"); // Null option for categories
        priorityComboBox.getItems().add("All Priorities"); // Null option for priorities
        categoryController.getAllCategories().forEach(category -> categoryComboBox.getItems().add(category.getName()));
        taskController.getAllPriorities().forEach(priority -> priorityComboBox.getItems().add(priority.getName()));

        // Create search button
        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("search-button");
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
            for (Task task : results) {
                resultListView.getItems().add(createTaskCard(task));
            }
        });

        // Create search form layout
        GridPane searchForm = new GridPane();
        searchForm.setHgap(10);
        searchForm.setVgap(10);
        searchForm.setPadding(new Insets(20));
        searchForm.getStyleClass().add("search-form");

        searchForm.add(new Label("Title:"), 0, 0);
        searchForm.add(titleField, 1, 0);
        searchForm.add(new Label("Category:"), 0, 1);
        searchForm.add(categoryComboBox, 1, 1);
        searchForm.add(new Label("Priority:"), 0, 2);
        searchForm.add(priorityComboBox, 1, 2);
        searchForm.add(searchButton, 1, 3);

        // Add search form and results to the view
        VBox topSection = new VBox(10, searchForm);
        topSection.setPadding(new Insets(20));
        this.setTop(topSection);
        this.setCenter(resultListView);
    }

    private HBox createTaskCard(Task task) {
        HBox taskCard = new HBox(10);
        taskCard.getStyleClass().add("task-card");
        taskCard.setPadding(new Insets(10));
        taskCard.setAlignment(Pos.CENTER_LEFT);

        // Task title
        Label titleLabel = new Label(task.getTitle());
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        titleLabel.getStyleClass().add("task-title");

        // Task category
        Label categoryLabel = new Label("📁 " + task.getCategory().getName());
        categoryLabel.getStyleClass().add("task-category");

        // Task priority
        Label priorityLabel = new Label("📌 " + task.getPriority().getName());
        priorityLabel.getStyleClass().add("task-priority");

        // Task deadline
        Label deadlineLabel = new Label("📅 " + task.getDeadline().format(DATE_FORMATTER));
        deadlineLabel.getStyleClass().add("task-deadline");

        taskCard.getChildren().addAll(titleLabel, categoryLabel, priorityLabel, deadlineLabel);
        return taskCard;
    }
}