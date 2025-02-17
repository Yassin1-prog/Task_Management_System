package com.medialab.view;

import com.medialab.controller.TaskController;
import com.medialab.model.Priority;
import com.medialab.view.components.PriorityView;
import com.medialab.view.dialogs.PriorityDialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class PriorityListView extends BorderPane {
    private TaskController taskController;
    private ListView<PriorityView> priorityListView;

    public PriorityListView(TaskController taskController) {
        this.taskController = taskController;
        priorityListView = new ListView<>();

        // Load priorities into the list
        loadPriorities();

        // Add buttons for adding/editing/deleting priorities
        Button addButton = new Button("Add Priority");
        addButton.setOnAction(e -> addPriority());

        Button editButton = new Button("Edit Priority");
        editButton.setOnAction(e -> editPriority());

        Button deleteButton = new Button("Delete Priority");
        deleteButton.setOnAction(e -> deletePriority());

        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        this.setTop(buttonBox);
        this.setCenter(priorityListView);
    }

    private void loadPriorities() {
        priorityListView.getItems().clear();
        for (Priority priority : taskController.getAllPriorities()) {
            priorityListView.getItems().add(new PriorityView(priority));
        }
    }

    private void addPriority() {
        // Open PriorityDialog for adding a new priority
        PriorityDialog dialog = new PriorityDialog();
        dialog.showAndWait().ifPresent(priority -> {
            if(taskController.getPriorityByName(priority.getName()) == null) {
                taskController.createPriority(priority.getName());
                loadPriorities(); // Refresh the priority list
            } else {
                showAlert("A priority with the same name already exists!");
            }
        });
    }

    private void editPriority() {
        // Open PriorityDialog for editing the selected priority
        PriorityView selectedPriorityView = priorityListView.getSelectionModel().getSelectedItem();
        if (selectedPriorityView != null && !selectedPriorityView.getPriority().isDefaultPriority()) { //default priority cannot be edited
            PriorityDialog dialog = new PriorityDialog();
            dialog.setPriority(selectedPriorityView.getPriority());
            dialog.showAndWait().ifPresent(priority -> {
                if(taskController.getPriorityByName(priority.getName()) == null || priority.getName().equals(selectedPriorityView.getPriority().getName())) {
                    taskController.updatePriority(selectedPriorityView.getPriority(), priority.getName());
                    loadPriorities(); // Refresh the priority list
                } else {
                    showAlert("A priority with the same name already exists!");
                }
            });
        }
    }

    private void deletePriority() {
        // Delete the selected priority (if not default)
        PriorityView selectedPriorityView = priorityListView.getSelectionModel().getSelectedItem();
        if (selectedPriorityView != null && !selectedPriorityView.getPriority().isDefaultPriority()) { // default priority cannot be deleted
            taskController.deletePriority(selectedPriorityView.getPriority());
            loadPriorities(); // Refresh the priority list
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Task Name");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}