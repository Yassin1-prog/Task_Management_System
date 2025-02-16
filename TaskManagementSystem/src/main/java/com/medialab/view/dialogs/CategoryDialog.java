package com.medialab.view.dialogs;

import com.medialab.model.Category;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class CategoryDialog extends Dialog<Category> {
    private TextField nameField;

    public CategoryDialog() {
        this.setTitle("Add/Edit Category");

        // Create form fields
        nameField = new TextField();

        // Set up the dialog buttons
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
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
        this.setResultConverter(new Callback<ButtonType, Category>() {
            @Override
            public Category call(ButtonType buttonType) {
                if (buttonType == saveButtonType) {
                    return new Category(nameField.getText());
                }
                return null;
            }
        });
    }

    private void validateForm() {
        boolean isValid = !nameField.getText().isEmpty();
        this.getDialogPane().lookupButton(ButtonType.OK).setDisable(!isValid);
    }

    public void setCategory(Category category) {
        nameField.setText(category.getName());
    }
}