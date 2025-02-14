package com.medialab.controller;

import com.medialab.model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TaskController {
    private List<Task> tasks;

    public TaskController() {
        tasks = new ArrayList<>();
    }

    public void initializeTasks(List<Task> loadedTasks) {
        tasks.clear();
        tasks.addAll(loadedTasks);
    }

    public void createTask(String title, String description, Category category,
                          Priority priority, LocalDate deadline) {
        Task newTask = new Task(title, description, category, priority, deadline);
        tasks.add(newTask);
        category.addTask(newTask);
        priority.addTask(newTask);
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

    public void checkDelayedTasks() {
        for(Task task : tasks) {
            task.updateStatus();
        }
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public List<Task> getTasksDueWithinDays(int days) {
        // TODO: Return tasks due within specified days
        return new ArrayList<>();
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