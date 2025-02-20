package com.medialab.view.dialogs;

import com.medialab.model.Priority;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class PriorityDialog extends Dialog<Priority> {
    private TextField nameField;
    private Priority priority;
    private final ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);

    public PriorityDialog() {
        this.setTitle("Add/Edit Priority");

        // Create form fields
        nameField = new TextField();

        // Set up the dialog buttons
        this.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the form layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);

        this.getDialogPane().setContent(grid);

        // Enable/disable the save button depending on validation
        this.getDialogPane().lookupButton(saveButtonType).setDisable(true);

        // Add validation
        nameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());

        // Set the result converter
        this.setResultConverter(new Callback<ButtonType, Priority>() {
            @Override
            public Priority call(ButtonType buttonType) {
                if (buttonType == saveButtonType) {
                    return new Priority(nameField.getText(), false); // Assume non-default priority
                }
                return null;
            }
        });
    }

    private void validateForm() {
        boolean isValid = !nameField.getText().isEmpty();
        this.getDialogPane().lookupButton(saveButtonType).setDisable(!isValid);
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
        if (priority.isDefaultPriority()) {
            nameField.setText(priority.getName());
            nameField.setDisable(true); // Disable editing for default priority
        } else {
            nameField.setText(priority.getName());
        }
    }
}