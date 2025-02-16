package com.medialab.view.dialogs;

import com.medialab.model.Reminder;
import com.medialab.model.Task;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.time.LocalDate;

public class ReminderDialog extends Dialog<Reminder> {
    private ComboBox<Task> taskComboBox;
    private ComboBox<Reminder.ReminderType> typeComboBox;
    private DatePicker customDatePicker;

    public ReminderDialog() {
        this.setTitle("Add/Edit Reminder");

        // Create form fields
        taskComboBox = new ComboBox<>();
        typeComboBox = new ComboBox<>();
        customDatePicker = new DatePicker();

        // Populate the type combo box
        typeComboBox.getItems().addAll(Reminder.ReminderType.values());

        // Set up the dialog buttons
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the form layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Task:"), 0, 0);
        grid.add(taskComboBox, 1, 0);
        grid.add(new Label("Type:"), 0, 1);
        grid.add(typeComboBox, 1, 1);
        grid.add(new Label("Custom Date:"), 0, 2);
        grid.add(customDatePicker, 1, 2);

        this.getDialogPane().setContent(grid);

        // Enable/disable the save button depending on validation
        this.getDialogPane().lookupButton(saveButtonType).setDisable(true);

        // Add validation
        taskComboBox.valueProperty().addListener((observable, oldValue, newValue) -> validateForm());
        typeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> validateForm());
        customDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> validateForm());

        // Set the result converter
        this.setResultConverter(new Callback<ButtonType, Reminder>() {
            @Override
            public Reminder call(ButtonType buttonType) {
                if (buttonType == saveButtonType) {
                    return new Reminder(
                            taskComboBox.getValue(),
                            typeComboBox.getValue(),
                            customDatePicker.getValue()
                    );
                }
                return null;
            }
        });
    }

    private void validateForm() {
        boolean isValid = taskComboBox.getValue() != null &&
                          typeComboBox.getValue() != null &&
                          (typeComboBox.getValue() != Reminder.ReminderType.CUSTOM_DATE || customDatePicker.getValue() != null);
        this.getDialogPane().lookupButton(ButtonType.OK).setDisable(!isValid);
    }

    public void setReminder(Reminder reminder) {
        taskComboBox.setValue(reminder.getTask());
        typeComboBox.setValue(reminder.getType());
        customDatePicker.setValue(reminder.getReminderDate());
    }

    public void setTasks(java.util.List<Task> tasks) {
        taskComboBox.getItems().addAll(tasks);
    }
}