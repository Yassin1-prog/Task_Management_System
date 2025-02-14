package com.medialab.model;

import java.time.LocalDate;

public class Reminder {
    private Task task;
    private ReminderType type;
    private LocalDate reminderDate;

    public enum ReminderType {
        ONE_DAY_BEFORE,
        ONE_WEEK_BEFORE,
        ONE_MONTH_BEFORE,
        CUSTOM_DATE
    }

    public Reminder(Task task, ReminderType type, LocalDate customDate) {
        this.task = task;
        this.type = type;
        if (type == ReminderType.CUSTOM_DATE) {
            this.reminderDate = customDate;
        } else {
            this.reminderDate = calculateReminderDate(type);
        }
    }

    public Task getTask() {
        return task;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }

    public void updateReminder(ReminderType type, LocalDate customDate) {
        this.type = type;
        if (type == ReminderType.CUSTOM_DATE) {
            this.reminderDate = customDate;
        } else {
            this.reminderDate = calculateReminderDate(type);
        }
    }

    public boolean isValid() {
        if(task.getDeadline().isBefore(reminderDate)) {
            return false;
        }
        return true;
    }

    private LocalDate calculateReminderDate(ReminderType type) {
        switch (type) {
            case ONE_DAY_BEFORE:
                return task.getDeadline().minusDays(1);
            case ONE_WEEK_BEFORE:
                return task.getDeadline().minusWeeks(1);
            case ONE_MONTH_BEFORE:
                return task.getDeadline().minusMonths(1);
            default:
                throw new IllegalArgumentException("Unsupported reminder type: " + type);
        }
    }

}