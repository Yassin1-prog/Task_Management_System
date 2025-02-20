package com.medialab.view.components;

import java.time.format.DateTimeFormatter;

import com.medialab.model.Task;
import com.medialab.model.Task.TaskStatus;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TaskView extends VBox {
    private Task task;
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public TaskView(Task task) {
        this.task = task;
        this.getStyleClass().add("task-view");
        render();
    }

    private void render() {
        // Title section
        Label titleIcon = new Label("📝");
        titleIcon.setStyle("-fx-font-size: 18px;");
        Label titleLabel = new Label(task.getTitle());
        titleLabel.getStyleClass().add("task-title");
        
        HBox titleBox = new HBox(10, titleIcon, titleLabel);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        // Description
        Label descriptionLabel = new Label(task.getDescription());
        descriptionLabel.getStyleClass().add("task-description");
        descriptionLabel.setWrapText(true);

        // Metadata section
        VBox metadataBox = new VBox(8);
        metadataBox.getStyleClass().add("task-metadata");

        // Priority with icon
        Label priorityLabel = new Label("⭐ " + task.getPriority().getName());
        priorityLabel.getStyleClass().addAll("metadata-label", "priority-label");

        // Deadline with icon
        Label deadlineLabel = new Label("📅 " + task.getDeadline().format(DATE_FORMATTER));
        deadlineLabel.getStyleClass().addAll("metadata-label", "deadline-label");

        // Status with dynamic icon
        Label statusLabel = new Label("⏳ " + task.getStatus().toString());
        statusLabel.getStyleClass().addAll("metadata-label", "status-label", 
            task.getStatus().toString());

        metadataBox.getChildren().addAll(priorityLabel, deadlineLabel, statusLabel);

        // Add all components
        this.setSpacing(10);
        this.getChildren().addAll(titleBox, descriptionLabel, metadataBox);
    }

    public Task getTask() {
        return task;
    }
}