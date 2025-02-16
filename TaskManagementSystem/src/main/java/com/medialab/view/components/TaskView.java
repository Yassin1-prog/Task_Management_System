package com.medialab.view.components;

import com.medialab.model.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TaskView extends VBox {
    private Task task;

    public TaskView(Task task) {
        this.task = task;
        render();
    }

    private void render() {
        // Display task details
        Label titleLabel = new Label("Title: " + task.getTitle());
        Label descriptionLabel = new Label("Description: " + task.getDescription());
        Label categoryLabel = new Label("Category: " + task.getCategory().getName());
        Label priorityLabel = new Label("Priority: " + task.getPriority().getName());
        Label deadlineLabel = new Label("Deadline: " + task.getDeadline().toString());
        Label statusLabel = new Label("Status: " + task.getStatus().toString());

        // Add all labels to the VBox
        this.getChildren().addAll(titleLabel, descriptionLabel, categoryLabel, priorityLabel, deadlineLabel, statusLabel);

        // Add styling (optional)
        this.setSpacing(5);
        this.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10;");
    }

    public Task getTask() {
        return task;
    }
}