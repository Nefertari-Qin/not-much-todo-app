package model;

import java.util.Date;
import java.util.Objects;

public class RegularTask {
    private UrgencyLevel level;
    private String content;
    private Date dueTime;
    private boolean isDue;
    private boolean isDone;

    public RegularTask(String content) {
        level = UrgencyLevel.R;
        this.content = content;
        isDue = false;
        isDone = false;
    }

    public UrgencyLevel getLevel() {
        return level;
    }

    public String getContent() {
        return content;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegularTask that = (RegularTask) o;
        return isDue == that.isDue
                && isDone == that.isDone && level == that.level
                && content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, content, isDue, isDone);
    }
}
