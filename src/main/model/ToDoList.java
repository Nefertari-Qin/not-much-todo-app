package model;

import model.exceptions.AlreadyExistException;
import model.exceptions.DoesntExistException;

import java.util.*;

public class ToDoList extends Observable implements Iterable<ToDoTask> {
    private String name;
    private List<ToDoTask> tasks;

    public ToDoList(String name) {
        this.name = name;
        tasks = new ArrayList<>();
        //tasks.add(new ToDoTask("1", "2", "3", "4"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addToDoTask(ToDoTask task) {
        setChanged();
        notifyObservers();
        tasks.add(task);
        //System.out.println("debug");
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
        return Objects.equals(name, list.name) && Objects.equals(tasks, list.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tasks);
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
