package com.medialab.view;

import com.medialab.controller.CategoryController;
import com.medialab.controller.ReminderController;
import com.medialab.controller.TaskController;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
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
        initializeUI();
    }

    private void initializeUI() {
        // Top section with summary information
        totalTasksLabel = new Label();
        completedTasksLabel = new Label();
        delayedTasksLabel = new Label();
        dueSoonLabel = new Label();

        refreshStatistics();

        VBox topSection = new VBox(10, totalTasksLabel, completedTasksLabel, delayedTasksLabel, dueSoonLabel);

        // Bottom section with tabs for tasks, categories, reminders, priorities, and search
        TabPane tabPane = new TabPane();

        Tab taskTab = new Tab("Tasks");
        Tab categoryTab = new Tab("Categories");
        Tab reminderTab = new Tab("Reminders");
        Tab priorityTab = new Tab("Priorities");
        Tab searchTab = new Tab("Search");

        tabPane.getTabs().addAll(taskTab, categoryTab, reminderTab, priorityTab, searchTab);

        // tasktab is the default tab
        taskTab.setContent(new TaskListView(taskController, categoryController, reminderController, this));
        tabPane.getSelectionModel().select(taskTab);

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
        this.setTop(topSection);
        this.setCenter(tabPane);
    }

    // Method to refresh the statistics labels
    public void refreshStatistics() {
        totalTasksLabel.setText("Total Tasks: " + taskController.getTotalTaskCount());
        completedTasksLabel.setText("Completed Tasks: " + taskController.getCompletedTaskCount());
        delayedTasksLabel.setText("Delayed Tasks: " + taskController.getDelayedTaskCount());
        dueSoonLabel.setText("Tasks Due in 7 Days: " + taskController.getTasksDueWithinDays(7).size());
    }
}