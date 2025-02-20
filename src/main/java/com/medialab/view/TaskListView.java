package com.medialab.view;

import java.util.List;

import com.medialab.controller.CategoryController;
import com.medialab.controller.ReminderController;
import com.medialab.controller.TaskController;
import com.medialab.model.Task;
import com.medialab.model.Task.TaskStatus;
import com.medialab.view.components.TaskView;
import com.medialab.view.dialogs.TaskDialog;
import com.medialab.model.Category;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class TaskListView extends BorderPane {
    private TaskController taskController;
    private ListView<Object> taskListView; // Use Object to allow both Category and Task items
    private CategoryController categoryController;
    private ReminderController reminderController;
    private MainView mainView;

    public TaskListView(TaskController taskController, CategoryController categoryController, ReminderController reminderController, MainView mainView) {
        this.taskController = taskController;
        taskListView = new ListView<>();
        taskListView.getStyleClass().add("task-list");
        this.categoryController = categoryController;
        this.reminderController = reminderController;
        this.mainView = mainView;

        Button addButton = createStyledButton("➕ Add Task", "add-button");
        Button editButton = createStyledButton("✏️ Edit Task", "edit-button");
        Button deleteButton = createStyledButton("🚮 Delete Task", "delete-button");

        addButton.setOnAction(e -> addTask());
        editButton.setOnAction(e -> editTask());
        deleteButton.setOnAction(e -> deleteTask());

        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        buttonBox.getStyleClass().add("category-button-box");
        buttonBox.setPadding(new Insets(10));

        taskListView.setCellFactory((Callback<ListView<Object>, ListCell<Object>>) new Callback<ListView<Object>, ListCell<Object>>() {
            @Override
            public ListCell<Object> call(ListView<Object> param) {
                return new ListCell<Object>() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            if (item instanceof Category) {
                                // Display category header
                                Label categoryLabel = new Label("📁 " + ((Category) item).getName());
                                categoryLabel.getStyleClass().add("category-header");
                                setGraphic(categoryLabel);
                            } else if (item instanceof Task) {
                                // Display task view
                                setGraphic(new TaskView((Task) item));
                            }
                        }
                    }
                };
            }
        });
        
        // Load tasks grouped by category
        loadTasksByCategory();
        mainView.refreshStatistics();

        this.setTop(buttonBox);
        this.setCenter(taskListView);
        this.setPadding(new Insets(10));
    }

    private Button createStyledButton(String text, String styleClass) {
        Button button = new Button(text);
        button.getStyleClass().add(styleClass);
        return button;
    }

    private void loadTasksByCategory() {
        taskListView.getItems().clear(); // Clear existing content

        // Load tasks grouped by category
        for (Category category : categoryController.getAllCategories()) {
            List<Task> tasks = taskController.getTasksByCategory(category);
            if (!tasks.isEmpty()) {
                // Add category header
                taskListView.getItems().add(category);

                // Add tasks under the category
                taskListView.getItems().addAll(tasks);
            }
        }
    }

    private void addTask() {
        // Open TaskDialog for adding a new task
        TaskDialog dialog = new TaskDialog(taskController, categoryController);
        dialog.showAndWait().ifPresent(task -> {
            if (taskController.getTaskByTitle(task.getTitle()) == null) {  // Don't add tasks with duplicate titles
                taskController.createTask(task.getTitle(), task.getDescription(), task.getCategory(), task.getPriority(), task.getDeadline(), task.getStatus());
                loadTasksByCategory(); // Refresh the task list
                mainView.refreshStatistics();
            } else {
                showAlert("A task with the same title already exists.");
            }
        });
    }

    private void editTask() {
        // Open TaskDialog for editing the selected task
        Task selectedTask = getSelectedTask();
        if (selectedTask != null) {
            TaskDialog dialog = new TaskDialog(taskController, categoryController);
            dialog.setTask(selectedTask);
            dialog.showAndWait().ifPresent(task -> {
                if (taskController.getTaskByTitle(task.getTitle()) == null || task.getTitle().equals(selectedTask.getTitle())) {  // Don't add tasks with duplicate titles
                    taskController.updateTask(selectedTask, task.getTitle(), task.getDescription(), task.getCategory(), task.getPriority(), task.getDeadline(), task.getStatus());
                    if (task.getStatus() == TaskStatus.COMPLETED) {
                        reminderController.deleteRemindersForTask(selectedTask);  // When task is marked as completed, delete all reminders for that task
                    }
                    loadTasksByCategory(); // Refresh the task list
                    mainView.refreshStatistics();
                } else {
                    showAlert("A task with the same title already exists.");
                }
            });
        }
    }

    private void deleteTask() {
        // Delete the selected task
        Task selectedTask = getSelectedTask();
        if (selectedTask != null) {
            reminderController.deleteRemindersForTask(selectedTask);
            taskController.deleteTask(selectedTask);
            loadTasksByCategory(); // Refresh the task list
            mainView.refreshStatistics();
        }
    }

    private Task getSelectedTask() {
        // Get the selected task from the ListView
        Object selectedItem = taskListView.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof Task) {
            return (Task) selectedItem;
        }
        return null;
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Task Name");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}