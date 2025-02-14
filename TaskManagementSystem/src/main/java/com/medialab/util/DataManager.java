package com.medialab.util;

import com.medialab.model.*;
import com.medialab.controller.*;
import java.util.*;

public class DataManager {
    private final TaskController taskController;
    private final CategoryController categoryController;
    private final ReminderController reminderController;
    private final PriorityController priorityController;

    public DataManager(TaskController taskController, 
                      CategoryController categoryController,
                      ReminderController reminderController,
                      PriorityController priorityController) {
        this.taskController = taskController;
        this.categoryController = categoryController;
        this.reminderController = reminderController;
        this.priorityController = priorityController;
    }

    public void saveAllData() {
        JsonManager.initializeDataDirectory();
        
        // Save all data to respective files
        JsonManager.saveToJson(taskController.getAllTasks(), "tasks.json");
        JsonManager.saveToJson(categoryController.getAllCategories(), "categories.json");
        JsonManager.saveToJson(priorityController.getAllPriorities(), "priorities.json");
        JsonManager.saveToJson(reminderController.getAllReminders(), "reminders.json");
    }

    @SuppressWarnings("unchecked")
    public void loadAllData() {
        // Load and initialize all data
        JsonManager.initializeDataDirectory();
        
        // Load categories first as tasks depend on them
        List<Category> categories = JsonManager.loadFromJson("categories.json", List.class);
        if (categories != null) {
            categoryController.initializeCategories(categories);
        }

        // Load priorities
        List<Priority> priorities = JsonManager.loadFromJson("priorities.json", List.class);
        if (priorities != null) {
            priorityController.initializePriorities(priorities);
        } else {
            priorityController.createDefaultPriority();
        }

        // Load tasks
        List<Task> tasks = JsonManager.loadFromJson("tasks.json", List.class);
        if (tasks != null) {
            taskController.initializeTasks(tasks);
        }

        // Load reminders
        List<Reminder> reminders = JsonManager.loadFromJson("reminders.json", List.class);
        if (reminders != null) {
            reminderController.initializeReminders(reminders);
        }

        // Check for delayed tasks after loading
        taskController.checkDelayedTasks();
    }
}