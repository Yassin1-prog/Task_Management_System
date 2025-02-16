package com.medialab.view.components;

import com.medialab.model.Reminder;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ReminderView extends HBox {
    private Reminder reminder;

    public ReminderView(Reminder reminder) {
        this.reminder = reminder;
        render();
    }

    private void render() {
        // Display reminder details
        Label taskLabel = new Label("Task: " + reminder.getTask().getTitle());
        Label typeLabel = new Label("Type: " + reminder.getType().toString());
        Label dateLabel = new Label("Date: " + reminder.getReminderDate().toString());

        // Add styling (optional)
        this.setSpacing(10);
        this.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10;");

        // Add the labels to the HBox
        this.getChildren().addAll(taskLabel, typeLabel, dateLabel);
    }

    public Reminder getReminder() {
        return reminder;
    }
}