package com.medialab.view.components;

import com.medialab.model.Priority;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PriorityView extends HBox {
    private Priority priority;

    public PriorityView(Priority priority) {
        this.priority = priority;
        render();
    }

    private void render() {
        // Display priority name
        Label nameLabel = new Label(priority.getName());

        // Add a label for default priority
        if (priority.isDefaultPriority()) {
            Label defaultLabel = new Label("(Default)");
            defaultLabel.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
            this.getChildren().addAll(nameLabel, defaultLabel);
        } else {
            this.getChildren().add(nameLabel);
        }

        // Add styling (optional)
        this.setSpacing(10);
        this.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10;");
    }

    public Priority getPriority() {
        return priority;
    }
}