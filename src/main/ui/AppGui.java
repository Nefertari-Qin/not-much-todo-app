package ui;

import model.App;
import ui.tabs.EditTab;
import ui.tabs.ToDoListsTab;
import ui.tabs.ToDoTasksTab;

import javax.swing.*;
import java.awt.*;

public class AppGui {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 880;
    private App app;

    public static void main(String[] args) {
        new AppGui();
    }

    //MODIFIES: this
    //EFFECTS: creates SmartHomeUI, loads SmartHome appliances, displays sidebar and tabs
    private AppGui() {
        JFrame appGui = new JFrame("Not Much ToDo App");
        appGui.setSize(WIDTH, HEIGHT);
        appGui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app = new App();

        JPanel tasksPanel = new TasksPanel();
        JPanel listsPanel = new ListsPanel();

        appGui.add(tasksPanel);
        appGui.add(listsPanel, BorderLayout.WEST);
        appGui.setVisible(true);
    }
}


