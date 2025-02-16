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

    public MainView(TaskController taskController, CategoryController categoryController, ReminderController reminderController) {
        this.taskController = taskController;
        this.categoryController = categoryController;
        this.reminderController = reminderController;
        initializeUI();
    }

    private void initializeUI() {
        // Top section with summary information
        Label totalTasksLabel = new Label("Total Tasks: " + taskController.getTotalTaskCount());
        Label completedTasksLabel = new Label("Completed Tasks: " + taskController.getCompletedTaskCount());
        Label delayedTasksLabel = new Label("Delayed Tasks: " + taskController.getDelayedTaskCount());
        Label dueSoonLabel = new Label("Tasks Due in 7 Days: " + taskController.getTasksDueWithinDays(7).size());

        VBox topSection = new VBox(10, totalTasksLabel, completedTasksLabel, delayedTasksLabel, dueSoonLabel);

        // Bottom section with tabs for tasks, categories, reminders, priorities, and search
        TabPane tabPane = new TabPane();
        Tab taskTab = new Tab("Tasks", new TaskListView(taskController));
        Tab categoryTab = new Tab("Categories", new CategoryListView(categoryController, taskController));
        Tab reminderTab = new Tab("Reminders", new ReminderListView(reminderController, taskController));
        Tab priorityTab = new Tab("Priorities", new PriorityListView(taskController));
        Tab searchTab = new Tab("Search", new SearchView(taskController, categoryController));

        tabPane.getTabs().addAll(taskTab, categoryTab, reminderTab, priorityTab, searchTab);

        // Main layout
        this.setTop(topSection);
        this.setCenter(tabPane);
    }
}