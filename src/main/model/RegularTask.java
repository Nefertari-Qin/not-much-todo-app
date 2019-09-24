package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegularTask implements Task {
    private String task;
    private String creator;
    private Date dueDate;

    private boolean isCompleted;
    private boolean isDue;

    // Constructor
    // MODIFIES: this
    // EFFECTS: construct a RegularTask with task content, creator name, due date and is not completed.
    public RegularTask(String task, String creator, String dueDate) {
        this.task = task;
        this.creator = creator;
        setDueDate(dueDate);
        this.isCompleted = false;
    }

    // setters
    // MODIFIES: this
    // EFFECTS: set the task content of this
    @Override
    public void setTask(String task) {
        this.task = task;
    }

    // MODIFIES: this
    // EFFECTS: set the task creator of this
    @Override
    public void setCreator(String creator) {
        this.creator = creator;
    }

    // MODIFIES: this
    // EFFECTS: set the due date of this to dueDate
    @Override
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

    // MODIFIES: this
    // EFFECTS: set the completeness of this based on isCompleted
    @Override
    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    // getters
    // EFFECTS: return the task content of this
    @Override
    public String getTask() {
        return this.task;
    }

    // EFFECTS: return the task creator of this
    @Override
    public String getCreator() {
        return this.creator;
    }

    // EFFECTS: return the task due date of this
    @Override
    public String getDueDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(this.dueDate);
        return strDate;
    }

    // EFFECTS: return true is this is completed; false OW.
    @Override
    public boolean isCompleted() {
        return this.isCompleted;
    }

    // compare current date with the due date
    // MODIFIES: this
    // EFFECTS: return true if current date is >= due date; false OW.
    @Override
    public boolean isDue() {
        Date today = new Date();
        this.isDue = !today.before(dueDate);
        return this.isDue;
    }
}
