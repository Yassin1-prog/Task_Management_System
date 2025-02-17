package com.medialab.view.dialogs;

import com.medialab.model.Task;
import com.medialab.controller.CategoryController;
import com.medialab.controller.TaskController;
import com.medialab.model.Category;
import com.medialab.model.Priority;
import com.medialab.model.Reminder;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.List;

public class TaskDialog extends Dialog<Task> {
    private TaskController taskController;
    private CategoryController categoryController;
    private TextField titleField;
    private TextArea descriptionField;
    private ComboBox<String> categoryComboBox;
    private ComboBox<String> priorityComboBox;
    private DatePicker deadlinePicker;
    private ComboBox<Task.TaskStatus> statusComboBox;

    private final ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);

    public TaskDialog(TaskController taskController, CategoryController categoryController) {
        this.taskController = taskController;
        this.categoryController = categoryController;
        this.setTitle("Add/Edit Task");

        // Create form fields
        titleField = new TextField();
        descriptionField = new TextArea();
        categoryComboBox = new ComboBox<>();
        priorityComboBox = new ComboBox<>();
        deadlinePicker = new DatePicker();
        statusComboBox = new ComboBox<>();

        // Populate category and priority combo boxes as well as task status
        categoryController.getAllCategories().forEach(category -> categoryComboBox.getItems().add(category.getName()));
        taskController.getAllPriorities().forEach(priority -> priorityComboBox.getItems().add(priority.getName()));
        statusComboBox.getItems().addAll(Task.TaskStatus.values());

    
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
        grid.add(new Label("Status:"), 0, 5);
        grid.add(statusComboBox, 1, 5);

        this.getDialogPane().setContent(grid);

        // Enable/disable the save button depending on validation
        this.getDialogPane().lookupButton(saveButtonType).setDisable(true);

        // Add validation
        titleField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        deadlinePicker.valueProperty().addListener((observable, oldValue, newValue) -> validateForm());
        categoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> validateForm());
        priorityComboBox.valueProperty().addListener((observable, oldValue, newValue) -> validateForm());

        // Set the result converter
        this.setResultConverter(new Callback<ButtonType, Task>() {
            @Override
            public Task call(ButtonType buttonType) {
                if (buttonType == saveButtonType) {
                    return new Task(
                            titleField.getText(),
                            descriptionField.getText(),
                            categoryController.getCategoryByName(categoryComboBox.getValue()),
                            taskController.getPriorityByName(priorityComboBox.getValue()),
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
                           !deadlinePicker.getValue().isBefore(LocalDate.now()) &&
                           categoryComboBox.getValue() != null &&
                           priorityComboBox.getValue() != null;
        this.getDialogPane().lookupButton(saveButtonType).setDisable(!isValid);
    }

    public void setTask(Task task) {
        titleField.setText(task.getTitle());
        descriptionField.setText(task.getDescription());
        categoryComboBox.setValue(task.getCategoryName());
        priorityComboBox.setValue(task.getPriorityName());
        deadlinePicker.setValue(task.getDeadline());
        statusComboBox.setValue(task.getStatus());
    }
}