package tests;

import model.ImportantTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class ImportantTaskTest {
    private ImportantTask testImportant;
    private String tc1 = "content1";
    private String tc2 = "content2";

    @BeforeEach
    public void runBefore() {
        testImportant = new ImportantTask(tc1);
    }

    @Test
    public void testConstructor() {
        assertEquals(tc1, testImportant.getTaskContent());
        assertFalse(testImportant.isCompleted());
    }

    @Test
    public void testSetDueDate() {
        testImportant.setDueDate(2019, 8,18);
        assertEquals(2019,testImportant.getDueDate().get(Calendar.YEAR));
        assertEquals(8,testImportant.getDueDate().get(Calendar.MONTH));
        assertEquals(18,testImportant.getDueDate().get(Calendar.DATE));
    }

    @Test
    public void testGetFormattedDueDate() {
        testImportant.setDueDate(2019,8,18);
        assertEquals("2019-08-18", testImportant.getFormattedStringDueDate());
    }

    @Test
    public void testIsDue() {
        assertFalse(testImportant.isDue());
    }

    @Test
    public void testSetTaskContent() {
        testImportant.setTaskContent(tc2);
        assertEquals(tc2, testImportant.getTaskContent());
    }

    @Test
    public void testMarkAsCompleted() {
        testImportant.markCompleted();
        assertTrue(testImportant.isCompleted());
    }

    @Test
    public void testMarkAsUncompleted() {
        testImportant.markCompleted();
        assertTrue(testImportant.isCompleted());
        testImportant.markUncompleted();
        assertFalse(testImportant.isCompleted());
    }
}
