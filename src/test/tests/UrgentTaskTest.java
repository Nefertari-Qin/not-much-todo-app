package tests;

import model.UrgentTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UrgentTaskTest {

    private UrgentTask testUrgentTask;
    private String taskName = "urgentTaskName";
    private String creatorName = "YQ";
    private String dueDate = "2000-08-18";

    @BeforeEach
    public void runBefore() {
        testUrgentTask = new UrgentTask(taskName, creatorName, dueDate);
    }

    @Test
    public void testConstructor() {
        assertEquals(taskName, testUrgentTask.getTask());
        assertEquals(creatorName, testUrgentTask.getCreator());
        assertEquals(dueDate, testUrgentTask.getDueDate());
        assertFalse(testUrgentTask.isCompleted());
    }

    @Test
    public void testSetTask() {
        testUrgentTask.setTask("anotherName");
        assertEquals("anotherName", testUrgentTask.getTask());
    }

    @Test
    public void testSetCreator() {
        testUrgentTask.setCreator("NG");
        assertEquals("NG", testUrgentTask.getCreator());
    }

    @Test
    public void testSetDueDateValidFormat() {
        assertTrue(testUrgentTask.setDueDate("2019-09-22"));
        assertEquals("2019-09-22", testUrgentTask.getDueDate());
    }

    @Test
    public void testSetDueDateInvalidFormat() {
        assertFalse(testUrgentTask.setDueDate("20190922"));
    }

    @Test
    public void testSetIsCompleted() {
        testUrgentTask.setIsCompleted(true);
        assertTrue(testUrgentTask.isCompleted());
    }

    @Test
    public void testIsDueBeforeDue() {
        assertTrue(testUrgentTask.isDue());
    }

    @Test
    public void testIsDueAtDue() {
        testUrgentTask.setDueDate("2019-09-22");
        assertTrue(testUrgentTask.isDue());
    }

    @Test
    public void testIsDueAfterDue() {
        testUrgentTask.setDueDate("2019-12-22");
        assertFalse(testUrgentTask.isDue());
    }


}
