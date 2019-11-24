package ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class ToDoListsGUI extends JPanel implements ListSelectionListener {
    private static final String CREATE_BUTTON = "Create";
    private static final String DELETE_BUTTON = "Delete";
    public static final int TEXT_BOX_WIDTH = 20;
    public static final int MAX_LIST_VISIBLE_ROW = 8;

    private JList toDoLists;
    private DefaultListModel toDoListModel;
    private JButton createButton;
    private JButton deleteButton;
    private JTextField toDoListName;

    public ToDoListsGUI() {
        super(new BorderLayout());
        toDoListModel = new DefaultListModel();
        initializeJList();
        JScrollPane listScrollPane = new JScrollPane(toDoLists);

        initializeCreateButtonAndTextField();
        initializeDeleteButton();
        JPanel buttonPane = initializeButtonPane();

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // Create a JList for toDoLists
    // MODIFIES: this
    // EFFECTS: instantiate and initialize a JList
    private void initializeJList() {
        toDoLists = new JList(toDoListModel);
        toDoLists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        toDoLists.setSelectedIndex(0);
        toDoLists.addListSelectionListener(this);
        toDoLists.setVisibleRowCount(MAX_LIST_VISIBLE_ROW);
    }

    private void initializeCreateButtonAndTextField() {
        createButton = new JButton(CREATE_BUTTON);
        CreateListener cl = new CreateListener(createButton);
        createButton.setActionCommand(CREATE_BUTTON);
        createButton.addActionListener(cl);
        createButton.setEnabled(false);

        toDoListName = new JTextField(TEXT_BOX_WIDTH);
        toDoListName.addActionListener(cl);
        toDoListName.getDocument().addDocumentListener(cl);
    }

    private void initializeDeleteButton() {
        deleteButton = new JButton(DELETE_BUTTON);
        deleteButton.setActionCommand(DELETE_BUTTON);
        deleteButton.addActionListener(new DeleteListener());
    }

    private JPanel initializeButtonPane() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(deleteButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(toDoListName);
        buttonPane.add(createButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        return buttonPane;
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
                deleteButton.setEnabled(false);
            } else {
                //Selection, enable the delete button.
                deleteButton.setEnabled(true);
            }
        }
    }

    class CreateListener implements ActionListener, DocumentListener {
        private boolean isEnabled = false;
        private JButton button;

        public CreateListener(JButton button) {
            this.button = button;
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = toDoListName.getText();

            if (name.equals("") || hasListAlreadyCreated(name)) {
                Toolkit.getDefaultToolkit().beep();
                toDoListName.requestFocusInWindow();
                toDoListName.selectAll();
                // Try following error message generator showed in C04-AlarmSystem
                JOptionPane.showMessageDialog(null,
                        "ToDo List: " + name + " has already been created",
                        "System Error",
                        JOptionPane.ERROR_MESSAGE);

                return;
            }

            updateSelectionIndexAfterCreation();

            //Reset the text field.
            toDoListName.requestFocusInWindow();
            toDoListName.setText("");
        }

        /**
         * Gives notification that there was an insert into the document.  The
         * range given by the DocumentEvent bounds the freshly inserted region.
         *
         * @param e the document event
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        private void enableButton() {
            if (!isEnabled) {
                button.setEnabled(true);
            }
        }

        /**
         * Gives notification that a portion of the document has been
         * removed.  The range is given in terms of what the view last
         * saw (that is, before updating sticky positions).
         *
         * @param e the document event
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        /**
         * Gives notification that an attribute or set of attributes changed.
         *
         * @param e the document event
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void updateSelectionIndexAfterCreation() {
            int index = toDoLists.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }
            toDoListModel.insertElementAt(toDoListName.getText(), index);

            //Select the new item and make it visible.
            toDoLists.setSelectedIndex(index);
            toDoLists.ensureIndexIsVisible(index);
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                isEnabled = false;
                return true;
            }
            return false;
        }

        private boolean hasListAlreadyCreated(String name) {
            return toDoListModel.contains(name);
        }
    }

    class DeleteListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = toDoLists.getSelectedIndex();
            removeJToDoListAt(index);
            int newIndex = updateIndexOfSelectedAfterRemove(index);

            toDoLists.setSelectedIndex(newIndex);
            toDoLists.ensureIndexIsVisible(newIndex);
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
                deleteButton.setEnabled(false);
            } else if (index == toDoListModel.getSize()) {
                //removed item in last position
                index--;
            }
            return index;
        }
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ToDoListGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ToDoListsGUI();
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
