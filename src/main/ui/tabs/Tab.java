package ui.tabs;

import ui.AppGui;
import ui.TasksPanel;

import javax.swing.*;
import java.awt.*;

public abstract class Tab {

    private TasksPanel tasksController;

    public JPanel getTab() {
        return tab;
    }

    protected JPanel tab;

    //REQUIRES: tasksController that holds this tab
    public Tab(TasksPanel tasksController) {
        this.tasksController = tasksController;
        tab = new JPanel();
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    //EFFECTS: returns the AppMainGui controller for this tab
    public TasksPanel getTasksController() {
        return tasksController;
    }

}


