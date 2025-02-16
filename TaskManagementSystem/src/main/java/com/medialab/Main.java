package com.medialab;

import com.medialab.controller.CategoryController;
import com.medialab.controller.ReminderController;
import com.medialab.controller.TaskController;
import com.medialab.util.DataManager;
import com.medialab.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene; // Import Scene class
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

        // Save data to JSON files when the application is closed
        primaryStage.setOnCloseRequest(event -> {
            dataManager.saveAllData();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}