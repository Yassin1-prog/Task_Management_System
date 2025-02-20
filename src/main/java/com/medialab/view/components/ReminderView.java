package com.medialab.view.components;

import java.time.format.DateTimeFormatter;

import com.medialab.model.Reminder;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ReminderView extends HBox {
    private Reminder reminder;
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public ReminderView(Reminder reminder) {
        this.reminder = reminder;
        this.getStyleClass().add("reminder-view");
        render();
    }

    private void render() {
        // Create main icon
        Label reminderIcon = new Label("🔔");
        reminderIcon.getStyleClass().addAll("icon-label");
        
        // Create VBox for task information
        VBox taskInfo = new VBox(5);
        taskInfo.getStyleClass().add("info-box");
        
        // Task title with icon
        HBox taskBox = new HBox(5);
        Label taskIcon = new Label("📝");
        taskIcon.getStyleClass().add("icon-label");
        
        Label taskLabel = new Label(reminder.getTask().getTitle());
        taskLabel.getStyleClass().add("task-title");
        taskBox.getChildren().addAll(taskIcon, taskLabel);
        
        // Reminder type with custom styling
        Label typeLabel = new Label("📧 " + 
            reminder.getType().toString());
        typeLabel.getStyleClass().add("reminder-type");
        
        // Date with icon
        HBox dateBox = new HBox(5);
        Label dateIcon = new Label("📅");
        dateIcon.getStyleClass().add("icon-label");
        
        Label dateLabel = new Label(reminder.getReminderDate().format(DATE_FORMATTER));
        dateLabel.getStyleClass().add("reminder-date");
        dateBox.getChildren().addAll(dateIcon, dateLabel);
        
        // Add all components to the task info box
        taskInfo.getChildren().addAll(taskBox, typeLabel, dateBox);
        
        // Set spacing and alignment
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(reminderIcon, taskInfo);
        
        // Make the task info box expand to fill available space
        HBox.setHgrow(taskInfo, Priority.ALWAYS);
    }

    public Reminder getReminder() {
        return reminder;
    }
}