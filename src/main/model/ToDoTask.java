package model;

import java.util.Calendar;

public class ToDoTask {
    protected String taskContent;
    protected Calendar dueDate;
    protected boolean isCompleted;
    protected boolean isDue;

    public ToDoTask(String taskContent) {
        this.taskContent = taskContent;
        this.isCompleted = false;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public Calendar getDueDate() {
        return this.dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isDue() {
        Calendar currentDate = Calendar.getInstance();
        this.isDue = currentDate.before(dueDate);
        return this.isDue;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public void setDueDate(int year, int month, int day) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(year, month, day);
        this.dueDate = currentDate;
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    public void markUncompleted() {
        this.isCompleted = false;
    }

    public String getFormattedStringDueDate() {
        String yearString = makeFormattedNumber(dueDate.get(Calendar.YEAR));
        String monthString = makeFormattedNumber(dueDate.get(Calendar.MONTH));
        String dayString = makeFormattedNumber(dueDate.get(Calendar.DATE));
        return yearString + "-" + monthString + "-" + dayString;
    }

    private String makeFormattedNumber(int i) {
        if (i < 10) {
            return "" + "0" + i;
        } else {
            return "" + i;
        }
    }
}
