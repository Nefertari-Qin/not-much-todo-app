package model;

import java.util.Calendar;

public interface ToDoTask {
    // getters
    String getTaskContent();

    Calendar getDueDate();

    boolean isCompleted();

    boolean isDue();

    void setTaskContent(String taskContent);

    void setDueDate(int year, int month, int day);

    void markCompleted();

    void markUncompleted();

    String getFormattedStringDueDate();
}
