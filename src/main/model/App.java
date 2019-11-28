package model;

import model.exceptions.*;
import network.MessagePrinter;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

// Represent an App* for this project
public class App extends Observable {
    private Map<String, ToDoList> nameListMap;
    private ToDoList listCurrentIn;

    // Constructor
    // EFFECTS: create a new ToDoApp with no ToDoList exist
    // and not currently in ant ToDoList
    public App() {
        nameListMap = new HashMap<>();
        MessagePrinter mp = new MessagePrinter();
        mp.printWebInfo();

        addObserver(mp);
    }

    // Add a ToDoList to App
    // MODIFIES: this
    // EFFECTS: - if a ToDoList has already been created, throw AlreadyExistException
    //          - ow add toDoList into app
    public void addToDoList(ToDoList toDoList) throws AlreadyExistException {
        String name = toDoList.getName();
        if (nameListMap.containsKey(name)) {
            throw new AlreadyExistException(name);
        }
        nameListMap.put(name, toDoList);

        setChanged();
        notifyObservers(name);
    }

    // Remove a ToDoList from App
    // MODIFIES: this
    // EFFECTS: - if a ToDoList with name hasn't already been created yet, throw DoesntExistException
    //          - ow remove the ToDoList with name from App
    public void removeToDoList(String name) throws DoesntExistException {
        if (!nameListMap.containsKey(name)) {
            throw new DoesntExistException(name);
        }
        nameListMap.remove(name);
    }

    // Get the ToDoList with given name
    // EFFECTS: - if a ToDoList with name hasn't already been created yet, throw DoesntExistException
    //          - ow return the ToDoList with name
    public ToDoList getToDoList(String name) throws DoesntExistException {
        if (!nameListMap.containsKey(name)) {
            throw new DoesntExistException(name);
        }
        return nameListMap.get(name);
    }

    // Count the number of ToDoList already created in App
    // EFFECTS: return the number of ToDoLists in App
    public int countList() {
        return nameListMap.size();
    }

    // Check whether a ToDoList has already been created
    // EFFECTS: return true if toDoList is in App already; false ow
    public boolean containList(ToDoList toDoList) {
        return nameListMap.containsValue(toDoList);
    }

    // Enter the ToDoList with name from App*
    // MODIFIES: this
    // EFFECTS:  - if listCurrentIn != null, Do nothing
    //           - if a ToDoList with name hasn't already been created yet, throw DoesntExistException
    //           - ow set it to be the listCurrentIn
    public void enterToDoList(String name) throws DoesntExistException {
        if (!nameListMap.containsKey(name)) {
            throw new DoesntExistException(name);
        }
        listCurrentIn = nameListMap.get(name);
    }

    // Exit the listCurrentIn
    // MODIFIES: this
    // EFFECTS: leave the listCurrentIn and set it to null
    public void exitToDoList() {
        listCurrentIn = null;
    }

    // Get the ToDoList currently in
    // EFFECTS: return listCurrentIn (null if outside of all list)
    public ToDoList getListCurrentIn() {
        return listCurrentIn;
    }

    // Change the name of the listCurrentIn
    // MODIFIES: this
    // EFFECTS: - if listCurrentIn == null, throw outsideListException
    //          - if listCurrentIn.getName() == newName throw SameNameException
    //          - ow change the name of listCurrentIn to newName
    public void editCurrentToDoListName(String newName) throws OutsideListException, SameNameException {
        if (listCurrentIn == null) {
            throw new OutsideListException();
        }
        if (listCurrentIn.getName() == newName) {
            throw new SameNameException();
        }
        nameListMap.remove(listCurrentIn.getName());
        listCurrentIn.setName(newName);
        nameListMap.put(newName, listCurrentIn);
    }
}
