package com.medialab.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private List<Task> tasks;

    public Category(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    // Getters and setters
    public String getName() {
        return this.name;
    }

    public void setName(String newname) {
        this.name = newname;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public void removeAllTasks() {
        this.tasks.clear();
    }
}