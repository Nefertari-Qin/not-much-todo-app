package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoTaskTest {
    private ToDoTask testToDoTask;
    private String taskName = "name";
    private String taskDscp = "NQ";
    private String taskType = "Test";
    private String taskDue = "2000-08-18";


    @BeforeEach
    public void runBefore() {
        testToDoTask = new ToDoTask(taskName, taskDscp, taskType, taskDue);
    }

    @Test
    public void testConstructor() {
        assertEquals(taskName, testToDoTask.getName());
        assertEquals(taskDscp, testToDoTask.getDescription());
        assertEquals(taskDue, testToDoTask.getDueTime());
        assertFalse(testToDoTask.isDue());
        assertFalse(testToDoTask.isDone());
    }

    @Test
    public void testSetName() {
        testToDoTask.setName("anotherName");
        assertEquals("anotherName", testToDoTask.getName());
    }

    @Test
    public void testSetDscp() {
        testToDoTask.setDescription("NG");
        assertEquals("NG", testToDoTask.getDescription());
    }

    @Test
    public void testSetType() {
        testToDoTask.setType("Try");
        assertEquals("Try", testToDoTask.getType());
    }

    @Test
    public void testSetDueTime() {
        testToDoTask.setDueTime("2019-09-22");
        assertEquals("2019-09-22", testToDoTask.getDueTime());
    }

    @Test
    public void testSetDue() {
        testToDoTask.setDue(true);
        assertTrue(testToDoTask.isDue());
    }

    @Test
    public void testSetDone() {
        testToDoTask.setDone(true);
        assertTrue(testToDoTask.isDone());
    }

    @Test
    public void testHashEquals() {
        assertTrue(testToDoTask.equals(testToDoTask));
        assertFalse(testToDoTask.equals(null));
        assertNotEquals(testToDoTask, new Boolean(true));
        ToDoTask task = new ToDoTask(taskName, taskDscp, taskType, taskDue);
        task.setDue(true);
        assertFalse(testToDoTask.equals(task));
        task.setDue(false);
        task.setDone(true);
        assertFalse(testToDoTask.equals(task));
        assertFalse(testToDoTask.equals(new ToDoTask("taskName", taskDscp, taskType, taskDue)));
        assertFalse(testToDoTask.equals(new ToDoTask(taskName, "taskDscp", taskType, taskDue)));
        assertFalse(testToDoTask.equals(new ToDoTask(taskName, taskDscp, "taskType", taskDue)));
        assertFalse(testToDoTask.equals(new ToDoTask(taskName, taskDscp, taskType, "taskDue")));
    }
}
