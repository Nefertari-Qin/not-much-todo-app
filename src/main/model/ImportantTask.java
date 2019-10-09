package model;

import java.util.Calendar;

import static model.ImportanceLevel.*;

public class ImportantTask extends ToDoTask {
    public ImportantTask(String taskContent) {
        super(taskContent);
        level = LOW;
    }

    // EFFECTS: return the task content with exclamation mark indicate importance.
    @Override
    public String getTaskContent() {
        if (level == LOW) {
            return "! " + super.getTaskContent() + " !";
        } else if (level == MID) {
            return "!! " + super.getTaskContent() + " !!";
        } else {
            return "!!! " + super.getTaskContent() + " !!!";
        }
    }

    public void setImportanceLevel(ImportanceLevel level) {
        this.level = level;
    }

    // MODIFIES: this
    // EFFECTS: upgrades this task's importance level, if possible
    @Override
    public void upgradeImpLevel() {
        if (level == LOW) {
            level = MID;
        } else if (level == MID) {
            level = HIGH;
        }
    }
}
