package tests;

import model.ToDoTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoTaskTest {
    private ToDoTask testToDoTask;
    private String taskName = "taskName";
    private String creatorName = "YQ";
    private String dueDate = "2000-08-18";


    @BeforeEach
    public void runBefore() {
        testToDoTask = new ToDoTask(taskName, creatorName, dueDate);
    }

    @Test
    public void testConstructor() {
        assertEquals(taskName,testToDoTask.getTask());
        assertEquals(creatorName,testToDoTask.getCreator());
        assertEquals(dueDate,testToDoTask.getDueDate());
        assertFalse(testToDoTask.isCompleted());
    }

    @Test
    public void testSetTask() {
        testToDoTask.setTask("anotherName");
        assertEquals("anotherName", testToDoTask.getTask());
    }

    @Test
    public void testSetCreator() {
        testToDoTask.setCreator("NG");
        assertEquals("NG", testToDoTask.getCreator());
    }

    @Test
    public void testSetDueDateValidFormat(){
        assertTrue(testToDoTask.setDueDate("2019-09-22"));
        assertEquals("2019-09-22", testToDoTask.getDueDate());
    }

    @Test
    public void testSetDueDateInvalidFormat(){
        assertFalse(testToDoTask.setDueDate("20190922"));
    }

    @Test
    public void testIsDueBeforeDue() {
        assertTrue(testToDoTask.isDue());
    }

    @Test
    public void testIsDueAtDue() {
        testToDoTask.setDueDate("2019-09-22");
        assertTrue(testToDoTask.isDue());
    }

    @Test
    public void testIsDueAfterDue() {
        testToDoTask.setDueDate("2019-12-22");
        assertFalse(testToDoTask.isDue());
    }
}
