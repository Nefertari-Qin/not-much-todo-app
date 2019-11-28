package model;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Action {
    private Date timeOfAction;
    private String content;

    public Action(String content) {
        timeOfAction = Calendar.getInstance().getTime();
        this.content = content;
    }

    // Getters:
    public Date getTimeOfAction() {
        return timeOfAction;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Action action = (Action) o;
        return timeOfAction.equals(action.timeOfAction) && content.equals(action.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeOfAction, content);
    }

    @Override
    public String toString() {
        return timeOfAction.toString() + "\n" + content;
    }
}
