package com.medialab.model;

public class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    // Getters and setters
    public String getName() {
        return this.name;
    }

    public void setName(String newname) {
        this.name = newname;
    }
}