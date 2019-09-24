package tests;

import model.RegularTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegularTaskTest {
    private RegularTask testRegularTask;
    private String taskName = "regularTaskName";
    private String creatorName = "YQ";
    private String dueDate = "2000-08-18";


    @BeforeEach
    public void runBefore() {
        testRegularTask = new RegularTask(taskName, creatorName, dueDate);
    }

    @Test
    public void testConstructor() {
        assertEquals(taskName, testRegularTask.getTask());
        assertEquals(creatorName, testRegularTask.getCreator());
        assertEquals(dueDate, testRegularTask.getDueDate());
        assertFalse(testRegularTask.isCompleted());
    }

    @Test
    public void testSetTask() {
        testRegularTask.setTask("anotherName");
        assertEquals("anotherName", testRegularTask.getTask());
    }

    @Test
    public void testSetCreator() {
        testRegularTask.setCreator("NG");
        assertEquals("NG", testRegularTask.getCreator());
    }

    @Test
    public void testSetDueDateValidFormat(){
        assertTrue(testRegularTask.setDueDate("2019-09-22"));
        assertEquals("2019-09-22", testRegularTask.getDueDate());
    }

    @Test
    public void testSetDueDateInvalidFormat(){
        assertFalse(testRegularTask.setDueDate("20190922"));
    }

    @Test
    public void testSetIsCompleted() {
        testRegularTask.setIsCompleted(true);
        assertTrue(testRegularTask.isCompleted());
    }

    @Test
    public void testIsDueBeforeDue() {
        assertTrue(testRegularTask.isDue());
    }

    @Test
    public void testIsDueAtDue() {
        testRegularTask.setDueDate("2019-09-22");
        assertTrue(testRegularTask.isDue());
    }

    @Test
    public void testIsDueAfterDue() {
        testRegularTask.setDueDate("2019-12-22");
        assertFalse(testRegularTask.isDue());
    }
}
