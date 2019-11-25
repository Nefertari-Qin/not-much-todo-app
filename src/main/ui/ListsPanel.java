package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

// Code & Idea reference:
// The Construction of ToDoListsGUI relied on very careful examination of following source:
// ---> C04-AlarmSystem         Lecture Lab
// ---> ListDemo                https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
// ---> JList Java Tutorials    https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
// ---> JList Java DOCS         https://docs.oracle.com/javase/8/docs/api/javax/swing/JList.html

// Side Note: There is no way for me to create such a fancy (at least I think it is) GUI from scratch.
//            Even though it was built from those template-like demo projects listed above,
//            I did spent a ton of time searching and looking up related Java API.

// Represent a GUI for ToDoList Panel
public class ListsPanel extends JPanel implements ListSelectionListener {
    public static final String NAME = "Nefertari";
    public static final String LIST_CREATION_PRFX = "ToDoLists";
    public static final int LP_WIDTH = AppGui.WIDTH / 4;
    public static final int LP_HEIGHT = AppGui.HEIGHT;
    public static final int MAX_LIST_VISIBLE_ROW = 8;

    private JList toDoLists;
    private DefaultListModel toDoListModel;
    private JButton newBtn;
    private JButton delBtn;

    public ListsPanel() {
        setPreferredSize(new Dimension(LP_WIDTH, LP_HEIGHT));
        setBackground(new Color(50, 50, 50, 50));
        toDoListModel = new DefaultListModel();

        JLabel greetingLabel = initializeGreeting();
        initializeJList();
        JPanel toDoListPanel = initializeToDoListPanel();
        JScrollPane listScrollPane = new JScrollPane(toDoLists);
        listScrollPane.setPreferredSize(new Dimension(LP_WIDTH - 5, LP_HEIGHT / 4));

        add(greetingLabel, BorderLayout.NORTH);
        add(toDoListPanel, BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.NORTH);
    }

    // Initialize a JLabel.
    // EFFECTS: return a JLabel with correct greeting message.
    private JLabel initializeGreeting() {
        String greeting = chooseGreetingString();
        JLabel greetingArea = new JLabel(greeting + NAME + "!", JLabel.LEFT);  // TODO: I don't really think
        greetingArea.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));        //   the LEFT does anything ...
        greetingArea.setSize(WIDTH, HEIGHT);
        return greetingArea;
    }

    // Initialize a JList.
    // MODIFIES: this
    // EFFECTS: instantiate and initialize a JList
    private void initializeJList() {
        toDoLists = new JList(toDoListModel);
        toDoLists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        toDoLists.setSelectedIndex(0);
        toDoLists.addListSelectionListener(this);
        toDoLists.setVisibleRowCount(MAX_LIST_VISIBLE_ROW);
        toDoLists.setBackground(new Color(65, 65, 65, 100));
        toDoLists.setCellRenderer(new FontCellRenderer());
    }

    // Initialize a JPanel
    // EFFECTS:
    private JPanel initializeToDoListPanel() {
        JPanel toDoListPanel = new JPanel();
        JLabel listCreationArea = getToDoListLabel();
        initializeNewToDoListButton();
        initializeDelToDoListButton();

        toDoListPanel.add(listCreationArea, BorderLayout.WEST);
        toDoListPanel.add(Box.createHorizontalStrut(100));
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

    private JLabel getToDoListLabel() {
        JLabel listCreationArea = new JLabel(LIST_CREATION_PRFX);
        listCreationArea.setFont(new Font(Font.SERIF, Font.BOLD, 22));
        listCreationArea.setSize(LP_WIDTH, HEIGHT);
        return listCreationArea;
    }

    private void initializeNewToDoListButton() {
        final String NEW_CMD = "new";
        newBtn = new JButton(NEW_CMD);
        newBtn.setActionCommand(NEW_CMD);
        newBtn.setMargin(new Insets(0, 0, 0, 0));
        newBtn.setPreferredSize(new Dimension(30, 20));
        newBtn.addActionListener(e -> {
            if (e.getActionCommand() == NEW_CMD) {
                String s = (String) JOptionPane.showInputDialog(null,
                        "Enter the name of new ToDo List:", "New ToDo List", JOptionPane.PLAIN_MESSAGE,
                        null, null, "new todo list");
                if ((s != null) && (s.length() > 0)) {
                    createToDoList(s);
                }
            }
        });
    }

    private void initializeDelToDoListButton() {
        final String DEL_CMD = "del";
        delBtn = new JButton(DEL_CMD);
        delBtn.setActionCommand(DEL_CMD);
        delBtn.setMargin(new Insets(0, 0, 0, 0));
        delBtn.setPreferredSize(new Dimension(30, 20));
        delBtn.addActionListener(e -> {
            if (e.getActionCommand() == DEL_CMD) {
                int index = toDoLists.getSelectedIndex();
                removeJToDoListAt(index);
                int newIndex = updateIndexOfSelectedAfterRemove(index);

                toDoLists.setSelectedIndex(newIndex);
                toDoLists.ensureIndexIsVisible(newIndex);
            }
        });
    }

    private void createToDoList(String name) {
        if (name.equals("") || hasListAlreadyCreated(name)) {
            JOptionPane.showMessageDialog(null,
                    "ToDo List: " + name + " has already been created",
                    "System Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        updateSelectionIndexAfterCreation(name);
    }

    private void updateSelectionIndexAfterCreation(String name) {
        int index = toDoLists.getSelectedIndex();
        if (index == -1) {
            index = 0;
        } else {
            index++;
        }
        toDoListModel.insertElementAt(name, index);
        //Select the new item and make it visible.
        toDoLists.setSelectedIndex(index);
        toDoLists.ensureIndexIsVisible(index);
    }

    private boolean hasListAlreadyCreated(String name) {
        return toDoListModel.contains(name);
    }

    private void removeJToDoListAt(int index) {
        if (index < 0) {
            JOptionPane.showMessageDialog(null, "No ToDo List has been created yet.",
                    "System Error", JOptionPane.ERROR_MESSAGE);
        } else {
            toDoListModel.remove(index);
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

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            if (toDoLists.getSelectedIndex() == -1) {
                //No selection, disable delete button.
                delBtn.setEnabled(false);
            } else {
                //Selection, enable the delete button.
                delBtn.setEnabled(true);
            }
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

    // Following code here just for development use, I need to
    // somehow visualize individual component. TODO: Delete when done.
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ToDoListGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ListsPanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
