package com.medialab.view.components;

import com.medialab.model.Category;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CategoryView extends HBox {
    private Category category;

    public CategoryView(Category category) {
        this.category = category;
        render();
    }

    private void render() {
        // Display category name
        Label nameLabel = new Label(category.getName());

        // Add styling (optional)
        this.setSpacing(10);
        this.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10;");

        // Add the label to the HBox
        this.getChildren().add(nameLabel);
    }

    public Category getCategory() {
        return category;
    }
}