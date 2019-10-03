package ui;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
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

    public void addList(ToDoList toDoList) {
        toDoLists.add(toDoList);
    }

    public List<ToDoList> getToDoLists() {
        return toDoLists;
    }

    public void run() {
        load(TODOLISTS_JSON);
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
        System.out.println("Enter '" + CLOSE_APP_COMMAND + "' to close NotMuchToDo App at any time");
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

    private void handleEnterList() {
        System.out.println("Enter the name of the ToDo list you want to enter:");
        String listName = getInputString();
        listCurrentIn = getList(listName);
        if (listCurrentIn == null) {
            System.out.println("You haven't created '" + listName + "' yet.");
            printIntroInstructions();
        } else {
            System.out.println("You are now in " + listName + ".\n");
            printInListExtraOptions();
            getInListExtraInput();
        }
    }

    private ToDoList getList(String listName) {
        for (ToDoList list : toDoLists) {
            if (list.getToDoListName().equals(listName)) {
                return list;
            }
        }
        return null;
    }

    private void printInListExtraOptions() {
        System.out.println("Enter '" + CREATE_TASK_COMMAND + "' to create a new ToDo task");
        System.out.println("Enter '" + ALL_TASK_COMMAND + "' to see all ToDo tasks");
        System.out.println("Enter '" + CHECK_DONE_TASK_COMMAND + "' to mark a ToDo task as done");
        System.out.println("Enter '" + CLEAN_DONE_TASK_COMMAND + "' to clean all completed ToDo tasks");
    }

    private void parseInListExtraInput(String str) {
        switch (str) {
            case CREATE_TASK_COMMAND:
                handleCreateNewTask();
                break;
            case ALL_TASK_COMMAND:
                handlePrintAllTasksName();
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

    private void handlePrintAllTasksName() {
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
        ToDoTask taskToCheckDone = listCurrentIn.getTask(strTask);
        if (taskToCheckDone == null) {
            System.out.println(strTask + " is no in the ToDo list.\n");
        } else if (taskToCheckDone.isCompleted()) {
            System.out.println(strTask + " was already completed, try to finish and cross off other task.");
        } else {
            taskToCheckDone.markCompleted();
            System.out.println("\nYou checked '" + strTask + "' as done from the ToDo list.\n");
        }
        printInListExtraOptions();
        getInListExtraInput();
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
        createNewTask(str);
    }

    private void createNewTask(String str) {
        if (str.equals("normal")) {
            System.out.println("\nEnter the normal task you want to create:");
            String norTaskContent = getInputString();
            ToDoTask norTask = setNewTaskDueDate(new NormalTask(norTaskContent));
            listCurrentIn.addTask(norTask);
        } else if (str.equals("important")) {
            System.out.println("\nEnter the important task you want to create:");
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

    private void printTaskExtraOptions() {
        System.out.println("Enter '" + NORMAL_TASK_COMMAND + "' to create a new Normal Task.");
        System.out.println("Enter '" + IMPORTANT_TASK_COMMAND + "' to create a new Important Task.");
    }

    private void endApp() {
        runApp = false;
        input.close();
        System.out.println("Closing ...");
        toDoLists.add(0, listCurrentIn);
        save(TODOLISTS_JSON);
    }

    public void save(String file) {
        toDoLists.add(0, listCurrentIn);
        Gson gson = new Gson();
        String listsJson = gson.toJson(toDoLists);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            System.out.println("Invalid file name, we cannot find the file!");
        }
        try {
            writer.write(listsJson);
            writer.close();
        } catch (IOException e) {
            System.out.println("Something wrong ... ");
        }
    }

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
