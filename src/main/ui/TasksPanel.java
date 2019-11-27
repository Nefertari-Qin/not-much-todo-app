package ui;

import model.App;
import model.ToDoList;
import model.ToDoTask;
import model.exceptions.DoesntExistException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

// Code & Idea reference:
// The Construction of ToDoListsGUI relied on very careful examination of following source:
// ---> B01-SmartHome            Optional Practice Problem
// ---> A06-SimpleDrawingPlayer  Lecture Lab
// ---> C04-AlarmSystem          Lecture Lab
// ---> TableDemo                https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
// ---> JTable Java Tutorials    https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
// ---> JTable Java DOCS         https://docs.oracle.com/javase/8/docs/api/javax/swing/JTable.html

// Side Note: There is no way for me to create such a fancy (at least I think it is) GUI from scratch.
//            Even though it was built from those template-like demo projects listed above,
//            I did spent a ton of time searching and looking up related Java API.

// Represent a GUI for TasksPanel
public class TasksPanel extends JPanel {
    public static final int TP_WIDTH = (AppGui.WIDTH / 4) * 3 - 25;
    public static final int TP_HEIGHT = AppGui.HEIGHT;
    public static final String GEN_CMD = "gen";
    public static final String RMV_CMD = "rmv";
    public static final int SCL_PNL_HEIGHT = 265;
    public static final int TEXT_BOX_WIDTH = 12;
    private static final int ROW_HEIGHT = 50;

    private App app;
    private DefaultTableModel toDoTaskTableModel;
    private JTextField taskName;
    private JTextField taskDscp;
    private JTextField taskType;
    private JTextField taskDue;
    private JButton genBtn;
    private JButton rmvBtn;
    private ListSelectionListener ll;
    private JList jtoDoLists;

    public TasksPanel(App app) {
        this.app = app;
        ll = new TaskTableRendererListener();
        jtoDoLists = null;
        initializeTasksPanelSetting();

        JLabel editorLabel = initializeEditorTitleLabel();
        JPanel editorPanel = initializeEditorPanel();
        JPanel creationPanel = initializeCreationPanel();
        JLabel tableLabel = initializeAllTaskTableTitle();
        JTable taskTable = initializeToDoTaskJTable();
        JScrollPane tableScrollablePane = initializeTaskTableScrollPane(taskTable);

        add(editorLabel);
        add(editorPanel);
        add(creationPanel);
        add(tableLabel);
        add(tableScrollablePane);
    }

    // Getter
    public ListSelectionListener getListListener() {
        return ll;
    }

    public void setJtoDoList(JList jtoDoLists) {
        this.jtoDoLists = jtoDoLists;
    }

    private void initializeTasksPanelSetting() {
        setPreferredSize(new Dimension(TP_WIDTH, TP_HEIGHT));
        setBackground(new Color(50, 50, 50, 50));
        setOpaque(true);
    }

    private JLabel initializeEditorTitleLabel() {
        JLabel editorLabel = new JLabel("ToDo Task Editor: ");
        editorLabel.setFont(new Font(Font.SERIF, Font.BOLD, 28));
        editorLabel.setPreferredSize(new Dimension(TP_WIDTH, ROW_HEIGHT));

        return editorLabel;
    }

    private JPanel initializeEditorPanel() {
        JPanel editorPanel = new JPanel();
        GroupLayout gl = new GroupLayout(editorPanel);
        editorPanel.setLayout(gl);
        editorPanel.setOpaque(false);

        initializeTextFields();
        layoutEditorPanel(gl);

        return editorPanel;
    }

    private JPanel initializeCreationPanel() {
        JPanel creationPanel = new JPanel();
        GroupLayout gl = new GroupLayout(creationPanel);
        creationPanel.setLayout(gl);
        creationPanel.setOpaque(false);

        initializeGenerateTaskButton();
        layoutCreationPanel(gl);

        return creationPanel;
    }

    private JLabel initializeAllTaskTableTitle() {
        JLabel titleLabel = new JLabel("All ToDo Tasks: ");
        titleLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));
        titleLabel.setPreferredSize(new Dimension(TP_WIDTH, TP_HEIGHT / 10));

        return titleLabel;
    }

    private JTable initializeToDoTaskJTable() {
        toDoTaskTableModel = new MyTableModel();
        JTable table = new JTable(toDoTaskTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(TP_WIDTH - 10, SCL_PNL_HEIGHT - 10));
        table.setFillsViewportHeight(false);
        table.setRowHeight(30);
        table.setOpaque(false);

        return table;
    }

    private JScrollPane initializeTaskTableScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(TP_WIDTH, SCL_PNL_HEIGHT));

        return scrollPane;
    }

    private void initializeTextFields() {
        JTextField fakeTF = new JTextField("", 1);
        Font bigFont = fakeTF.getFont().deriveFont(Font.PLAIN, 20f);

        taskName = new JTextField();
        taskName.setColumns(TEXT_BOX_WIDTH);
        taskName.setFont(bigFont);

        taskDscp = new JTextField();
        taskDscp.setColumns(TEXT_BOX_WIDTH);
        taskDscp.setFont(bigFont);

        taskType = new JTextField();
        taskType.setColumns(TEXT_BOX_WIDTH);
        taskType.setFont(bigFont);

        taskDue = new JTextField();
        taskDue.setColumns(TEXT_BOX_WIDTH);
        taskDue.setFont(bigFont);
    }

    private void initializeGenerateTaskButton() {
        genBtn = new JButton("Generate Task");
        genBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        genBtn.setActionCommand(GEN_CMD);
        genBtn.setMargin(new Insets(0, 32, 0, 32));

        genBtn.addActionListener(new NewToDoTaskListener());
    }


    private void layoutEditorPanel(GroupLayout gl) {
        JLabel name = getLabelForEditorArea("Task Name: ");
        JLabel dscp = getLabelForEditorArea("Task Content: ");
        JLabel type = getLabelForEditorArea("Task Type: ");

        GroupLayout.SequentialGroup row = gl.createSequentialGroup();
        GroupLayout.SequentialGroup column = gl.createSequentialGroup();

        row.addGroup(gl.createParallelGroup().addComponent(name).addComponent(dscp).addComponent(type));
        row.addGroup(gl.createParallelGroup().addComponent(taskName).addComponent(taskDscp).addComponent(taskType));

        column.addGroup(gl.createParallelGroup().addComponent(name).addComponent(taskName));
        column.addGap(70);
        column.addGroup(gl.createParallelGroup().addComponent(dscp).addComponent(taskDscp));
        column.addGap(70);
        column.addGroup(gl.createParallelGroup().addComponent(type).addComponent(taskType));

        gl.setHorizontalGroup(column);
        gl.setVerticalGroup(row);
    }

    private void layoutCreationPanel(GroupLayout gl) {
        JLabel dueDateLabel = getLabelForEditorArea("Task Due Date: ");
        GroupLayout.SequentialGroup row = gl.createSequentialGroup();
        GroupLayout.SequentialGroup column = gl.createSequentialGroup();
        row.addGap(15);
        row.addGroup(gl.createParallelGroup().addComponent(dueDateLabel));
        row.addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(taskDue).addComponent(genBtn));
        column.addGroup(gl.createParallelGroup().addComponent(dueDateLabel).addComponent(taskDue));
        column.addGap(346);
        column.addGroup(gl.createParallelGroup().addComponent(genBtn));
        gl.setHorizontalGroup(column);
        gl.setVerticalGroup(row);
    }

    private JLabel getLabelForEditorArea(String s) {
        JLabel label = new JLabel(s);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        return label;
    }


    // Represent a ListSelectionListener or an Observer for ToDoList that is accustomed to this program
    class TaskTableRendererListener implements ListSelectionListener, Observer {

        /**
         * This method is called whenever the observed object is changed. An
         * application calls an <tt>Observable</tt> object's
         * <code>notifyObservers</code> method to have all the object's
         * observers notified of the change.
         *
         * @param o   the observable object.
         * @param arg an argument passed to the <code>notifyObservers</code>
         */
        @Override
        public void update(Observable o, Object arg) {
            updateTaskTable();
        }

        /**
         * Called whenever the value of the selection changes.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {

            if (e.getValueIsAdjusting() == false) {
                if (jtoDoLists.getSelectedIndex() == -1) {
                    app.exitToDoList();
                } else {
                    try {
                        app.enterToDoList((String) jtoDoLists.getSelectedValue());
                    } catch (DoesntExistException ex) {
                        JOptionPane.showMessageDialog(
                                null, ex.getMessage(),
                                "System Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                updateTaskTable();
            }
        }

        // Extra Helper to update task table content dynamically at run time.
        // EFFECTS: update task table by first clear all content, then re-render.
        private void updateTaskTable() {
            while (toDoTaskTableModel.getRowCount() > 0) {
                toDoTaskTableModel.setRowCount(0);
            }
            ToDoList l = app.getListCurrentIn();
            if (l != null) {
                for (ToDoTask t : app.getListCurrentIn()) {
                    addTaskToTable(t);
                }
            }
        }

        private void addTaskToTable(ToDoTask t) {
            addRowToTable(t.getName(), t.getDescription(), t.getType(), t.getDueTime(), new Boolean(t.isDone()));
        }

        // Extra Helper to add row dynamically at run time.
        // MODIFIES: this
        // EFFECTS: add a row to table
        private void addRowToTable(String n, String d, String t, String dueTime, Boolean f) {
            Vector<Object> data = new Vector<>();
            data.add(n);
            data.add(d);
            data.add(t);
            data.add(dueTime);
            data.add(f);
            toDoTaskTableModel.addRow(data);
        }
    }


    // Represent a ActionListener that is accustomed to this program
    class NewToDoTaskListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event that indicates creation of a new ToDoTask
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand() == GEN_CMD) {
                if (app.getListCurrentIn() == null) {
                    showWarningMessage("Select the ToDo List you want add Task to!");
                    return;
                } else if (taskName.getText().length() == 0 || taskDscp.getText().length() == 0
                        || taskType.getText().length() == 0 || taskDue.getText().length() == 0) {
                    showWarningMessage("Text Field cannot be Empty!");
                } else {
                    ToDoTask task = new ToDoTask(taskName.getText(), taskDscp.getText(),
                            taskType.getText(), taskDue.getText());
                    app.getListCurrentIn().addObserver((Observer) ll);
                    app.getListCurrentIn().addToDoTask(task);
                    taskName.setText("");
                    taskDscp.setText("");
                    taskType.setText("");
                    taskDue.setText("");
                }
            }
        }

        // Extra Helper to prompt user warning message.
        private void showWarningMessage(String wm) {
            JOptionPane.showMessageDialog(null, wm, "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Represent a TableModel that is accustomed to this program
    class MyTableModel extends DefaultTableModel {
        private final String[] header = new String[]{
                "Task Name",
                "Task Description",
                "Task Type",
                "Due Date YYYY-MM-DD",
                "Finished?"
        };

        private MyTableModel() {
            super();
            setColumnIdentifiers(header);
        }

        // Extra Helper Method overrides AbstractTableModel up the type Hierarchy:
        // EFFECTS: Display last column of my table as CheckBox instead of String "true/false".
        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
    }


    // TODO: Delete all of the following code when finished. They are here just for
    //  development use, I need to somehow visualize individual component.
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("ToDoTasksGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(TP_WIDTH + 100, TP_HEIGHT - 70));
        App a = new App();
        ToDoList t = new ToDoList("Default ToDo List");
        try {
            a.addToDoList(t);
            a.enterToDoList("Default ToDo List");
        } catch (Exception e) {
            System.out.println("Impossible Situation");
        }
        JComponent newContentPane = new TasksPanel(a);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
