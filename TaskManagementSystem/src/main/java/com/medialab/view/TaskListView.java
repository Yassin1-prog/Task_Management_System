package com.medialab.view;

import com.medialab.controller.CategoryController;
import com.medialab.controller.ReminderController;
import com.medialab.controller.TaskController;
import com.medialab.model.Task;
import com.medialab.view.components.TaskView;
import com.medialab.view.dialogs.TaskDialog;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class TaskListView extends BorderPane {
    private TaskController taskController;
    private ListView<TaskView> taskListView;
    private CategoryController categoryController;
    private ReminderController reminderController;

    public TaskListView(TaskController taskController, CategoryController categoryController, ReminderController reminderController) {
        this.taskController = taskController;
        taskListView = new ListView<>();
        this.categoryController = categoryController;
        this.reminderController = reminderController;

        // Load tasks into the list
        loadTasks();

        // Add buttons for adding/editing/deleting tasks
        Button addButton = new Button("Add Task");
        addButton.setOnAction(e -> addTask());

        Button editButton = new Button("Edit Task");
        editButton.setOnAction(e -> editTask());

        Button deleteButton = new Button("Delete Task");
        deleteButton.setOnAction(e -> deleteTask());

        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        this.setTop(buttonBox);
        this.setCenter(taskListView);
    }

    private void loadTasks() {
        taskListView.getItems().clear();
        for (Task task : taskController.getAllTasks()) {
            taskListView.getItems().add(new TaskView(task));
        }
    }

    private void addTask() {
        // Open TaskDialog for adding a new task
        TaskDialog dialog = new TaskDialog(taskController, categoryController);
        dialog.showAndWait().ifPresent(task -> {
            taskController.createTask(task.getTitle(), task.getDescription(), task.getCategory(), task.getPriority(), task.getDeadline());
            loadTasks(); // Refresh the task list
        });
    }

    private void editTask() {
        // Open TaskDialog for editing the selected task
        TaskView selectedTaskView = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTaskView != null) {
            TaskDialog dialog = new TaskDialog(taskController, categoryController);
            dialog.setTask(selectedTaskView.getTask());
            dialog.showAndWait().ifPresent(task -> {
                taskController.updateTask(selectedTaskView.getTask(), task.getTitle(), task.getDescription(), task.getCategory(), task.getPriority(), task.getDeadline());
                loadTasks(); // Refresh the task list
            });
        }
    }

    private void deleteTask() {
        // Delete the selected task
        TaskView selectedTaskView = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTaskView != null) {
            reminderController.deleteRemindersForTask(selectedTaskView.getTask());
            taskController.deleteTask(selectedTaskView.getTask());
            loadTasks(); // Refresh the task list
        }
    }
}