package ui;

import model.ToDoTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Reference:
// I learned many of the interaction methods
// from LittleLoggingCalculator in Basics 4's lecture lab
// and from practice problem FitLifeGymChain in long-form-problem-starters

// Represent a class with user interaction
public class ToDoApp {
    private static final String CREATE_TASK_COMMAND = "new task";
    private static final String CROSS_TASK_COMMAND = "cross task";
    private static final String ALL_TASK_COMMAND = "all task";
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

    // EFFECTS: returns true if task is a already made ToDoTask in App
    private boolean containsTask(String task) {
        // stub
        return false;
    }

    // EFFECTS: suggest create new ToDoTask if no task is found,
    // otherwise print all ToDoTasks being made
    private void printAllTasks() {
        if (tasks.size() == 0) {
            System.out.println("No task need to do yet. Let's create a new ToDoTask!");
            printInstructions();
        } else {
            System.out.println("All tasks to do:");
            for (ToDoTask task : tasks) {
                System.out.println(task.getTask()
                        + " by " + task.getCreator()
                        + ", due on " + task.getDueDate()
                        + ". \nCompletion: "  + task.isCompleted());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deal with general user input
    public void handleUserInput() {
        printInstructions();
        String inputString;

        while (runApp) {
            inputString = getFormattedInputString();
            if (inputString.length() > 0) {
                parseInput(inputString);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: interpret user input
    private void parseInput(String string) {
        switch (string) {
            case CREATE_TASK_COMMAND:
                handleCreateNewTask();
                break;
            case CROSS_TASK_COMMAND:
                handleCrossTask();
                break;
            case ALL_TASK_COMMAND:
                printAllTasks();
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
        ToDoTask toDoTask = new ToDoTask(task, creator, dueDate);
        System.out.println("ToDo task: '" + task + "' was made by " + creator + ", and will due on " + dueDate);
        addTask(toDoTask);
        printInstructions();
    }

    private void handleCrossTask() {
        System.out.println("\nYou crossed off a task from the ToDo list");
        printInstructions();
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
        System.out.println("Enter '" + CROSS_TASK_COMMAND + "' to cross off a ToDo task");
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
