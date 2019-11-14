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
        super(toDoList.getName(), false, false, false, false);
        theToDoList = toDoList;
        toDoListName = toDoList.getName();
        toDoListContent = new JTextField(toDoListName + " has ... stuff");  // TODO
        toDoListContent.setEditable(false);
        toDoListContent.setAlignmentX(CENTER_ALIGNMENT);

        JButton enterList = new JButton(new OpenCloseAction());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ToDoListUI that = (ToDoListUI) o;
        return theToDoList.equals(that.theToDoList)
                && toDoListContent.equals(that.toDoListContent)
                && toDoListName.equals(that.toDoListName);
    }

    private void setPosition() {
        setLocation(0, 0 + HEIGHT * listCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theToDoList, toDoListContent, toDoListName);
    }

    private class OpenCloseAction extends AbstractAction {
        OpenCloseAction() {
            super("Enter");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // stub
        }
    }
}
