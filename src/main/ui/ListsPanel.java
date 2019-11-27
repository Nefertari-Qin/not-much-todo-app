package ui;

import model.App;
import model.ToDoList;
import model.exceptions.AlreadyExistException;
import model.exceptions.DoesntExistException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

// Code & Idea reference:
// The Construction of ToDoListsGUI relied on very careful examination of following source:
// ---> B01-SmartHome           Optional Practice Problem
// ---> A06-SimpleDrawingPlayer Lecture Lab
// ---> C04-AlarmSystem         Lecture Lab
// ---> ListDemo                https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
// ---> JList Java Tutorials    https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
// ---> JList Java DOCS         https://docs.oracle.com/javase/8/docs/api/javax/swing/JList.html

// Side Note: There is no way for me to create such a fancy (at least I think it is) GUI from scratch.
//            Even though it was built from those template-like demo projects listed above,
//            I did spent a ton of time searching and looking up related Java API.

// Represent a GUI for ListsPanel
public class ListsPanel extends JPanel {
    public static final String NAME = "Nefertari";
    public static final String LIST_CREATION_PRFX = "ToDoLists";
    public static final String NEW_CMD = "new";
    public static final String DEL_CMD = "del";

    public static final int LP_WIDTH = AppGui.WIDTH / 4 - 10;
    public static final int LP_HEIGHT = AppGui.HEIGHT;
    public static final int MAX_LIST_VISIBLE_ROW = 8;

    private App app;
    private JList jtoDoLists;
    private DefaultListModel toDoListModel;
    private JButton newBtn;
    private JButton delBtn;
    private ListSelectionListener ll;

    public ListsPanel(App app, ListSelectionListener ll) {
        this.app = app;
        this.ll = ll;
        setPreferredSize(new Dimension(LP_WIDTH, LP_HEIGHT));
        setBackground(new Color(50, 50, 50, 50));
        toDoListModel = new DefaultListModel();

        JLabel greetingLabel = initializeGreeting();
        JLabel nameArea = new JLabel(NAME + "!");
        nameArea.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        initializeJList();
        JPanel toDoListPanel = initializeToDoListPanel();
        JScrollPane listScrollPane = new JScrollPane(jtoDoLists);
        listScrollPane.setPreferredSize(new Dimension(LP_WIDTH - 20, LP_HEIGHT / 4));

        add(greetingLabel);
        add(nameArea);
        add(toDoListPanel);
        add(listScrollPane);
    }

    // Initialize a JLabel.
    // EFFECTS: return a JLabel with correct greeting message.
    private JLabel initializeGreeting() {
        String greeting = chooseGreetingString();
        JLabel greetingArea = new JLabel(greeting);
        //JLabel nameArea = new JLabel(NAME + "!");
        greetingArea.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        //greetingArea.setPreferredSize(new Dimension(LP_WIDTH, LP_HEIGHT / 25));
        //nameArea.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        //nameArea.setPreferredSize(new Dimension(LP_WIDTH, LP_HEIGHT / 25));
        return greetingArea;
    }

    // Initialize a JList.
    // MODIFIES: this
    // EFFECTS: instantiate and initialize a JList to hold ToDoLists
    private void initializeJList() {
        jtoDoLists = new JList(toDoListModel);
        jtoDoLists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtoDoLists.setSelectedIndex(0);

        jtoDoLists.addListSelectionListener(new ToDoListListSelectionListener());
        jtoDoLists.addListSelectionListener(ll);

        jtoDoLists.setVisibleRowCount(MAX_LIST_VISIBLE_ROW);
        jtoDoLists.setBackground(new Color(65, 65, 65, 100));
        jtoDoLists.setCellRenderer(new FontCellRenderer());
        jtoDoLists.setOpaque(false);
    }

    // Initialize a JPanel
    // EFFECTS: instantiate and initialize a JPanel for adding and deleting ToDoList
    private JPanel initializeToDoListPanel() {
        JPanel toDoListPanel = new JPanel();
        JLabel listEditingArea = getToDoListEditingLabel();
        initializeNewToDoListButton();
        initializeDelToDoListButton();

        toDoListPanel.add(listEditingArea, BorderLayout.WEST);
        toDoListPanel.add(Box.createHorizontalStrut(25));
        toDoListPanel.add(newBtn, BorderLayout.EAST);
        toDoListPanel.add(Box.createHorizontalStrut(5));
        toDoListPanel.add(delBtn);
        return toDoListPanel;
    }

    // A helper to choose greeting string
    // EFFECTS: Return correct greeting string base on the time in the day
    private String chooseGreetingString() {
        String greeting;
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonth().getValue();
        int day = now.getDayOfMonth();

        LocalDateTime noon = LocalDateTime.of(year, month, day, 12, 0);
        LocalDateTime dusk = LocalDateTime.of(year, month, day, 18, 0);
        if (now.isBefore(noon)) {
            greeting = "Good Morning, ";
        } else if (now.isAfter(dusk)) {
            greeting = "Good Evening, ";
        } else {
            greeting = "Good Afternoon, ";
        }
        return greeting;
    }

    // Initialize a JLabel
    // EFFECTS: instantiate and initialize a JLabel for ToDoList Title
    private JLabel getToDoListEditingLabel() {
        JLabel listEditingArea = new JLabel(LIST_CREATION_PRFX);
        listEditingArea.setFont(new Font(Font.SERIF, Font.BOLD, 22));
        listEditingArea.setSize(LP_WIDTH, HEIGHT);
        return listEditingArea;
    }

    // Initialize a JButton
    // EFFECTS: instantiate and initialize a JButton for adding new ToDoList
    private void initializeNewToDoListButton() {
        newBtn = new JButton(NEW_CMD);
        newBtn.setActionCommand(NEW_CMD);
        newBtn.setMargin(new Insets(0, 0, 0, 0));
        newBtn.setPreferredSize(new Dimension(30, 20));
        newBtn.addActionListener(new NewToDoListListener());
    }

    // Initialize a JButton
    // EFFECTS: instantiate and initialize a JButton for deleting selected ToDoList
    private void initializeDelToDoListButton() {
        delBtn = new JButton(DEL_CMD);
        delBtn.setActionCommand(DEL_CMD);
        delBtn.setMargin(new Insets(0, 0, 0, 0));
        delBtn.setPreferredSize(new Dimension(30, 20));
        delBtn.addActionListener(new DelToDoListListener());
    }


    class ToDoListListSelectionListener implements ListSelectionListener {

        /**
         * Called whenever the value of the selection changes.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() == false) {
                if (jtoDoLists.getSelectedIndex() == -1) {
                    delBtn.setEnabled(false);
                    app.exitToDoList();
                } else {
                    delBtn.setEnabled(true);
                    try {
                        app.enterToDoList((String) jtoDoLists.getSelectedValue());
                        // showTasksPanel(); // TODO: specify and implement this method later
                    } catch (DoesntExistException ex) {
                        JOptionPane.showMessageDialog(
                                null, ex.getMessage(),
                                "System Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    class NewToDoListListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event that indicates creation of a new ToDoList
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand() == NEW_CMD) {
                String name = (String) JOptionPane.showInputDialog(
                        null,
                        "Enter the name of new ToDo List:",
                        "New ToDo List",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "new todo list");
                if ((name != null) && (name.length() > 0)) {
                    createToDoList(name);
                }
            }
        }

        private void createToDoList(String name) {
            if (name.equals("")) {
                JOptionPane.showMessageDialog(null, "ToDo List name cannot be empty!",
                        "System Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                ToDoList newList = new ToDoList(name);
                app.addToDoList(newList);
                app.enterToDoList(name);
                updateSelectionIndexAfterCreation(name);
            } catch (AlreadyExistException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (DoesntExistException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void updateSelectionIndexAfterCreation(String name) {
            int index = jtoDoLists.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }
            toDoListModel.insertElementAt(name, index);
            jtoDoLists.setSelectedIndex(index);
            jtoDoLists.ensureIndexIsVisible(index);
        }
    }

    class DelToDoListListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event that indicates deleting selected ToDoList
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand() == DEL_CMD) {
                int index = jtoDoLists.getSelectedIndex();
                String name = (String) jtoDoLists.getSelectedValue();
                removeJToDoListAt(name, index);
                int newIndex = updateIndexOfSelectedAfterRemove(index);

                jtoDoLists.setSelectedIndex(newIndex);
                jtoDoLists.ensureIndexIsVisible(newIndex);
            }
        }

        private void removeJToDoListAt(String name, int index) {
            if (index < 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "No ToDo List has been created yet.",
                        "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    app.removeToDoList(name);
                    toDoListModel.remove(index);
                } catch (DoesntExistException e) {
                    JOptionPane.showMessageDialog(
                            null, e.getMessage(),
                            "System Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private int updateIndexOfSelectedAfterRemove(int index) {
            int size = toDoListModel.getSize();
            if (size == 0) {
                delBtn.setEnabled(false);
            } else if (index == toDoListModel.getSize()) {
                //removed item in last position
                index--;
            }
            return index;
        }
    }

    class FontCellRenderer extends DefaultListCellRenderer {

        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            Font font = new Font((String) value, Font.TRUETYPE_FONT, 18);
            label.setFont(font);
            return label;
        }
    }


    // TODO: Delete all of the following code when finished. They are here just for
    //  development use, I need to somehow visualize individual component.
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ToDoListsGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(LP_WIDTH + 100, LP_HEIGHT - 300));
        //frame.setResizable(false);
        JComponent newContentPane = new ListsPanel(new App(), e -> {
        });
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
