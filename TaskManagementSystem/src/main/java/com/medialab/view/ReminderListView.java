package com.medialab.view;

import com.medialab.controller.ReminderController;
import com.medialab.controller.TaskController;
import com.medialab.model.Reminder;
import com.medialab.model.Task;
import com.medialab.view.components.ReminderView;
import com.medialab.view.dialogs.ReminderDialog;

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

        // Load reminders into the list
        loadReminders();

        // Add buttons for adding/editing/deleting reminders
        Button addButton = new Button("Add Reminder");
        addButton.setOnAction(e -> addReminder());

        Button editButton = new Button("Edit Reminder");
        editButton.setOnAction(e -> editReminder());

        Button deleteButton = new Button("Delete Reminder");
        deleteButton.setOnAction(e -> deleteReminder());

        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        this.setTop(buttonBox);
        this.setCenter(reminderListView);
    }

    private void loadReminders() {
        reminderListView.getItems().clear();
        for (Reminder reminder : reminderController.getAllReminders()) {
            reminderListView.getItems().add(new ReminderView(reminder));
        }
    }

    private void addReminder() {
        // Open ReminderDialog for adding a new reminder
        ReminderDialog dialog = new ReminderDialog();
        dialog.setTasks(taskController.getAllTasks()); // Populate tasks in the dialog
        dialog.showAndWait().ifPresent(reminder -> {
            reminderController.createReminder(reminder.getTask(), reminder.getType(), reminder.getReminderDate());
            loadReminders(); // Refresh the reminder list
        });
    }

    private void editReminder() {
        // Open ReminderDialog for editing the selected reminder
        ReminderView selectedReminderView = reminderListView.getSelectionModel().getSelectedItem();
        if (selectedReminderView != null) {
            ReminderDialog dialog = new ReminderDialog();
            dialog.setReminder(selectedReminderView.getReminder());
            dialog.setTasks(taskController.getAllTasks()); // Populate tasks in the dialog
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
