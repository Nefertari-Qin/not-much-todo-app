package ui;

import model.App;
import ui.tabs.EditTab;
import ui.tabs.ToDoListsTab;
import ui.tabs.ToDoTasksTab;

import javax.swing.*;

public class AppMainGui extends JFrame {

    public static final int EDIT_TAB_INDEX = 0;
    public static final int LISTS_TAB_INDEX = 1;
    public static final int TASK_TAB_INDEX = 2;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private JTabbedPane topTabArea;
    private App app;

    public static void main(String[] args) {
        new AppMainGui();
    }

    //MODIFIES: this
    //EFFECTS: creates SmartHomeUI, loads SmartHome appliances, displays sidebar and tabs
    private AppMainGui() {
        super("Not Much ToDo App");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        app = new App();
        loadAppliances();

        topTabArea = new JTabbedPane();
        topTabArea.setTabPlacement(JTabbedPane.TOP);

        loadTabs();
        add(topTabArea);

        setVisible(true);
    }

    //EFFECTS: returns SmartHome object controlled by this UI
    public App getApp() {
        return app;
    }

    //MODIFIES: this
    //EFFECTS: installs several appliances and sets no one home
    private void loadAppliances() {
        // stub
    }

    //MODIFIES: this
    //EFFECTS: adds editTab, toDoListsTab and toDoTasksTab to this UI
    private void loadTabs() {
        JPanel editTab = new EditTab(this);
        JPanel toDoListsTab = new ToDoListsTab(this);
        JPanel toDoTasksTab = new ToDoTasksTab(this);

        topTabArea.add(editTab, EDIT_TAB_INDEX);
        topTabArea.setTitleAt(EDIT_TAB_INDEX, "Edit");
        topTabArea.add(toDoListsTab, LISTS_TAB_INDEX);
        topTabArea.setTitleAt(LISTS_TAB_INDEX, "ToDoLists");
        topTabArea.add(toDoTasksTab, TASK_TAB_INDEX);
        topTabArea.setTitleAt(TASK_TAB_INDEX, "ToDoTasks");
    }

    //EFFECTS: returns sidebar of this UI
    public JTabbedPane getTabbedPane() {
        return topTabArea;
    }


}


