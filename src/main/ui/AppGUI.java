package ui;

import model.App;
import model.ToDoList;
import model.exceptions.AlreadyExistException;
import model.exceptions.DoesntExistException;
import network.MessagePrinter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

// Code & Idea reference:
// The Construction of AppGUI relied on very careful examination of following projects:
// ---> B01-SmartHome                Optional Practice Problem
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
        setUpMessagePrinter();
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
        addMenuItem(createToDoListMenu, new CreateNewToDoListAction(), KeyStroke.getKeyStroke("control C"));
        return createToDoListMenu;
    }

    private JMenu getRemoveToDoListMenu() {
        JMenu removeToDoListMenu = new JMenu("Remove");
        removeToDoListMenu.setMnemonic('R');
        addMenuItem(removeToDoListMenu, new RemoveToDoListAction(), KeyStroke.getKeyStroke("control R"));
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

    private void setUpMessagePrinter() {
        MessagePrinter mp = new MessagePrinter();
        try {
            mp.printWebInfo();
        } catch (IOException e) {
            System.out.println("Something wrong with reading the web.");
        }
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
                    app.removeToDoList(toDoListName);
                    Component toDoListUIToRemove = getComponentToRemove(toDoListName);
                    desktop.remove(toDoListUIToRemove); //ToDo: figure out which to remove.
                    repaint();
                }
            } catch (DoesntExistException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        private Component getComponentToRemove(String toDoListName) {
            Component[] components = desktop.getComponents();
            for (Component c : components) {
                ToDoListUI toDoListUI = (ToDoListUI) c;
                if (toDoListUI.getToDoListName().equals(toDoListName)) {
                    return c;
                }
            }
            return null;
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
