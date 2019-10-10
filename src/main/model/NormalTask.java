package model;

import static model.ImportanceLevel.NORMAL;

public class NormalTask extends ToDoTask {
    public NormalTask(String taskContent) {
        super(taskContent);
    }

    @Override
    // MODIFIES: this
    // EFFECT: do nothing, normalTask cannot set the importance level
    public void setImportanceLevel(ImportanceLevel level) {
    }

    // EFFECTS: do nothing, normalTask cannot change importance level.
    @Override
    public void upgradeImpLevel() {
        level = NORMAL;
    }
}
