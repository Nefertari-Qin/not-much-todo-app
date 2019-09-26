package ui;

import model.NormalTask;
import model.ToDoTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Represent a ToDoApp class with user interaction
public class ToDoApp {
    private static final String CREATE_TASK_COMMAND = "new task";
    private static final String ALL_TASK_COMMAND = "all task";
    private static final String CHECK_DONE_TASK_COMMAND = "check done";
    private static final String CLEAN_DONE_TASK_COMMAND = "clean done";
    private static final String CLOSE_APP_COMMAND = "close app";

    private boolean runApp;
    private Scanner input;
    private List<ToDoTask> tasks;

    public ToDoApp() {
        runApp = true;
        input = new Scanner(System.in);
        tasks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add task to tasks list
    private void addTask(ToDoTask task) {
        tasks.add(task);
    }

    // EFFECTS: returns the ToDoTask if it is already made ToDoTask in App
    private ToDoTask getTask(String task) {
        for (ToDoTask regularTask : tasks) {
            if (regularTask.getTask().equals(task)) {
                return regularTask;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: deal with general user input
    public void handleUserInput() {
        printInstructions();
        String inputString;

        while (runApp) {
            inputString = getFormattedInputString();
            if (inputString.length() > 0) {
                parseSomeInput(inputString);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: interpret user input
    private void parseSomeInput(String string) {
        switch (string) {
            case CREATE_TASK_COMMAND:
                handleCreateNewTask();
                break;
            case ALL_TASK_COMMAND:
                handlePrintAllTasks();
                break;
            default:
                parseMoreInput(string);
                break;
        }
    }

    private void parseMoreInput(String string) {
        switch (string) {
            case CHECK_DONE_TASK_COMMAND:
                handleCheckDoneTask();
                break;
            case CLEAN_DONE_TASK_COMMAND:
                handleCleanDoneTask();
                break;
            case CLOSE_APP_COMMAND:
                endApp();
                break;
            default:
                System.out.println("Sorry, I didn't understand that. Please try again.");
                printInstructions();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: create a new ToDoTask and add it to tasks
    private void handleCreateNewTask() {
        System.out.println("\nEnter the task you want to do:");
        String task = getInputString();
        System.out.println("A new ToDo task is successfully added to NotMuchToDo App.");
        System.out.println("\nEnter the name of the task creator:");
        String creator = getInputString();
        System.out.println("\nEnter the due date of the task with valid date format: YYYY-MM-DD:");
        String dueDate = getInputString();
        NormalTask normalTask = new NormalTask(task, creator, dueDate);
        System.out.println("ToDo task: '" + task + "' was made by " + creator + ", and will due on " + dueDate);
        addTask(normalTask);
        printInstructions();
    }

    // EFFECTS: suggest create new ToDoTask if no task is found,
    // otherwise print all ToDoTasks being made
    private void handlePrintAllTasks() {
        if (tasks.size() == 0) {
            System.out.println("No task need to do yet. Let's create a new ToDoTask!");
            printInstructions();
        } else {
            System.out.println("All tasks to do:");
            for (ToDoTask task : tasks) {
                if (task != null) {
                    System.out.println(task.getTask()
                            + " by " + task.getCreator()
                            + ", due on " + task.getDueDate()
                            + ". \nCompletion: " + task.isCompleted());
                }
            }
        }
    }

    // EFFECTS: cross the ToDoTask from tasks if ToDoTask is a made
    private void handleCheckDoneTask() {
        System.out.println("\nEnter the task you want to cross:");
        String strTask = getInputString();
        ToDoTask taskChecked = getTask(strTask);
        if (taskChecked == null) {
            System.out.println(strTask + " is no in the ToDo list.");
        } else if (taskChecked.isCompleted()) {
            System.out.println(strTask + " was already completed, try to finish and cross off other task.");
        } else {
            taskChecked.markCompleted();
            System.out.println("\nYou checked '" + strTask + "' as done from the ToDo list");
        }
        printInstructions();
    }

    // EFFECTS: clean up all tasks which is already been set as done.
    private void handleCleanDoneTask() {
        int totalCompleted = 0;
        List<ToDoTask> cleanedTasks = new ArrayList<>();
        for (ToDoTask task : tasks) {
            if (!task.isCompleted()) {
                cleanedTasks.add(task);
            } else {
                totalCompleted += 1;
            }
        }
        this.tasks = cleanedTasks;
        System.out.println("You cleaned off " + totalCompleted + " number of tasks.");
    }

    // MODIFIES: this
    // EFFECTS: end the application
    private void endApp() {
        runApp = false;
        input.close();
        System.out.println("Closing ...");
    }

    // EFFECTS: print initial user instruction
    private void printInstructions() {
        System.out.println("\nSome things you can do:\n");
        System.out.println("Enter '" + CREATE_TASK_COMMAND + "' to create a new ToDo task");
        System.out.println("Enter '" + ALL_TASK_COMMAND + "' to see all ToDo tasks");
        System.out.println("Enter '" + CHECK_DONE_TASK_COMMAND + "' to cross off a ToDo task");
        System.out.println("Enter '" + CLEAN_DONE_TASK_COMMAND + "' to clean all completed ToDo tasks");
        System.out.println("Enter '" + CLOSE_APP_COMMAND + "' to close NotMuchToDo App");
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
