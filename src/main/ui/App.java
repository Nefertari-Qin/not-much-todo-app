package ui;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import exceptions.ListDoesntExistException;
import exceptions.TaskDoesntExistException;
import model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App implements Loadable, Saveable {
    private static final String TODOLISTS_JSON = "./data/todolists.json";

    private static final String CREATE_LIST_COMMAND = "new list";
    private static final String ALL_LIST_COMMAND = "all lists";
    private static final String ENTER_LIST_COMMAND = "enter list";
    // private static final String DELETE_LIST_COMMAND = "delete list";
    private static final String CREATE_TASK_COMMAND = "new task";
    private static final String NORMAL_TASK_COMMAND = "normal";
    private static final String IMPORTANT_TASK_COMMAND = "important";
    private static final String ALL_TASK_COMMAND = "all tasks";
    private static final String CHECK_DONE_TASK_COMMAND = "done";
    private static final String CLEAN_DONE_TASK_COMMAND = "clean";
    private static final String CLOSE_APP_COMMAND = "close";


    private boolean runApp;
    private Scanner input;
    private List<ToDoList> toDoLists;
    ToDoList listCurrentIn;

    // Constructor:
    // EFFECTS: create a new App ready to run
    public App() {
        runApp = true;
        input = new Scanner(System.in);
        toDoLists = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add the given toDoList to app
    public void addList(ToDoList toDoList) {
        toDoLists.add(toDoList);
    }

    // EFFECTS: return the list of all ToDoLists created in app
    public List<ToDoList> getToDoLists() {
        return toDoLists;
    }

    // MODIFIES: this
    // EFFECTS: remove given toDoList from app if it is in the app; OW do nothing
    public void removeList(ToDoList toDoList) {
        toDoLists.remove(toDoList);
    }

    // MODIFIES: this
    // EFFECTS: load the existing app info and run the app
    public void run() {
        // TODO load(TODOLISTS_JSON);
        System.out.println("\nSome things you can do:");
        printIntroInstructions();
        String inputString;
        while (runApp) {
            inputString = getFormattedInputString();
            if (inputString.length() > 0) {
                parseInput(inputString);
            }
        }
    }

    private void printIntroInstructions() {
        System.out.println("Enter '" + CREATE_LIST_COMMAND + "' to create a new ToDo list");
        System.out.println("Enter '" + ALL_LIST_COMMAND + "' to see all ToDo lists");
        System.out.println("Enter '" + ENTER_LIST_COMMAND + "' to enter any one of existed ToDo list");
        System.out.println("Enter '" + CLOSE_APP_COMMAND + "' to close WhateverName App at any time");
    }

    // MODIFIES: this
    // EFFECTS: interpret user input
    private void parseInput(String str) {
        switch (str) {
            case CREATE_LIST_COMMAND:
                handleCreateNewList();
                break;
            case ALL_LIST_COMMAND:
                handlePrintAllListsName();
                break;
            case ENTER_LIST_COMMAND:
                handleEnterList();
                break;
            case CLOSE_APP_COMMAND:
                endApp();
                break;
            default:
                System.out.println("Sorry, I didn't understand that. Please try again.");
                printIntroInstructions();
                break;
        }
    }

    private void handleCreateNewList() {
        System.out.println("Enter the name of the ToDo list you want to create:");
        String listName = getInputString();
        listCurrentIn = new ToDoList(listName);
        toDoLists.add(listCurrentIn);
        System.out.println("ToDo list: '" + listName + "' was created successfully.");
        System.out.println("You are now in " + listName + " .\n");
        printInListExtraOptions();
        getInListExtraInput();
    }

    private void handlePrintAllListsName() {
        if (toDoLists.size() == 0) {
            System.out.println("No ToDo list has been created in NotJustToDo App yet. "
                    + "\nLet's create a new ToDo list!\n");
            printIntroInstructions();
        } else {
            for (ToDoList toDoList : toDoLists) {
                System.out.println(toDoList.getToDoListName());
            }
            printIntroInstructions();
        }
    }

    // MODIFIES: this
    // EFFECTS: enter the list with the given list name
    private void handleEnterList() {
        System.out.println("Enter the name of the ToDo list you want to enter:");
        String listName = getInputString();
        try {
            listCurrentIn = getList(listName);
            System.out.println("You are now in " + listName + ".\n");
            printInListExtraOptions();
            getInListExtraInput();
        } catch (ListDoesntExistException e) {
            System.out.println("You haven't created '" + listName + "' yet.");
            printIntroInstructions();
        }
    }

    // EFFECTS: - if there is a ToDoList of the given name, return the ToDoList
    //          - OW throw ListDoesntExistException
    private ToDoList getList(String listName) throws ListDoesntExistException {
        for (ToDoList list : toDoLists) {
            if (list.getToDoListName().equals(listName)) {
                return list;
            }
        }
        throw new ListDoesntExistException();
    }

    // EFFECTS: print extra options while inside a particular list
    private void printInListExtraOptions() {
        System.out.println("Enter '" + CREATE_TASK_COMMAND + "' to create a new ToDo task");
        System.out.println("Enter '" + ALL_TASK_COMMAND + "' to see all ToDo tasks");
        System.out.println("Enter '" + CHECK_DONE_TASK_COMMAND + "' to mark a ToDo task as done");
        System.out.println("Enter '" + CLEAN_DONE_TASK_COMMAND + "' to clean all completed ToDo tasks");
    }

    // EFFECTS: interpret extra user input while in a particular list
    private void parseInListExtraInput(String str) {
        switch (str) {
            case CREATE_TASK_COMMAND:
                handleCreateNewTask();
                break;
            case ALL_TASK_COMMAND:
                handlePrintAllTasksContent();
                break;
            case CHECK_DONE_TASK_COMMAND:
                handleCheckDoneTask();
                break;
            case CLEAN_DONE_TASK_COMMAND:
                handleCleanDoneTask();
                break;
            default:
                System.out.println("Sorry, I didn't understand that. Please try again.");
                printIntroInstructions();
                break;
        }
    }

    // EFFECTS: print out all existing tasks content
    private void handlePrintAllTasksContent() {
        if (listCurrentIn.countTasks() == 0) {
            System.out.println("No task has been created in "
                    + listCurrentIn.getToDoListName()
                    + " yet. \nLet's create a new ToDoTask!\n");
        } else {
            System.out.println("All tasks to do:");
            for (ToDoTask task : listCurrentIn.getToDoTasks()) {
                if (task != null) {
                    System.out.println(task.getTaskContent()
                            + " will be due on " + task.getFormattedStringDueDate()
                            + ". \nCompletion: " + task.isCompleted());
                }
            }
        }
        printInListExtraOptions();
        getInListExtraInput();
    }

    // EFFECTS: cross the ToDoTask from tasks if ToDoTask is a made
    private void handleCheckDoneTask() {
        System.out.println("\nEnter the task you want to cross:");
        String strTask = getInputString();
        ToDoTask taskToCheckDone;
        try {
            taskToCheckDone = listCurrentIn.getTask(strTask);
            if (taskToCheckDone.isCompleted()) {
                System.out.println(strTask + " was already completed, try to finish and cross off other task.");
            } else {
                taskToCheckDone.markCompleted();
                System.out.println("\nYou checked '" + strTask + "' as done from the ToDo list.\n");
            }
        } catch (TaskDoesntExistException e) {
            System.out.println(strTask + " is no in the ToDo list.\n");
        } finally {
            printInListExtraOptions();
            getInListExtraInput();
        }
    }

    // EFFECTS: clean up all tasks which is already been set as done.
    private void handleCleanDoneTask() {
        int totalCompleted = 0;
        List<ToDoTask> incompleteTasks = new ArrayList<>();
        for (ToDoTask taskToClean : listCurrentIn.getToDoTasks()) {
            if (!taskToClean.isCompleted()) {
                incompleteTasks.add(taskToClean);
            } else {
                totalCompleted += 1;
            }
        }
        listCurrentIn.setToDoTasks(incompleteTasks);
        System.out.println("You cleaned off " + totalCompleted + " number of tasks.");
        printInListExtraOptions();
        getInListExtraInput();
    }

    // MODIFIES: this
    // EFFECTS: create a new ToDoTask and add it to tasks
    private void handleCreateNewTask() {
        printTaskExtraOptions();
        String str = getInputString();
        System.out.println("\nEnter the " + str + " task you want to create:");
        createNewTask(str);
    }

    // MODIFIES: this
    // EFFECTS: create a new task of given str type and
    //          add it to the ToDoList currently in
    private void createNewTask(String str) {
        if (str.equals(NORMAL_TASK_COMMAND)) {
            String norTaskContent = getInputString();
            ToDoTask norTask = setNewTaskDueDate(new NormalTask(norTaskContent));
            listCurrentIn.addTask(norTask);
        } else if (str.equals(IMPORTANT_TASK_COMMAND)) {
            String impTaskContent = getInputString();
            ToDoTask impTask = setNewTaskDueDate(new ImportantTask(impTaskContent));
            listCurrentIn.addTask(impTask);
        } else {
            parseInput(str);
        }
        System.out.println("A new task is successfully added to '" + listCurrentIn.getToDoListName() + "' .\n");
        printInListExtraOptions();
        getInListExtraInput();
    }

    // MODIFIES: task
    // EFFECTS: set the due date of given task
    private ToDoTask setNewTaskDueDate(ToDoTask task) {
        String regString = "-";
        System.out.println("\nEnter the due date of the task with valid date format: YYYY-MM-DD:");
        String dueDate = getInputString();
        String[] parts = dueDate.split(regString);
        int intYear = Integer.parseInt(parts[0]);
        int intMonth = Integer.parseInt(parts[1]);
        int intDay = Integer.parseInt(parts[2]);
        task.setDueDate(intYear, intMonth, intDay);
        return task;
    }

    // EFFECTS: print extra options to create new task
    private void printTaskExtraOptions() {
        System.out.println("Enter '" + NORMAL_TASK_COMMAND + "' to create a new Normal Task.");
        System.out.println("Enter '" + IMPORTANT_TASK_COMMAND + "' to create a new Important Task.");
    }

    // MODIFIES: this
    // EFFECTS: close the app and save the current state of app
    private void endApp() {
        runApp = false;
        input.close();
        System.out.println("Closing ...");
        save(TODOLISTS_JSON);
    }

    // MODIFIES: this
    // EFFECTS: save all created ToDoLists info in the app
    public void save(String file) {
        toDoLists.add(0, listCurrentIn);
        Gson gson = new Gson();
        String listsJson = gson.toJson(toDoLists);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(listsJson);
            writer.close();
        } catch (IOException e) {
            System.out.println("Something wrong with saving data to file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: load existing ToDoLists info back to app
    public void load(String file) {
        Gson gson = new Gson();
        JsonReader reader = null;

        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file name, we cannot find the file!");
        }

        ToDoList[] lists = gson.fromJson(reader, ToDoList[].class);

        boolean isFirst = true;
        for (ToDoList list : lists) {
            if (isFirst) {
                listCurrentIn = list;
                isFirst = false;
            } else {
                toDoLists.add(list);
            }
        }
    }

    // EFFECTS: get user input while inside a particular ToDoList
    private void getInListExtraInput() {
        String inputString = getInputString();
        if (inputString.length() > 0) {
            parseInListExtraInput(inputString);
        }
    }

    // EFFECTS: get user input
    private String getInputString() {
        String str = "";
        if (input.hasNext()) {
            str = input.nextLine();
        }
        return str;
    }

    // EFFECTS: format the user input
    private String getFormattedInputString() {
        String str = "";
        if (input.hasNext()) {
            str = input.nextLine();
            str = makeFormattedString(str);
        }
        return str;
    }

    //MODIFIES: this
    //EFFECTS: format s by removing white space and quotation marks around s
    private String makeFormattedString(String s) {
        s = s.toLowerCase();
        s = s.trim();
        s = s.replaceAll("\"|\'", "");
        return s;
    }
}
