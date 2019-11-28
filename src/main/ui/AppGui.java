package ui;

import model.App;

import javax.swing.*;
import java.awt.*;

public class AppGui {

    static final int WIDTH = 1000;
    static final int HEIGHT = 600;

    public static void main(String[] args) {
        new AppGui();
    }

    private AppGui() {
        App app = new App();
        JFrame appGui = new JFrame("Not Much ToDo App");
        appGui.setSize(WIDTH, HEIGHT);
        appGui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        appGui.setResizable(false);

        TasksPanel tasksPanel = new TasksPanel(app);
        ListsPanel listsPanel = new ListsPanel(app, tasksPanel.getListListener());
        tasksPanel.setJtoDoList(listsPanel.getJtoDoLists());

        appGui.add(tasksPanel);
        appGui.add(listsPanel, BorderLayout.WEST);
        appGui.setVisible(true);
    }
}


