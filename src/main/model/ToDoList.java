package model;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    private String toDoListName;
    private List<ToDoTask> toDoTasks;

    // Constructor
    // EFFECTS: construct a ToDoList with given name
    public ToDoList(String name) {
        toDoTasks = new ArrayList<>();
        this.toDoListName = name;
    }

    // EFFECTS: get the name of this
    public String getToDoListName() {
        return toDoListName;
    }

    // EFFECTS: get list of ToDoTasks in this
    public List<ToDoTask> getToDoTasks() {
        return toDoTasks;
    }

    public void setToDoTasks(List<ToDoTask> toDoTasks) {
        this.toDoTasks = toDoTasks;
    }

    // MODIFIES: this
    // EFFECTS: set the name of this
    public void setToDoListName(String toDoListName) {
        this.toDoListName = toDoListName;
    }

    // REQUIRES: task is incomplete and not due
    // MODIFIES: this
    // EFFECTS: add task to this ToDoList
    public void addTask(ToDoTask task) {
        toDoTasks.add(task);
    }

    // MODIFIES: this
    // EFFECTS: remove task from ToDoList if task is contained in the list
    // Otherwise, do nothing.
    public void removeTask(ToDoTask task) {
        toDoTasks.remove(task);
    }

    // EFFECTS: return the number of tasks in the ToDoList
    public int countTasks() {
        return toDoTasks.size();
    }

    // EFFECTS: return true if task is in the ToDoList
    public boolean containTask(ToDoTask task) {
        return toDoTasks.contains(task);
    }

    // EFFECTS: return the ToDoTask with the given name
    public ToDoTask getTask(String task) {
        for (ToDoTask taskWanted : toDoTasks) {
            if (taskWanted.getTaskContent().equals(task)) {
                return taskWanted;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: deleted all completed tasks in the ToDoList
    // and return the number of tasks being deleted
    public int deleteCompleted() {
        int completedTasksDeleted = 0;
        List<ToDoTask> cleanedResultTasks = new ArrayList<>();
        for (ToDoTask task : toDoTasks) {
            if (!task.isCompleted()) {
                cleanedResultTasks.add(task);
            } else {
                completedTasksDeleted += 1;
            }
        }
        this.toDoTasks = cleanedResultTasks;
        return completedTasksDeleted;
    }
}
