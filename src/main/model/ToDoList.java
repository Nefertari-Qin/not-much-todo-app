package model;

import java.util.*;

// Represents a ToDoList that contain arbitrary number of ToDoTasks
public class ToDoList extends Observable implements Iterable<ToDoTask> {
    private String name;
    private List<ToDoTask> tasks;

    // Constructor:
    public ToDoList(String name) {
        this.name = name;
        tasks = new ArrayList<>();
    }

    // Getter
    public String getName() {
        return name;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: return the number of ToDoTasks in this ToDoList
    public int size() {
        return tasks.size();
    }

    // EFFECTS: return true if this ToDoList contains given ToDoTask
    public boolean contains(ToDoTask task) {
        return tasks.contains(task);
    }

    // MODIFIES: this
    // EFFECTS: add a given ToDoTask to this ToDoList
    public void addToDoTask(ToDoTask task) {
        tasks.add(task);
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ToDoList list = (ToDoList) o;
        return Objects.equals(name, list.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<ToDoTask> iterator() {
        return tasks.iterator();
    }
}
