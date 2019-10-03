package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.App;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLoadable {

    private App testApp;
    private static final String TESTLOADABLE_JSON = "./data/testloadable.json";

    @BeforeEach
    private void runBefore() {
        testApp = new App();
        ToDoList testList1 = new ToDoList("test list 1");
        ToDoList testList2 = new ToDoList("test list 2");
        ToDoTask n1 = new NormalTask("n1");
        ToDoTask n2 = new NormalTask("n2");
        ToDoTask i1 = new ImportantTask("i1");
        ToDoTask i2 = new ImportantTask("i2");
        ToDoTask i3 = new ImportantTask("i3");
        testList1.addTask(n1);
        testList1.addTask(i2);
        testList2.addTask(i1);
        testList2.addTask(n2);
        testList2.addTask(i3);
        testApp.addList(testList1);
        testApp.addList(testList2);
        testApp.save(TESTLOADABLE_JSON);
    }

    @Test
    public void testLoad() {
        App testApp2 = new App();
        testLoad(testApp2);
        List<ToDoList> listsInApp = testApp2.getToDoLists();
        ToDoList list1 = listsInApp.get(0);
        ToDoList list2 = listsInApp.get(1);
        assertEquals("test list 1", list1.getToDoListName());
        assertEquals(2, list1.countTasks());
        assertEquals("test list 2", list2.getToDoListName());
        assertEquals(3, list2.countTasks());
    }

    private void testLoad(Loadable loadable) {
        loadable.load(TESTLOADABLE_JSON);
    }
}
