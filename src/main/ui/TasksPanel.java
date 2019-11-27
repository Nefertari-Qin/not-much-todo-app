package ui;

import model.App;
import model.ToDoList;
import model.ToDoTask;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

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
    private JTable table;
    private ListSelectionListener ll;

    public TasksPanel(App app) {
        this();
        this.app = app;
        ll = new TaskTableRendererListener();
    }

    public TasksPanel() {
        initializeTasksPanelSeting();

        JLabel titleLabel = initializeTitle();
        initializeToDoTaskJTable();
        JScrollPane scrollPane = initializeTaskTableScrollPane();

        JLabel editorLabel = new JLabel("ToDo Task Editor: ", SwingConstants.LEFT);
        editorLabel.setFont(new Font(Font.SERIF, Font.BOLD, 28));
        editorLabel.setPreferredSize(new Dimension(TP_WIDTH, ROW_HEIGHT));

        JPanel editorPanel = initializeEditorPanel();
        JPanel creationPanel = initializeCreationPanel();

        add(editorLabel, BorderLayout.NORTH);
        add(editorPanel);
        add(creationPanel);
        add(titleLabel);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private JPanel initializeCreationPanel() {
        JPanel cp = new JPanel();
        GroupLayout gl = new GroupLayout(cp);
        cp.setLayout(gl);
        cp.setOpaque(false);
        initializeGenerateTaskButton();
        JLabel due = getEditorAreaLabel("Task Due Date: ");
        GroupLayout.SequentialGroup row = gl.createSequentialGroup();
        GroupLayout.SequentialGroup column = gl.createSequentialGroup();
        row.addGap(15);
        row.addGroup(gl.createParallelGroup().addComponent(due));
        row.addGroup(gl.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(taskDue).addComponent(genBtn));
        column.addGroup(gl.createParallelGroup().addComponent(due).addComponent(taskDue));
        column.addGap(346);
        column.addGroup(gl.createParallelGroup().addComponent(genBtn));
        gl.setHorizontalGroup(column);
        gl.setVerticalGroup(row);
        return cp;
    }

    private void initializeGenerateTaskButton() {
        genBtn = new JButton("Generate Task");
        genBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        genBtn.setActionCommand(GEN_CMD);
        genBtn.setMargin(new Insets(0, 32, 0, 32));
        genBtn.addActionListener(new NewToDoTaskListener());
    }

    public ListSelectionListener getListListener() {
        return ll;
    }

    private JScrollPane initializeTaskTableScrollPane() {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(TP_WIDTH, SCL_PNL_HEIGHT));
        return scrollPane;
    }

    private void initializeTasksPanelSeting() {
        setPreferredSize(new Dimension(TP_WIDTH, TP_HEIGHT));
        setBackground(new Color(50, 50, 50, 50));
        setOpaque(true);
    }

    private JPanel initializeEditorPanel() {
        JPanel editorPanel = new JPanel();
        GroupLayout gl = new GroupLayout(editorPanel);
        editorPanel.setLayout(gl);
        editorPanel.setOpaque(false);
        initializeTextFields();
        GroupLayout.SequentialGroup row = gl.createSequentialGroup();
        GroupLayout.SequentialGroup column = gl.createSequentialGroup();
        layoutEditor(gl, row, column);
        gl.setHorizontalGroup(column);
        gl.setVerticalGroup(row);
        return editorPanel;
    }

    private void layoutEditor(GroupLayout gl, GroupLayout.SequentialGroup row, GroupLayout.SequentialGroup column) {
        JLabel name = getEditorAreaLabel("Task Name: ");
        JLabel dscp = getEditorAreaLabel("Task Content: ");
        JLabel type = getEditorAreaLabel("Task Type: ");
        row.addGroup(gl.createParallelGroup().addComponent(name).addComponent(dscp).addComponent(type));
        row.addGroup(gl.createParallelGroup().addComponent(taskName).addComponent(taskDscp).addComponent(taskType));
        column.addGroup(gl.createParallelGroup().addComponent(name).addComponent(taskName));
        column.addGap(70);
        column.addGroup(gl.createParallelGroup().addComponent(dscp).addComponent(taskDscp));
        column.addGap(70);
        column.addGroup(gl.createParallelGroup().addComponent(type).addComponent(taskType));
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

    private JLabel getEditorAreaLabel(String s) {
        JLabel label = new JLabel(s);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        return label;
    }

    private void initializeToDoTaskJTable() {
        toDoTaskTableModel = new MyTableModel();
        table = new JTable(toDoTaskTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(TP_WIDTH - 10, SCL_PNL_HEIGHT - 10));
        table.setFillsViewportHeight(false);
        table.setRowHeight(30);
        table.setOpaque(false);
    }

    private JLabel initializeTitle() {
        JLabel titleLabel = new JLabel("All ToDo Tasks in: ");
        titleLabel.setFont(new Font(Font.SERIF, Font.BOLD, 28));
        titleLabel.setPreferredSize(new Dimension(TP_WIDTH, TP_HEIGHT / 25));
        return titleLabel;
    }

    class TaskTableRendererListener implements ListSelectionListener, Observer {
        private ToDoList selectedList = app.getListCurrentIn();

        /**
         * Called whenever the value of the selection changes.
         *
         * @param e the event that characterizes the change.
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() == false) {
                updateTaskTable();
            }
        }

        private void addTaskToTable(ToDoTask t) {
            addRowToTable(
                    t.getName(),
                    t.getDescription(),
                    t.getType(),
                    t.getDueTime(),
                    new Boolean(t.isDone()));
        }

        // Helper to add row dynamically at run time.
        // MODIFIES: this
        // EFFECTS:
        private void addRowToTable(String n, String d, String t, String dueTime, Boolean f) {
            Vector<Object> data = new Vector<>();
            data.add(n);
            data.add(d);
            data.add(t);
            data.add(dueTime);
            data.add(f);
            toDoTaskTableModel.addRow(data);
        }

        private void updateTaskTable() {
            if (selectedList != null) {
                toDoTaskTableModel.setRowCount(0);
                for (ToDoTask t : selectedList) {
                    addTaskToTable(t);
                }
            }
        }

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
    }

    class NewToDoTaskListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event that indicates creation of a new ToDoTask
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand() == GEN_CMD) {
                ToDoList crnt = app.getListCurrentIn();
                if (crnt == null) {
                    JOptionPane.showMessageDialog(
                            null, "Select the ToDo List you want add Task to!",
                            "System Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    ToDoTask task = new ToDoTask(taskName.getText(), taskDscp.getText(),
                            taskType.getText(),  taskDue.getText());
                    crnt.addObserver((Observer) ll);
                    crnt.addToDoTask(task);
                }
            }
        }
    }

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

        // EFFECTS: Display last column of my table as CheckBox instead of String "true/false".
        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
    }


    // TODO: Delete all of the following code when finished. They are here just for
    //  development use, I need to somehow visualize individual component.
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ToDoTasksGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new TasksPanel(new App());
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
