package com.medialab.util;

import com.medialab.model.*;
import com.google.gson.reflect.TypeToken;
import com.medialab.controller.*;
import java.util.*;

public class DataManager {
    private final TaskController taskController;
    private final CategoryController categoryController;
    private final ReminderController reminderController;

    public DataManager(TaskController taskController, 
                      CategoryController categoryController,
                      ReminderController reminderController) {
        this.taskController = taskController;
        this.categoryController = categoryController;
        this.reminderController = reminderController;
    }

    public void saveAllData() {
        JsonManager.initializeDataDirectory();
        
        // Save all data to respective files
        JsonManager.saveToJson(taskController.getAllTasks(), "tasks.json");
        JsonManager.saveToJson(categoryController.getAllCategories(), "categories.json");
        JsonManager.saveToJson(taskController.getAllPriorities(), "priorities.json");
        JsonManager.saveToJson(reminderController.getAllReminders(), "reminders.json");
    }

    public void loadAllData() {
        // Load categories
        List<Category> categories = JsonManager.loadFromJson(
            "categories.json", 
            new TypeToken<List<Category>>() {}.getType()
        );
        if (categories != null) {
            categoryController.initializeCategories(categories);
        }

        // Load priorities
        List<Priority> priorities = JsonManager.loadFromJson(
            "priorities.json", 
            new TypeToken<List<Priority>>() {}.getType()
        );
        if (priorities != null) {
            taskController.initializePriorities(priorities);
        }

        // Load tasks
        List<Task> tasks = JsonManager.loadFromJson(
            "tasks.json", 
            new TypeToken<List<Task>>() {}.getType()
        );
        if (tasks != null) {
            taskController.initializeTasks(tasks);
        }

        // Load reminders
        List<Reminder> reminders = JsonManager.loadFromJson(
            "reminders.json", 
            new TypeToken<List<Reminder>>() {}.getType()
        );
        if (reminders != null) {
            reminderController.initializeReminders(reminders);
        }
    }
}