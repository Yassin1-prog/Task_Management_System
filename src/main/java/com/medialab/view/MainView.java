package com.medialab.view;

import com.medialab.controller.CategoryController;
import com.medialab.controller.ReminderController;
import com.medialab.controller.TaskController;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class MainView extends BorderPane {
    private TaskController taskController;
    private CategoryController categoryController;
    private ReminderController reminderController;

    private Label totalTasksLabel;
    private Label completedTasksLabel;
    private Label delayedTasksLabel;
    private Label dueSoonLabel;

    public MainView(TaskController taskController, CategoryController categoryController, ReminderController reminderController) {
        this.taskController = taskController;
        this.categoryController = categoryController;
        this.reminderController = reminderController;
        
        // Load CSS
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        initializeUI();
    }

    private void initializeUI() {
        // Create statistics grid
        GridPane statsGrid = new GridPane();
        statsGrid.getStyleClass().add("statistics-box");
        statsGrid.setHgap(15);
        statsGrid.setVgap(15);

        // Ensure each cell gets equal space
        for (int i = 0; i < 2; i++) {
            statsGrid.getColumnConstraints().add(new ColumnConstraints(250)); 
            statsGrid.getRowConstraints().add(new RowConstraints(50));
        }

        // Labels for statistics
        totalTasksLabel = createStyledLabel("📋");
        completedTasksLabel = createStyledLabel("✅");
        delayedTasksLabel = createStyledLabel("⏰");
        dueSoonLabel = createStyledLabel("📅");

        refreshStatistics();

        // Add labels to grid
        statsGrid.add(totalTasksLabel, 0, 0);
        statsGrid.add(completedTasksLabel, 1, 0);
        statsGrid.add(delayedTasksLabel, 0, 1);
        statsGrid.add(dueSoonLabel, 1, 1);
        
        // Add padding around the top section
        BorderPane.setMargin(statsGrid, new Insets(20, 20, 10, 20));

        // Bottom section with tabs
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab taskTab = createTab("Tasks", "📝");
        Tab categoryTab = createTab("Categories", "📁");
        Tab reminderTab = createTab("Reminders", "🔔");
        Tab priorityTab = createTab("Priorities", "📌");
        Tab searchTab = createTab("Search", "🔍");

        tabPane.getTabs().addAll(taskTab, categoryTab, reminderTab, priorityTab, searchTab);
        
        // Set default tab
        taskTab.setContent(new TaskListView(taskController, categoryController, reminderController, this));
        tabPane.getSelectionModel().select(taskTab);

        // Tab change listener
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                if (newTab == taskTab) {
                    newTab.setContent(new TaskListView(taskController, categoryController, reminderController, this));
                } else if (newTab == categoryTab) {
                    newTab.setContent(new CategoryListView(categoryController, taskController, reminderController, this));
                } else if (newTab == reminderTab) {
                    newTab.setContent(new ReminderListView(reminderController, taskController));
                } else if (newTab == priorityTab) {
                    newTab.setContent(new PriorityListView(taskController));
                } else if (newTab == searchTab) {
                    newTab.setContent(new SearchView(taskController, categoryController));
                }
            }
        });

        // Main layout
        this.setTop(statsGrid);
        this.setCenter(tabPane);
        BorderPane.setMargin(tabPane, new Insets(0, 20, 20, 20));
    }

    private Label createStyledLabel(String emoji) {
        Label label = new Label();
        label.getStyleClass().add("statistics-label");
        return label;
    }

    private Tab createTab(String text, String emoji) {
        Tab tab = new Tab(emoji + " " + text);
        return tab;
    }

    public void refreshStatistics() {
        totalTasksLabel.setText("📋 Total Tasks: " + taskController.getTotalTaskCount());
        completedTasksLabel.setText("✅ Completed Tasks: " + taskController.getCompletedTaskCount());
        delayedTasksLabel.setText("⏰ Delayed Tasks: " + taskController.getDelayedTaskCount());
        dueSoonLabel.setText("📅 Tasks Due in 7 Days: " + taskController.getTasksDueWithinDays(7).size());
    }
}