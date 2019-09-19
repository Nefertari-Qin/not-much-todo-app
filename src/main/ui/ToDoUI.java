package ui;

import java.util.Scanner;

// Reference:
// I learned many of the interaction methods
// from LittleLoggingCalculator in Basics 4's lecture lab
// and from practice problem FitLifeGymChain in long-form-problem-starters

// Represent a class with user interaction
public class ToDoUI {
    private static final String CREATE_TASK_COMMAND = "new task";
    private static final String CROSS_TASK_COMMAND = "cross task";
    private static final String CLOSE_APP_COMMAND = "close app";

    private boolean runApp;
    private Scanner input;

    public ToDoUI() {
        runApp = true;
        input = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: deal with general user input
    private void handleUserInput() {
        printInstructions();
        String inputString;

        while (runApp) {
            inputString = getFormattedInputString();
            parseInput(inputString);
        }
    }

    // MODIFIES: this
    // EFFECTS: interpret user input
    private void parseInput(String string) {
        if (string.length() > 0) {
            switch (string) {
                case CREATE_TASK_COMMAND:
                    handleCreateNewTask();
                    break;
                case CROSS_TASK_COMMAND:
                    handleCrossTask();
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
    }

    private void handleCrossTask() {
        System.out.println("\nYou crossed off a task from the ToDo list");
        printInstructions();
    }

    private void handleCreateNewTask() {
        System.out.println("\nEnter the task you want to do:");
        String task = getInputString();
        System.out.println("A new ToDo task is successfully added to NotMuchToDo App.");
        System.out.println("\nEnter the name of the task creator:");
        String creatorName = getInputString();
        System.out.println("ToDo task: '" + task + "' was made by " + creatorName + ".");
        printInstructions();
    }

    // EFFECTS: get user input
    private String getInputString() {
        String str = "";
        if (input.hasNext()) {
            str = input.nextLine();
        }
        return str;
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
        System.out.println("Enter '" + CROSS_TASK_COMMAND + "' to cross off a ToDo task");
        System.out.println("Enter '" + CLOSE_APP_COMMAND + "' to close NotMuchToDo App");
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

    public static void main(String[] args) {
        System.out.println("Welcome to NotMuchToDo Application!");
        ToDoUI toDoUI = new ToDoUI();
        toDoUI.handleUserInput();
    }
}
