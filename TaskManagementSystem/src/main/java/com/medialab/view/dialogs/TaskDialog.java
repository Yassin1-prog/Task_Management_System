package com.medialab.view.dialogs;

import com.medialab.model.Task;
import com.medialab.model.Category;
import com.medialab.model.Priority;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.time.LocalDate;

public class TaskDialog extends Dialog<Task> {
    private TextField titleField;
    private TextArea descriptionField;
    private ComboBox<Category> categoryComboBox;
    private ComboBox<Priority> priorityComboBox;
    private DatePicker deadlinePicker;

    public TaskDialog() {
        this.setTitle("Add/Edit Task");

        // Create form fields
        titleField = new TextField();
        descriptionField = new TextArea();
        categoryComboBox = new ComboBox<>();
        priorityComboBox = new ComboBox<>();
        deadlinePicker = new DatePicker();

        // Set up the dialog buttons
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the form layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Category:"), 0, 2);
        grid.add(categoryComboBox, 1, 2);
        grid.add(new Label("Priority:"), 0, 3);
        grid.add(priorityComboBox, 1, 3);
        grid.add(new Label("Deadline:"), 0, 4);
        grid.add(deadlinePicker, 1, 4);

        this.getDialogPane().setContent(grid);

        // Enable/disable the save button depending on validation
        this.getDialogPane().lookupButton(saveButtonType).setDisable(true);

        // Add validation
        titleField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        deadlinePicker.valueProperty().addListener((observable, oldValue, newValue) -> validateForm());

        // Set the result converter
        this.setResultConverter(new Callback<ButtonType, Task>() {
            @Override
            public Task call(ButtonType buttonType) {
                if (buttonType == saveButtonType) {
                    return new Task(
                            titleField.getText(),
                            descriptionField.getText(),
                            categoryComboBox.getValue(),
                            priorityComboBox.getValue(),
                            deadlinePicker.getValue()
                    );
                }
                return null;
            }
        });
    }

    private void validateForm() {
        boolean isValid = !titleField.getText().isEmpty() &&
                          deadlinePicker.getValue() != null &&
                          !deadlinePicker.getValue().isBefore(LocalDate.now());
        this.getDialogPane().lookupButton(ButtonType.OK).setDisable(!isValid);
    }

    public void setTask(Task task) {
        titleField.setText(task.getTitle());
        descriptionField.setText(task.getDescription());
        categoryComboBox.setValue(task.getCategory());
        priorityComboBox.setValue(task.getPriority());
        deadlinePicker.setValue(task.getDeadline());
    }
}