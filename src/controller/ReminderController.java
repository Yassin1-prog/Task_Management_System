package controller;

import model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class ReminderController {
    private List<Reminder> reminders;

    public ReminderController() {
        reminders = new ArrayList<>();
    }

    public void createReminder(Task task, Reminder.ReminderType type, 
                                 LocalDate customDate) {
        reminders.add(new Reminder(task, type, customDate));
    }

    public void updateReminder(Reminder reminder, Reminder.ReminderType type, 
                             LocalDate customDate) {
        reminder.updateReminder(type, customDate);
    }

    public void deleteReminder(Reminder reminder) {
        reminders.remove(reminder);
    }

    public void deleteRemindersForTask(Task task) {
        reminders.removeIf(reminder -> reminder.getTask().equals(task));
    }

    public List<Reminder> getActiveReminders() {
        return reminders;
    }
}
