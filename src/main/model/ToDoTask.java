package model;

import java.util.Calendar;

public class ToDoTask {
    protected String taskContent;
    protected Calendar dueDate;
    protected boolean isCompleted;
    protected boolean isDue;

    // Constructor:
    // EFFECTS: create a ToDoTask with given taskContent and is incompleted
    public ToDoTask(String taskContent) {
        this.taskContent = taskContent;
        this.isCompleted = false;
    }

    // EFFECTS: return the content of this
    public String getTaskContent() {
        return taskContent;
    }

    // EFFECTS: return the due date of the task
    public Calendar getDueDate() {
        return this.dueDate;
    }

    // EFFECTS: return true if this is completed; false OW.
    public boolean isCompleted() {
        return isCompleted;
    }

    // EFFECTS: return true if this is due; false OW.
    public boolean isDue() {
        Calendar currentDate = Calendar.getInstance();
        this.isDue = currentDate.before(dueDate);
        return this.isDue;
    }

    // MODIFIES: this
    // EFFECTS: set the content of this
    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    // MODIFIES: this
    // EFFECTS: set the due date of this
    public void setDueDate(int year, int month, int day) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(year, month, day);
        this.dueDate = currentDate;
    }

    // MODIFIES: this
    // EFFECTS: set this to be completed
    public void markCompleted() {
        this.isCompleted = true;
    }

    // MODIFIES: this
    // EFFECTS: set this to be uncompleted
    public void markUncompleted() {
        this.isCompleted = false;
    }

    // EFFECTS: return the due date in yyyy-mm-dd format
    public String getFormattedStringDueDate() {
        String yearString = makeFormattedNumber(dueDate.get(Calendar.YEAR));
        String monthString = makeFormattedNumber(dueDate.get(Calendar.MONTH));
        String dayString = makeFormattedNumber(dueDate.get(Calendar.DATE));
        return yearString + "-" + monthString + "-" + dayString;
    }

    // EFFECTS: return the month/date number in two digits format
    private String makeFormattedNumber(int i) {
        if (i < 10) {
            return "" + "0" + i;
        } else {
            return "" + i;
        }
    }
}
