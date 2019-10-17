package tests;

import exceptions.ListDoesntExistException;
import exceptions.TaskDoesntExistException;
import model.ImportantTask;
import model.NormalTask;
import model.ToDoList;
import model.ToDoTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.App;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AppTest {
    private App testApp;

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
    }

    @Test
    public void testGetListThrowNoException() {
        try {
            assertEquals("test list 2", testApp.getList("test list 2").getToDoListName());
        } catch (ListDoesntExistException e) {
            fail("No exception should be thrown, caught TaskDoesntExistException");
        }
    }

    @Test
    public void testGetTaskThrowTaskDoesntExistException() {
        try {
            assertEquals("test list 2", testApp.getList("test list"));
            fail("No exception was thrown; Expect ListDoesn'tExistException");
        } catch (ListDoesntExistException e) {
            System.out.println("'testGetTaskThrowTaskDoesntExistException' passed.");
        }
    }
}
