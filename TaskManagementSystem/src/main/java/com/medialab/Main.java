package com.medialab;

import java.util.List;

import com.medialab.controller.CategoryController;
import com.medialab.controller.ReminderController;
import com.medialab.controller.TaskController;
import com.medialab.model.Task.TaskStatus;
import com.medialab.model.*;
import com.medialab.util.DataManager;
import com.medialab.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene; // Import Scene class
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {
    private TaskController taskController;
    private CategoryController categoryController;
    private ReminderController reminderController;
    private DataManager dataManager;

    @Override
    public void start(Stage primaryStage) {
        // Initialize controllers
        taskController = new TaskController();
        categoryController = new CategoryController();
        reminderController = new ReminderController();

        // Initialize DataManager
        dataManager = new DataManager(taskController, categoryController, reminderController);

        // Load data from JSON files
        dataManager.loadAllData();

        // Initialize MainView and pass the controllers to it
        MainView mainView = new MainView(taskController, categoryController, reminderController);

        // Set up the primary stage
        primaryStage.setTitle("Medialab Assistant");
        primaryStage.setScene(new Scene(mainView, 800, 600)); // Create a Scene with MainView
        primaryStage.show();

        // Check for delayed tasks and today's reminders
        checkForDelayedTasks();
        checkForTodaysReminders();

        // Save data to JSON files when the application is closed
        primaryStage.setOnCloseRequest(event -> {
            dataManager.saveAllData();
        });
    }

    private void checkForDelayedTasks() {
        List<Task> delayedTasks = taskController.getTasksByStatus(TaskStatus.DELAYED);
        if (!delayedTasks.isEmpty()) {
            String message = "You have " + delayedTasks.size() + " delayed task(s).\n";
            for (Task task : delayedTasks) {
                message += "- " + task.getTitle() + "\n";
            }
            showAlert("Delayed Tasks", message);
        }
    }

    private void checkForTodaysReminders() {
        List<Reminder> todaysReminders = reminderController.getRemindersForToday();
        if (!todaysReminders.isEmpty()) {
            String message = "You have reminders for the following tasks today:\n";
            for (Reminder reminder : todaysReminders) {
                message += "- " + reminder.getTask().getTitle() + "\n";
            }
            showAlert("Today's Reminders", message);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}