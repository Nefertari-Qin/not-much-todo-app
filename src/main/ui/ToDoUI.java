package ui;

import java.util.Scanner;

// Represent a class with user interaction
// Many interaction methods learned from LittleLoggingCalculator from Basics 4's lecture lab
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

    private void handleUserInput() {
        printInstructions();
        String inputString;

        while (runApp) {
            inputString = getFormattedInputString();
            parseInput(inputString);
        }
    }

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
                    endProgram();
                    break;
                default:
                    System.out.println("Sorry, I didn't understand that. Please try again.");
                    printInstructions();
                    break;
            }
        }
    }

    private void handleCrossTask() {
        // stub
    }

    private void handleCreateNewTask() {
        System.out.println("\nEnter the task you want to do:");
        String task = getInputString();
        System.out.println("A new ToDo task is successfully added to NotMuchToDo App.");
        System.out.println("\nEnter the name of the task creator:");
        String creatorName = getInputString();
        System.out.println("ToDo task: '" + task + "' was made by " + creatorName + ".");
    }


    private void endProgram() {
        runApp = false;
        input.close();
        System.out.println("Closing ...");
    }

    private void printInstructions() {
        System.out.println("\nSome things you can do:\n");
        System.out.println("Enter '" + CREATE_TASK_COMMAND + "' to create a new ToDo task");
        System.out.println("Enter '" + CROSS_TASK_COMMAND + "' to cross off a ToDo task");
        System.out.println("Enter '" + CLOSE_APP_COMMAND + "' to close NotMuchToDo App");
    }

    private String getFormattedInputString() {
        String str = "";
        if (input.hasNext()) {
            str = input.nextLine();
            str = makeFormattedString(str);
        }
        return str;
    }

    private String getInputString() {
        String str = "";
        if (input.hasNext()) {
            str = input.nextLine();
        }
        return str;
    }

    //EFFECTS: removes white space and quotation marks around s
    private String makeFormattedString(String s) {
        s = s.toLowerCase();
        s = s.trim();
        s = s.replaceAll("\"|\'", "");
        return s;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to NotMuchToDo Application!");
        ToDoUI myToDo = new ToDoUI();
        myToDo.handleUserInput();
    }
}
