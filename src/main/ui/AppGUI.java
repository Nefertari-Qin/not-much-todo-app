package ui;

import model.App;
import model.ToDoList;
import model.exceptions.AlreadyExistException;
import model.exceptions.DoesntExistException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Code & Idea reference:
// The Construction of AppGUI relied on very careful examination of following projects:
// ---> B01-SmartHome                Optional Practice
// ---> A06-SimpleDrawingPlayer      Lecture Lab
// ---> C04-AlarmSystem              Lecture Lab

// Side Note: There is no way for me to create such a fancy (at least I think it is) GUI from scratch.
//            Even though it was modeled from those projects listed above, I did spent a ton of time
//            searching and looking up related Java API.

// Represent a GUI for App
public class AppGUI extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 900;

    private App app;
    private JDesktopPane desktop;

    public AppGUI() {
        app = new App();
        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        setContentPane(desktop);
        setTitle("Not Much ToDo App");
        setSize(WIDTH, HEIGHT);

        addMenu();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(getCreateToDoListMenu());
        menuBar.add(getRemoveToDoListMenu());
        setJMenuBar(menuBar);
    }

    private JMenu getCreateToDoListMenu() {
        JMenu createToDoListMenu = new JMenu("Create");
        createToDoListMenu.setMnemonic('C');
        addMenuItem(createToDoListMenu, new CreateNewToDoListAction(),
                KeyStroke.getKeyStroke("control C"));
        return createToDoListMenu;
    }

    private JMenu getRemoveToDoListMenu() {
        JMenu removeToDoListMenu = new JMenu("Remove");
        removeToDoListMenu.setMnemonic('R');
        addMenuItem(removeToDoListMenu, new RemoveToDoListAction(),
                KeyStroke.getKeyStroke("control R"));
        return removeToDoListMenu;
    }

    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    // EFFECTS: place App window at the centre of desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    private class CreateNewToDoListAction extends AbstractAction {

        CreateNewToDoListAction() {
            super("Create New ToDo List");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String toDoListName = JOptionPane.showInputDialog(null,
                    "Name of ToDo list to create?",
                    "Enter ToDo list name",
                    JOptionPane.QUESTION_MESSAGE);
            try {
                if (toDoListName != null) {
                    ToDoList toDoList = new ToDoList(toDoListName);
                    app.addToDoList(toDoList);
                    desktop.add(new ToDoListUI(toDoList));
                }
            } catch (AlreadyExistException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class RemoveToDoListAction extends AbstractAction {

        RemoveToDoListAction() {
            super("Remove ToDo List");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String toDoListName = JOptionPane.showInputDialog(null,
                    "Name of ToDo list to remove?",
                    "Enter ToDo list name",
                    JOptionPane.QUESTION_MESSAGE);
            try {
                if (toDoListName != null) {
                    ToDoList toDoList = new ToDoList(toDoListName);
                    app.removeToDoList(toDoListName);
                    desktop.remove(new ToDoListUI(toDoList)); // TODO: seems like override equals and hashCode didn't help
                }
            } catch (DoesntExistException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            AppGUI.this.requestFocusInWindow();
        }
    }

    public static void main(String[] args) {
        new AppGUI();
    }
}
