package ui.tabs;

import ui.AppGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditTab extends Tab implements ActionListener {
    public static final String TODO_LIST_NAME = "New ToDo List Name";
    public static final String TODO_TASK_NAME = "New ToDo Task Name";

    public EditTab(AppGui appController) {
        super(appController);
        tab.setLayout(new GridLayout(3, 1));
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case TODO_LIST_NAME:
                JTextField source = (JTextField) e.getSource();
                break;
                // stub
            default:
                break;
        }

    }
}
