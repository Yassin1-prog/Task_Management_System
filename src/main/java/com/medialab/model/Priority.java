package com.medialab.model;

public class Priority {
    private String name;
    private boolean isDefault;

    public Priority(String name, boolean isDefault) {
        this.name = name;
        this.isDefault = isDefault;
    }

    // Getters and setters

    public void setName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return this.name;
    }

    public boolean isDefaultPriority() {
        return isDefault;
    }
}