package com.medialab.view;

import com.medialab.controller.ReminderController;
import com.medialab.controller.TaskController;
import com.medialab.model.Reminder;
import com.medialab.model.Task;
import com.medialab.view.components.ReminderView;
import com.medialab.view.dialogs.ReminderDialog;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ReminderListView extends BorderPane {
    private ReminderController reminderController;
    private TaskController taskController; // Needed to populate tasks in the dialog
    private ListView<ReminderView> reminderListView;

    public ReminderListView(ReminderController reminderController, TaskController taskController) {
        this.reminderController = reminderController;
        this.taskController = taskController; // Initialize TaskController
        reminderListView = new ListView<>();
        reminderListView.getStyleClass().add("reminder-list");

        // Load reminders into the list
        loadReminders();

        // Create styled buttons with icons
        Button addButton = createStyledButton("➕ Add Reminder", "add-button");
        Button editButton = createStyledButton("✏️ Edit Reminder", "edit-button");
        Button deleteButton = createStyledButton("🚮 Delete Reminder", "delete-button");

        addButton.setOnAction(e -> addReminder());
        editButton.setOnAction(e -> editReminder());
        deleteButton.setOnAction(e -> deleteReminder());

        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        buttonBox.getStyleClass().add("category-button-box");

        this.setTop(buttonBox);
        this.setCenter(reminderListView);
        
        // Add padding to the main container
        this.setPadding(new Insets(10));
    }

    private Button createStyledButton(String text, String styleClass) {
        Button button = new Button(text);
        button.getStyleClass().add(styleClass);
        return button;
    }

    private void loadReminders() {
        reminderListView.getItems().clear();
        for (Reminder reminder : reminderController.getAllReminders()) {
            reminder.updateReminder(reminder.getType(), reminder.getReminderDate()); // in case task deadline gets changed
            reminderListView.getItems().add(new ReminderView(reminder));
        }
    }

    private void addReminder() {
        // Open ReminderDialog for adding a new reminder
        ReminderDialog dialog = new ReminderDialog(taskController);
        dialog.showAndWait().ifPresent(reminder -> {
            reminderController.createReminder(reminder.getTask(), reminder.getType(), reminder.getReminderDate());
            loadReminders(); // Refresh the reminder list
        });
    }

    private void editReminder() {
        // Open ReminderDialog for editing the selected reminder
        ReminderView selectedReminderView = reminderListView.getSelectionModel().getSelectedItem();
        if (selectedReminderView != null) {
            ReminderDialog dialog = new ReminderDialog(taskController);
            dialog.setReminder(selectedReminderView.getReminder());
            dialog.showAndWait().ifPresent(reminder -> {
                reminderController.updateReminder(selectedReminderView.getReminder(), reminder.getType(), reminder.getReminderDate());
                loadReminders(); // Refresh the reminder list
            });
        }
    }

    private void deleteReminder() {
        // Delete the selected reminder
        ReminderView selectedReminderView = reminderListView.getSelectionModel().getSelectedItem();
        if (selectedReminderView != null) {
            reminderController.deleteReminder(selectedReminderView.getReminder());
            loadReminders(); // Refresh the reminder list
        }
    }
}
