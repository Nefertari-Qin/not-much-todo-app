package model;

import static model.ImportanceLevel.NORMAL;

public class NormalTask extends ToDoTask {
    public NormalTask(String taskContent) {
        super(taskContent);
    }

    // EFFECTS: do nothing, normalTask cannot change importance level.
    @Override
    public void upgradeImpLevel() {
        level = NORMAL;
    }
}
