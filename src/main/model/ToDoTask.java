package model;

import java.util.Objects;

public class ToDoTask {
    private String name;
    private String description;
    private String type;
    private String dueTime;
    private boolean isDue;
    private boolean isDone;

    public ToDoTask(String n, String d, String t, String dueTime) {
        name = n;
        description = d;
        type = t;
        this.dueTime = dueTime;
        isDue = false;
        isDone = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public boolean isDue() {
        return isDue;
    }

    public void setDue(boolean due) {
        isDue = due;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ToDoTask toDoTask = (ToDoTask) o;
        return isDue == toDoTask.isDue
                && isDone == toDoTask.isDone
                && Objects.equals(name, toDoTask.name)
                && Objects.equals(description, toDoTask.description)
                && Objects.equals(type, toDoTask.type)
                && Objects.equals(dueTime, toDoTask.dueTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, type, dueTime, isDue, isDone);
    }
}
