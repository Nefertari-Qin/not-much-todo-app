package ui;

import model.App;

import javax.swing.*;
import java.awt.*;

public class AppGui {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private App app;

    public static void main(String[] args) {
        new AppGui();
    }

    private AppGui() {
        app = new App();
        JFrame appGui = new JFrame("Not Much ToDo App");
        appGui.setSize(WIDTH, HEIGHT);
        appGui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        appGui.setResizable(false);

        TasksPanel tasksPanel = new TasksPanel(app);
        ListsPanel listsPanel = new ListsPanel(app, tasksPanel.getListListener());

        appGui.add(tasksPanel);
        appGui.add(listsPanel, BorderLayout.WEST);
        appGui.setVisible(true);
    }
}


