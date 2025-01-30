package controller;

import model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class TaskController {
    private List<Task> tasks;
    private List<Category> categories;
    private List<Priority> priorities;

    public TaskController() {
        tasks = new ArrayList<>();
        categories = new ArrayList<>();
        priorities = new ArrayList<>();
        //Initialize with default priority
        priorities.add(new Priority("Default", true));
    }

    public void createTask(String title, String description, Category category,
                          Priority priority, LocalDate deadline) {
        // TODO: Create and add new task
        Task newTask = new Task(title, description, category, priority, deadline);
        tasks.add(newTask);
        category.addTask(newTask);
        categories.add(category);
    }

    public void createPriority(String name) {
        priorities.add(new Priority(name, false));
    }

    public void updatePriority(Priority priority, String name) {
        priority.setName(name);
    }

    public void deletePriority(Priority priority) {
        priorities.remove(priority);
    }

    public void updateTask(Task task, String title, String description,
                          Category category, Priority priority, LocalDate deadline) {
        //Update task details
        task.updateTask(title, description, category, priority, deadline);
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> searchTasks(String title, Category category, Priority priority) {
        // TODO: Implement search functionality based on criteria
        return new ArrayList<>();
    }

    public void checkDelayedTasks() {
        for(Task task : tasks) {
            task.updateStatus();
        }
    }

    public List<Task> getTasksDueWithinDays(int days) {
        // TODO: Return tasks due within specified days
        return new ArrayList<>();
    }

    // Statistics methods
    public int getTotalTaskCount() {
        return tasks.size();
    }

    public int getCompletedTaskCount() {
        // TODO: Count completed tasks
        return 0;
    }

    public int getDelayedTaskCount() {
        // TODO: Count delayed tasks
        return 0;
    }
}
