package com.medialab.model;

import java.util.ArrayList;
import java.util.List;

public class Priority {
    private String name;
    private boolean isDefault;
    private List<Task> tasks;

    public Priority(String name, boolean isDefault) {
        this.name = name;
        this.isDefault = isDefault;
        this.tasks = new ArrayList<>();
    }

    // Getters and setters

    public void setName(String newName) {
        this.name = newName;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public boolean isDefaultPriority() {
        return isDefault;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

}