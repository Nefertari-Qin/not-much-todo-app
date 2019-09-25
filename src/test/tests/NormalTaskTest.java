package tests;

import model.NormalTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NormalTaskTest {
    private NormalTask testNormalTask;
    private String taskName = "normalTaskName";
    private String creatorName = "NQ";
    private String dueDate = "2000-08-18";


    @BeforeEach
    public void runBefore() {
        testNormalTask = new NormalTask(taskName, creatorName, dueDate);
    }

    @Test
    public void testConstructor() {
        assertEquals(taskName, testNormalTask.getTask());
        assertEquals(creatorName, testNormalTask.getCreator());
        assertEquals(dueDate, testNormalTask.getDueDate());
        assertFalse(testNormalTask.isCompleted());
    }

    @Test
    public void testSetTask() {
        assertEquals(taskName, testNormalTask.getTask());
        testNormalTask.setTask("anotherName");
        assertEquals("anotherName", testNormalTask.getTask());
    }

    @Test
    public void testSetCreator() {
        assertEquals(creatorName, testNormalTask.getCreator());
        testNormalTask.setCreator("NG");
        assertEquals("NG", testNormalTask.getCreator());
    }

    @Test
    public void testSetDueDateValidFormat(){
        assertEquals(dueDate, testNormalTask.getDueDate());
        assertTrue(testNormalTask.setDueDate("2019-09-22"));
        assertEquals("2019-09-22", testNormalTask.getDueDate());
    }

    @Test
    public void testSetDueDateInvalidFormat(){
        assertEquals(dueDate, testNormalTask.getDueDate());
        assertFalse(testNormalTask.setDueDate("20190922"));
    }

    @Test
    public void testMarkAsCompleted() {
        testNormalTask.markCompleted();
        assertTrue(testNormalTask.isCompleted());
    }

    @Test
    public void testMarkAsUncompleted() {
        testNormalTask.markCompleted();
        assertTrue(testNormalTask.isCompleted());
        testNormalTask.markUncompleted();
        assertFalse(testNormalTask.isCompleted());
    }

    @Test
    public void testIsDueBeforeDue() {
        assertTrue(testNormalTask.isDue());
    }

    @Test
    public void testIsDueAtDue() {
        testNormalTask.setDueDate("2019-09-22");
        assertTrue(testNormalTask.isDue());
    }

    @Test
    public void testIsDueAfterDue() {
        testNormalTask.setDueDate("2019-12-22");
        assertFalse(testNormalTask.isDue());
    }
}
