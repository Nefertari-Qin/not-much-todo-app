package tests;

import org.junit.jupiter.api.BeforeEach;
import ui.ToDoApp;

class ToDoAppTest {
    private ToDoApp testToDoApp;

    @BeforeEach
    public void runBefore() {
        testToDoApp = new ToDoApp();
    }
}