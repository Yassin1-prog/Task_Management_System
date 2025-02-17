package com.medialab.view.dialogs;

import com.medialab.controller.TaskController;
import com.medialab.model.Reminder;
import com.medialab.model.Task;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.List;

public class ReminderDialog extends Dialog<Reminder> {
    private TaskController taskController;
    private ComboBox<String> taskComboBox;
    private ComboBox<Reminder.ReminderType> typeComboBox;
    private DatePicker customDatePicker;

    private final ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);

    public ReminderDialog(TaskController taskController) {
        this.taskController = taskController;
        this.setTitle("Add/Edit Reminder");

        // Create form fields
        taskComboBox = new ComboBox<>();
        typeComboBox = new ComboBox<>();
        customDatePicker = new DatePicker();

        // Populate the type combo box and tasks
        typeComboBox.getItems().addAll(Reminder.ReminderType.values());
        taskController.getAllTasks().stream()
            .filter(task -> task.getStatus() != Task.TaskStatus.COMPLETED) // Filter out completed tasks
            .forEach(task -> taskComboBox.getItems().add(task.getTitle())); // Add remaining tasks to the combo box

        // Set up the dialog buttons
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
        customDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> validateForm());

        // datepicker is disabled if the type is not custom date.
        typeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean isCustomDate = newValue == Reminder.ReminderType.CUSTOM_DATE;
            customDatePicker.setDisable(!isCustomDate);      
            if (!isCustomDate) {
                customDatePicker.setValue(null); // Clear the date if not CUSTOM_DATE
            }
            validateForm();
        });

        // Set the result converter
        this.setResultConverter(new Callback<ButtonType, Reminder>() {
            @Override
            public Reminder call(ButtonType buttonType) {
                if (buttonType == saveButtonType) {
                    return new Reminder(
                            taskController.getTaskByTitle(taskComboBox.getValue()),
                            typeComboBox.getValue(),
                            customDatePicker.getValue()
                    );
                }
                return null;
            }
        });
    }

    private void validateForm() {
        boolean isCustomDate = typeComboBox.getValue() == Reminder.ReminderType.CUSTOM_DATE;
        // reminders onnly make sense if they are not after deadlines and are after today
        boolean hasValidDate = customDatePicker.getValue() != null && customDatePicker.getValue().isAfter(LocalDate.now()) 
        && !customDatePicker.getValue().isAfter(taskController.getTaskByTitle(taskComboBox.getValue()).getDeadline()); 
    
        boolean isValid = taskComboBox.getValue() != null &&
                          typeComboBox.getValue() != null &&
                          (!isCustomDate || hasValidDate); // Only check date if CUSTOM_DATE is selected
    
        this.getDialogPane().lookupButton(saveButtonType).setDisable(!isValid);
    }
    

    public void setReminder(Reminder reminder) {
        taskComboBox.setValue(reminder.getTaskTitle());
        typeComboBox.setValue(reminder.getType());
        customDatePicker.setValue(reminder.getReminderDate());
    }
}