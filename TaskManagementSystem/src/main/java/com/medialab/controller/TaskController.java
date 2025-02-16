package com.medialab.controller;

import com.medialab.model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TaskController {
    private List<Task> tasks;
    private List<Priority> priorities;

    public TaskController() {
        tasks = new ArrayList<>();
        priorities = new ArrayList<>();
        priorities.add(new Priority("Default", true));  
    }

    public void initializeTasks(List<Task> loadedTasks) {
        tasks.clear();
        tasks.addAll(loadedTasks);
    }

    public void initializePriorities(List<Priority> loadedPriorities) {
        priorities.clear();
        priorities.addAll(loadedPriorities);
    }
    
    public void createDefaultPriority() {
        priorities.add(new Priority("Default", true));
    }

    public Priority getDefaultPriority() {
        for(Priority priority : priorities) {
            if(priority.isDefaultPriority()) {
                return priority;
            }
        }
        return null;
    }

    public void createTask(String title, String description, Category category,
                          Priority priority, LocalDate deadline) {
        Task newTask = new Task(title, description, category, priority, deadline);
        tasks.add(newTask);
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
        List<Task> results = tasks.stream()
        .filter(task -> task.getTitle().contains(title) || 
                        task.getCategory().equals(category) || 
                        task.getPriority().equals(priority))
        .collect(Collectors.toList());
        return results;
    }

    public List<Task> searchTasks(String title, String categoryName, String priorityName) {
        List<Task> results = tasks.stream()
        .filter(task -> task.getTitle().contains(title) || 
                        task.getCategoryName().contains(categoryName) || 
                        task.getPriorityName().equals(priorityName))
        .collect(Collectors.toList());
        return results;
    }

    public void checkDelayedTasks() {
        for(Task task : tasks) {
            task.updateStatus();
        }
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public List<Priority> getAllPriorities() {
        return priorities;
    }

    public boolean priorityExists(String name) {
        return priorities.stream().anyMatch(priority -> priority.getName().equals(name));
    }

    public void createPriority(String name) {
        priorities.add(new Priority(name, false));
    }

    public void updatePriority(Priority priority, String newName) {
        priority.setName(newName);
    }

    public void deletePriority(Priority priority) {
        priorities.remove(priority);
        updateTaskPriorities(priority, getDefaultPriority());
    }

    public List<Task> getTasksDueWithinDays(int days) {
        LocalDate now = LocalDate.now();
        LocalDate futureDate = now.plusDays(days);
        List<Task> results = tasks.stream()
        .filter(task -> task.getDeadline().isBefore(futureDate) && !task.getDeadline().isBefore(now))
        .collect(Collectors.toList());
        return results;
    }

    // Delete tasks by category (needed when deleting a category)
    public void deleteTasksByCategory(Category category) {
        tasks.removeIf(task -> task.getCategory().equals(category));
    }

    // Update task priorities when a priority level is deleted
    public void updateTaskPriorities(Priority deletedPriority, Priority newPriority) {
        for (Task task : tasks) {
            if (task.getPriority().equals(deletedPriority)) {
                task.setPriority(newPriority);
            }
        }
    }

    // Get tasks by category (needed for UI display)
    public List<Task> getTasksByCategory(Category category) {
        return tasks.stream()
            .filter(task -> task.getCategory().equals(category))
            .collect(Collectors.toList());
    }

    // Get tasks by status
    public List<Task> getTasksByStatus(Task.TaskStatus status) {
        return tasks.stream()
            .filter(task -> task.getStatus() == status)
            .collect(Collectors.toList());
    }

    // Statistics methods
    public int getTotalTaskCount() {
        return tasks.size();
    }

    public long getCompletedTaskCount() {
        long nbr = tasks.stream().filter(task -> task.getStatus() == Task.TaskStatus.COMPLETED).count();
        return nbr;
    }

    public long getDelayedTaskCount() {
        long nbr = tasks.stream().filter(task -> task.getStatus() == Task.TaskStatus.DELAYED).count();
        return nbr;
    }
}