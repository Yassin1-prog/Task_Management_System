package com.medialab;

import com.medialab.controller.*;
import com.medialab.model.*;
import com.medialab.util.DataManager;

import java.time.LocalDate;
import java.util.List;

public class TestMain {
    private static TaskController taskController;
    private static CategoryController categoryController;
    private static ReminderController reminderController;
    private static PriorityController priorityController;
    private static DataManager dataManager;

    public static void main(String[] args) {
        // Initialize controllers
        initializeControllers();

        // Run test scenarios
        testCategoryOperations();
        testTaskOperations();
        testReminderOperations();
        
        // Save all data
        saveData();
        
        // Load data to verify persistence
        loadData();
        
        // Print final state
        printCurrentState();
    }

    private static void initializeControllers() {
        taskController = new TaskController();
        categoryController = new CategoryController();
        reminderController = new ReminderController();
        priorityController = new PriorityController();
        dataManager = new DataManager(taskController, categoryController, reminderController, priorityController);
    }

    private static void testCategoryOperations() {
        System.out.println("\n=== Testing Category Operations ===");
      
        categoryController.createCategory("Work");
        categoryController.createCategory("Personal");
        
        // Print categories
        System.out.println("Created categories:");
        categoryController.getAllCategories().forEach(category -> 
            System.out.println(" - " + category.getName()));
        
        // Update category
        List<Category> categories = categoryController.getAllCategories();
        categoryController.updateCategory(categories.get(0), "Work Projects");
        
        System.out.println("\nAfter updating 'Work' to 'Work Projects':");
        categoryController.getAllCategories().forEach(category -> 
            System.out.println(" - " + category.getName()));
    }

    private static void testTaskOperations() {
        System.out.println("\n=== Testing Task Operations ===");
        
        // Get a category
        Category category = categoryController.getAllCategories().get(0);
        
        // Create tasks
        taskController.createTask(
            "Complete Project",
            "Finish the Java project implementation",
            category,
            priorityController.getDefaultPriority(),
            LocalDate.now().plusDays(7)
        );

        taskController.createTask(
            "Past Due Task",
            "This task is already delayed",
            category,
            priorityController.getDefaultPriority(),
            LocalDate.now().minusDays(1)
        );

        // Print tasks
        System.out.println("\nCreated tasks:");
        taskController.getAllTasks().forEach(task -> 
            System.out.println(" - " + task.getTitle() + " (Due: " + task.getDeadline() + ")"));
        
        // Test search
        System.out.println("\nSearching for tasks with 'Project' in title:");
        taskController.searchTasks("Project", null, null).forEach(task ->
            System.out.println(" - " + task.getTitle()));

        // Check delayed tasks
        taskController.checkDelayedTasks();
        System.out.println("\nDelayed tasks count: " + taskController.getDelayedTaskCount());
    }

    private static void testReminderOperations() {
        System.out.println("\n=== Testing Reminder Operations ===");
        
        // Get a task
        Task task = taskController.getAllTasks().get(0);
        
        // Create reminders
        reminderController.createReminder(
            task,
            Reminder.ReminderType.ONE_DAY_BEFORE,
            null
        );

        reminderController.createReminder(
            task,
            Reminder.ReminderType.CUSTOM_DATE,
            LocalDate.now().plusDays(2)
        );

        // Print reminders
        System.out.println("\nActive reminders:");
        reminderController.getActiveReminders().forEach(reminder ->
            System.out.println(" - " + reminder.getTask().getTitle() + 
                             " (Reminder date: " + reminder.getReminderDate() + ")"));
    }

    private static void saveData() {
        System.out.println("\n=== Saving Data ===");
        try {
            dataManager.saveAllData();
            System.out.println("Data saved successfully");
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
           //e.printStackTrace();
        }
    }

    private static void loadData() {
        System.out.println("\n=== Loading Data ===");
        try {
            dataManager.loadAllData();
            System.out.println("Data loaded successfully");
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    private static void printCurrentState() {
        System.out.println("\n=== Current System State ===");
        System.out.println("Categories:");
        categoryController.getAllCategories().forEach(category ->
            System.out.println(" - " + category.getName()));

        System.out.println("\nTasks:");
        taskController.getAllTasks().forEach(task ->
            System.out.println(" - " + task.getTitle() + " (Status: " + task.getStatus() + ")"));

        System.out.println("\nReminders:");
        reminderController.getActiveReminders().forEach(reminder ->
            System.out.println(" - " + reminder.getTask().getTitle() + 
                             " (Date: " + reminder.getReminderDate() + ")"));
        
        System.out.println("\nStatistics:");
        System.out.println("Total tasks: " + taskController.getTotalTaskCount());
        System.out.println("Completed tasks: " + taskController.getCompletedTaskCount());
        System.out.println("Delayed tasks: " + taskController.getDelayedTaskCount());
    }
}