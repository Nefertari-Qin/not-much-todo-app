package model;

import model.exceptions.AlreadyExistException;
import model.exceptions.DoesntExistException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToDoList {
    private String name;
    private List<RegularTask> regularTasks;
    private List<UrgentTask> urgentTasks;

    public ToDoList(String name) {
        this.name = name;
        regularTasks = new ArrayList<>();
        urgentTasks = new ArrayList<>();
    }

    // EFFECTS: return list of regularTasks
    public List<RegularTask> getRegularTasks() {
        return regularTasks;
    }

    // EFFECTS: return the number of regularTasks
    public int countRegularTasks() {
        return regularTasks.size();
    }

    // EFFECTS: return list of urgentTasks
    public List<UrgentTask> getUrgentTasks() {
        return urgentTasks;
    }

    // EFFECTS: return the number of urgentTasks
    public int countUrgentTasks() {
        return urgentTasks.size();
    }

    // EFFECTS: return the total number of tasks
    public int countTotalTasks() {
        return (regularTasks.size() + urgentTasks.size());
    }

    // EFFECTS: return the name of the ToDoList
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: set the name of the ToDoList
    public void setName(String newName) {
        name = newName;
    }

    // Add a UrgentTask to ToDoList
    // MODIFIES: this, urgentTask
    // EFFECTS: - if a UrgentTask has already been created, throw AlreadyExistException
    //          - ow add urgentTask into this ToDoList and let it know this ToDoList it's in
    public void addUrgentTask(UrgentTask urgentTask) throws AlreadyExistException {
        if (urgentTasks.contains(urgentTask)) {
            throw new AlreadyExistException(urgentTask.getContent());
        }
        urgentTasks.add(urgentTask);
        urgentTask.setTheToDoList(this);
    }

    // Remove a UrgentTask from ToDoList
    // MODIFIES: this, urgentTask
    // EFFECTS: - if a UrgentTask with name hasn't already been created yet, throw DoesntExistException
    //          - ow remove the urgentTask with name from ToDoList tell it know it's out of this ToDoList
    public void removeUrgentTask(UrgentTask urgentTask) throws DoesntExistException {
        if (!urgentTasks.contains(urgentTask)) {
            throw new DoesntExistException(urgentTask.getContent());
        }
        urgentTasks.remove(urgentTask);
        urgentTask.setTheToDoList(null);

    }

    public void addRegularTask(RegularTask regularTask) {
        if (!regularTasks.contains(regularTask)) {
            regularTasks.add(regularTask);
        }
    }

    public void removeRegularTask(RegularTask regularTask) {
        regularTasks.remove(regularTask);
    }

    // IntelliJ self-generated equals and hasCode:
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ToDoList toDoList = (ToDoList) o;
        return name.equals(toDoList.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
