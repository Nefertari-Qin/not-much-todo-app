package tests;

import model.ImportantTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImportantTaskTest {

    private ImportantTask testImportantTask;
    private String taskName = "importantTaskName";
    private String creatorName = "YQ";
    private String dueDate = "2000-08-18";

    @BeforeEach
    public void runBefore() {
        testImportantTask = new ImportantTask(taskName, creatorName, dueDate);
    }

    @Test
    public void testConstructor() {
        assertEquals(taskName, testImportantTask.getTask());
        assertEquals(creatorName, testImportantTask.getCreator());
        assertEquals(dueDate, testImportantTask.getDueDate());
        assertFalse(testImportantTask.isCompleted());
    }

    @Test
    public void testSetTask() {
        testImportantTask.setTask("anotherName");
        assertEquals("anotherName", testImportantTask.getTask());
    }

    @Test
    public void testSetCreator() {
        testImportantTask.setCreator("NG");
        assertEquals("NG", testImportantTask.getCreator());
    }

    @Test
    public void testSetDueDateValidFormat() {
        assertTrue(testImportantTask.setDueDate("2019-09-22"));
        assertEquals("2019-09-22", testImportantTask.getDueDate());
    }

    @Test
    public void testSetDueDateInvalidFormat() {
        assertFalse(testImportantTask.setDueDate("20190922"));
    }

    @Test
    public void testMarkAsCompleted() {
        testImportantTask.markCompleted();
        assertTrue(testImportantTask.isCompleted());
    }

    @Test
    public void testMarkAsUncompleted() {
        testImportantTask.markCompleted();
        assertTrue(testImportantTask.isCompleted());
        testImportantTask.markUncompleted();
        assertFalse(testImportantTask.isCompleted());
    }

    @Test
    public void testIsDueBeforeDue() {
        assertTrue(testImportantTask.isDue());
    }

    @Test
    public void testIsDueAtDue() {
        testImportantTask.setDueDate("2019-09-22");
        assertTrue(testImportantTask.isDue());
    }

    @Test
    public void testIsDueAfterDue() {
        testImportantTask.setDueDate("2019-12-22");
        assertFalse(testImportantTask.isDue());
    }


}
