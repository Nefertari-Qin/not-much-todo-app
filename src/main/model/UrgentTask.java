package model;

import model.exceptions.AlreadyExistException;

import java.util.Date;
import java.util.Objects;

public class UrgentTask {
    private UrgencyLevel level;
    private String content;
    private Date dueTime;
    private boolean isDue;
    private boolean isDone;
    private ToDoList theToDoList;

    public UrgentTask(String content, ToDoList theToDoList) {
        level = UrgencyLevel.I;
        this.content = content;
        this.theToDoList = theToDoList;
        isDue = false;
        isDone = false;
    }

    public void setTheToDoList(ToDoList theToDoList) {
        if (theToDoList == null) {
            this.theToDoList = null;
        } else if (!this.theToDoList.equals(theToDoList)) {
            this.theToDoList = theToDoList;
            try {
                theToDoList.addUrgentTask(this);
            } catch (AlreadyExistException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ToDoList getTheToDoList() {
        return theToDoList;
    }

    public UrgencyLevel getLevel() {
        return level;
    }

    public void setLevel(UrgencyLevel level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public boolean isDue() {
        return isDue;
    }

    public void setDue(boolean due) {
        isDue = due;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    // IntelliJ self-generated equals and hasCode:
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UrgentTask that = (UrgentTask) o;
        return isDue == that.isDue
                && isDone == that.isDone
                && level == that.level
                && content.equals(that.content)
                && dueTime.equals(that.dueTime)
                && theToDoList.equals(that.theToDoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, content, dueTime, isDue, isDone, theToDoList);
    }
}
