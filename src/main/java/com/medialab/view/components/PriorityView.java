package com.medialab.view.components;

import com.medialab.model.Priority;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PriorityView extends HBox {
    private Priority priority;

    public PriorityView(Priority priority) {
        this.priority = priority;
        this.getStyleClass().add("priority-view");
        render();
    }

    private void render() {
        // Create icon based on priority
        Label iconLabel = new Label("📌");
        iconLabel.setStyle("-fx-font-size: 18px;");

        // Display priority name with styled label
        Label nameLabel = new Label(priority.getName());
        nameLabel.setStyle("-fx-font-weight: bold;");

        HBox contentBox = new HBox(10, iconLabel, nameLabel);
        contentBox.setAlignment(Pos.CENTER_LEFT);

        if (priority.isDefaultPriority()) {
            Label defaultLabel = new Label("(Default)");
            defaultLabel.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
            contentBox.getChildren().add(defaultLabel);
        }

        this.getChildren().add(contentBox);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    public Priority getPriority() {
        return priority;
    }
}