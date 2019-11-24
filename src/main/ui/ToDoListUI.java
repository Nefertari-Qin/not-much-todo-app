package ui;

import model.ToDoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class ToDoListUI extends JInternalFrame {
    private static final int WIDTH = 580;
    private static final int HEIGHT = 100;
    private static int listCount = 0;
    private ToDoList theToDoList;
    private JTextField toDoListContent;
    private String toDoListName;

    public ToDoListUI(ToDoList toDoList) {
        super(toDoList.getName(), false, false, false, true);
        theToDoList = toDoList;
        toDoListName = toDoList.getName();
        toDoListContent = new JTextField(toDoListName + " has ... stuff");  // TODO
        toDoListContent.setEditable(false);
        toDoListContent.setAlignmentX(CENTER_ALIGNMENT);

        JButton enterList = new JButton(new EnterToDoListAction());
        enterList.setAlignmentX(CENTER_ALIGNMENT);
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        cp.add(toDoListContent);
        cp.add(enterList);
        setSize(WIDTH, HEIGHT);
        setPosition();
        listCount++;
        setVisible(true);
    }

    public String getToDoListName() {
        return toDoListName;
    }

    private void setPosition() {
        setLocation(0, 0 + HEIGHT * listCount);
    }

    private class EnterToDoListAction extends AbstractAction {
        EnterToDoListAction() {
            super("Enter");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // stub
        }
    }
}
