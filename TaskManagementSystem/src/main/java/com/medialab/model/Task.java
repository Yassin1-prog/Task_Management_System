package com.medialab.model;

import java.time.LocalDate;

/**
 * Represents a task with a title, description, category, priority, deadline, and status.
 */
public class Task {
    private String title;
    private String description;
    private Category category;
    private Priority priority;
    private LocalDate deadline;
    private TaskStatus status;

    /**
     * Enum representing the status of a task.
     */
    public enum TaskStatus {
        OPEN, IN_PROGRESS, POSTPONED, COMPLETED, DELAYED
    }

    /**
     * Constructs a new Task.
     *
     * @param title       the title of the task
     * @param description the description of the task
     * @param category    the category of the task
     * @param priority    the priority of the task
     * @param deadline    the deadline of the task
     * @param status      the status of the task
     */
    public Task(String title, String description, Category category, 
                Priority priority, LocalDate deadline, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
    }

    /**
     * Gets the priority of the task.
     *
     * @return the priority of the task
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Gets the title of the task.
     *
     * @return the title of the task
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the task.
     *
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the category of the task.
     *
     * @return the category of the task
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Gets the name of the category.
     *
     * @return the name of the category
     */
    public String getCategoryName() {
        return category.getName();
    }

    /**
     * Gets the name of the priority.
     *
     * @return the name of the priority
     */
    public String getPriorityName() {
        return priority.getName();
    }

    /**
     * Gets the status of the task, updating it if the deadline has passed and the task is not completed.
     *
     * @return the status of the task
     */
    public TaskStatus getStatus() {
        if (this.deadline.isBefore(LocalDate.now()) && this.status != TaskStatus.COMPLETED) {
            this.status = TaskStatus.DELAYED;
        }
        return this.status;
    }

    /**
     * Sets the priority of the task.
     *
     * @param priority the new priority of the task
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Sets the category of the task.
     *
     * @param category the new category of the task
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Updates the task details.
     *
     * @param title       the new title of the task
     * @param description the new description of the task
     * @param category    the new category of the task
     * @param priority    the new priority of the task
     * @param deadline    the new deadline of the task
     * @param status      the new status of the task
     */
    public void updateTask(String title, String description, Category category, 
                           Priority priority, LocalDate deadline, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
    }

    /**
     * Gets the deadline of the task.
     *
     * @return the deadline of the task
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * Checks if the task is delayed.
     *
     * @return true if the task is delayed, false otherwise
     */
    public boolean isDelayed() {
        return this.status == TaskStatus.DELAYED;
    }

    /**
     * Updates the status of the task based on the deadline.
     */
    public void updateStatus() {
        if (this.deadline.isBefore(LocalDate.now()) && this.status != TaskStatus.COMPLETED) {
            this.status = TaskStatus.DELAYED;
        }
    }
}