package model;

import java.util.Date;

public class RegularTask {
    private UrgencyLevel level;
    private String content;
    private Date dueTime;
    private boolean isDue;
    private boolean isDone;

    public RegularTask(String content) {
        this.content = content;
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
}
