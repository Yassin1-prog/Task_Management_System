package com.medialab.model;

import java.time.LocalDate;

public class Task {
    private String title;
    private String description;
    private Category category;
    private Priority priority;
    private LocalDate deadline;
    private TaskStatus status;

    // Enum for task status
    public enum TaskStatus {
        OPEN, IN_PROGRESS, POSTPONED, COMPLETED, DELAYED
    }

    // Constructor
    public Task(String title, String description, Category category, 
                Priority priority, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.deadline = deadline;
        this.status = TaskStatus.OPEN;
    }

    // Getters and setters for all fields

    public Priority getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public Category getCategory() {
        return category;
    }

    public TaskStatus getStatus() {
        if(this.deadline.isBefore(LocalDate.now()) && this.status != TaskStatus.COMPLETED) {
            this.status = TaskStatus.DELAYED;
        }
        return this.status;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void updateTask(String title, String description, Category category, 
                           Priority priority, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.deadline = deadline;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean isDelayed() {
        return this.status == TaskStatus.DELAYED;
    }

    public void updateStatus() {
        if(this.deadline.isBefore(LocalDate.now()) && this.status != TaskStatus.COMPLETED) {
            this.status = TaskStatus.DELAYED;
        }
    }
}