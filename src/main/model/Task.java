package model;

public interface Task {
    // setters
    // MODIFIES: this
    // EFFECTS: set the task content of this
    void setTask(String task);

    // MODIFIES: this
    // EFFECTS: set the task creator of this
    void setCreator(String creator);

    // MODIFIES: this
    // EFFECTS: set the due date of this to dueDate
    boolean setDueDate(String dueDate);

    // MODIFIES: this
    // EFFECTS: set the completeness of this based on isCompleted
    void setIsCompleted(boolean isCompleted);

    // getters
    // EFFECTS: return the task content of this
    String getTask();

    // EFFECTS: return the task creator of this
    String getCreator();

    // EFFECTS: return the task due date of this
    String getDueDate();

    // EFFECTS: return true is this is completed; false OW.
    boolean isCompleted();

    // compare current date with the due date
    // MODIFIES: this
    // EFFECTS: return true if current date is >= due date; false OW.
    boolean isDue();
}
