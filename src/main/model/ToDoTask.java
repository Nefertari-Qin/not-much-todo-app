package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoTask {
    private String task;
    private String creator;
    private Date dueDate;

    private boolean isCompleted;
    private boolean isDue;

    // Constructor
    // MODIFIES: this
    // EFFECTS: construct a ToDoTask with task content, creator name, due date and is not completed.
    public ToDoTask(String task, String creator, String dueDate) {
        this.task = task;
        this.creator = creator;
        setDueDate(dueDate);
        this.isCompleted = false;
    }

    // setters
    public void setTask(String task) {
        this.task = task;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    // MODIFIES: this
    // EFFECTS: set the due date of this to dueDate
    public boolean setDueDate(String dueDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.dueDate = sdf.parse(dueDate);
            return true;
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again with valid date format: YYYY-MM-DD");
            return false;
        }
    }

    // getters
    public String getTask() {
        return this.task;
    }

    public String getCreator() {
        return this.creator;
    }

    public String getDueDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(this.dueDate);
        return strDate;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    // compare current date with the due date
    // MODIFIES: this
    // EFFECTS: return true if current date is >= due date; false OW.
    public boolean isDue() {
        Date today = new Date();
        this.isDue = !today.before(dueDate);
        return this.isDue;
    }



}
