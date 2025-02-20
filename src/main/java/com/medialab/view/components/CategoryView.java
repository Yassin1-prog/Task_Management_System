package com.medialab.view.components;

import com.medialab.model.Category;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CategoryView extends HBox {
    private Category category;

    public CategoryView(Category category) {
        this.category = category;
        this.getStyleClass().add("category-view");
        render();
    }

    private void render() {
        // Create icon and name labels
        Label iconLabel = new Label("📁");
        iconLabel.setStyle("-fx-font-size: 18px;");

        Label nameLabel = new Label(category.getName());
        nameLabel.setStyle("-fx-font-weight: bold;");

        // Create content box
        HBox contentBox = new HBox(10, iconLabel, nameLabel);
        contentBox.setAlignment(Pos.CENTER_LEFT);

        this.getChildren().add(contentBox);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    public Category getCategory() {
        return category;
    }
}