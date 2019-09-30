package model;

import java.util.Calendar;

public class NormalTask implements ToDoTask {
    private String taskContent;
    private Calendar dueDate;
    private boolean isCompleted;
    private boolean isDue;

    // Constructor
    // EFFECTS: create a ToDoTask with task content and is not completed
    public NormalTask(String taskContent) {
        this.taskContent = taskContent;
        this.isCompleted = false;
    }

    // getters
    @Override
    public String getTaskContent() {
        return taskContent;
    }

    @Override
    public Calendar getDueDate() {
        return this.dueDate;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public boolean isDue() {
        Calendar currentDate = Calendar.getInstance();
        this.isDue = currentDate.before(dueDate);
        return this.isDue;
    }

    @Override
    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    @Override
    public void setDueDate(int year, int month, int day) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(year, month, day);
        this.dueDate = currentDate;
    }

    @Override
    public void markCompleted() {
        this.isCompleted = true;
    }

    @Override
    public void markUncompleted() {
        this.isCompleted = false;
    }

    @Override
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
