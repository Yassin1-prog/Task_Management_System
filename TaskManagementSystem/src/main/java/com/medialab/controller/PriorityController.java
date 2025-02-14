package com.medialab.controller;

import com.medialab.model.*;
import java.util.List;
import java.util.ArrayList;

public class PriorityController {
    private List<Priority> priorities;

    public PriorityController() {
        priorities = new ArrayList<>();
        //Initialize with default priority
        priorities.add(new Priority("Default", true));
    }

    public void initializePriorities(List<Priority> loadedPriorities) {
        priorities.clear();
        priorities.addAll(loadedPriorities);
    }
    
    public void createDefaultPriority() {
        priorities.add(new Priority("Default", true));
    }

    public void createPriority(String name) {
        priorities.add(new Priority(name, false));
    }

    public void updatePriority(Priority priority, String name) {
        priority.setName(name);
    }

    public void deletePriority(Priority priority) {
        priorities.remove(priority);
        // tasks with this priotiry should get assigned the default priority
        priority.getTasks().forEach(task -> {
            if(task.getPriority().equals(priority)) {
                task.setPriority(priorities.get(0));
            }
        });
    }

    public Priority getDefaultPriority() {
        return priorities.get(0);
    }

    public List<Priority> getAllPriorities() {
        return new ArrayList<>(priorities);
    }
}