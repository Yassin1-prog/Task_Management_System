// Main.java
package com.medialab;

import com.medialab.controller.*;
import com.medialab.util.DataManager;
//import com.medialab.view.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Platform;

public class Main extends Application {
    private DataManager dataManager;
    private TaskController taskController;
    private CategoryController categoryController;
    private ReminderController reminderController;
    private PriorityController priorityController;

    @Override
    public void start(Stage primaryStage) {
        // Initialize controllers
        initializeControllers();

        // Load saved data
        loadData();

        // Check for delayed tasks and show notification if needed
        checkDelayedTasks();

        // Initialize and show main window
        //MainWindow mainWindow = new MainWindow(primaryStage, taskController, 
                                             //categoryController, reminderController);
        //mainWindow.show();

        // Add shutdown hook to save data
        Platform.setImplicitExit(true);
        primaryStage.setOnCloseRequest(event -> {
            saveData();
            Platform.exit();
        });
    }

    private void initializeControllers() {
        taskController = new TaskController();
        categoryController = new CategoryController();
        reminderController = new ReminderController();
        priorityController = new PriorityController();
        dataManager = new DataManager(taskController, categoryController, reminderController, priorityController);
    }

    private void loadData() {
        try {
            dataManager.loadAllData();
        } catch (Exception e) {
            // TODO: Show error dialog to user
            e.printStackTrace();
        }
    }

    private void saveData() {
        try {
            dataManager.saveAllData();
        } catch (Exception e) {
            // TODO: Show error dialog to user
            e.printStackTrace();
        }
    }

    private void checkDelayedTasks() {
        long delayedTaskCount = taskController.getDelayedTaskCount();
        if (delayedTaskCount > 0) {
            // TODO: Show notification dialog about delayed tasks
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}