package tests;

import model.NormalTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static model.ImportanceLevel.NORMAL;
import static org.junit.jupiter.api.Assertions.*;

public class NormalTaskTest {
    private NormalTask testNormal;
    private String tc1 = "content1";
    private String tc2 = "content2";

    @BeforeEach
    public void runBefore() {
        testNormal = new NormalTask(tc1);
    }

    @Test
    public void testConstructor() {
        assertEquals(tc1, testNormal.getTaskContent());
        assertFalse(testNormal.isCompleted());
        assertEquals(NORMAL, testNormal.getImportanceLevel());
    }

    @Test
    public void testSetDueDate() {
        testNormal.setDueDate(2019,9,30);
        assertEquals(2019, testNormal.getDueDate().get(Calendar.YEAR));
        assertEquals(9, testNormal.getDueDate().get(Calendar.MONTH));
        assertEquals(30, testNormal.getDueDate().get(Calendar.DATE));
    }

    @Test
    public void testGetFormattedDueDate() {
        testNormal.setDueDate(2019,9,30);
        assertEquals("2019-09-30", testNormal.getFormattedStringDueDate());
    }

    @Test
    public void testIsDue() {
        assertFalse(testNormal.isDue());
    }

    @Test
    public void testSetTaskContent() {
        testNormal.setTaskContent(tc2);
        assertEquals(tc2, testNormal.getTaskContent());
    }

    @Test
    public void testMarkAsCompleted() {
        testNormal.markCompleted();
        assertTrue(testNormal.isCompleted());
    }

    @Test
    public void testMarkAsUncompleted() {
        testNormal.markCompleted();
        assertTrue(testNormal.isCompleted());
        testNormal.markUncompleted();
        assertFalse(testNormal.isCompleted());
    }

    @Test
    public void testUpgradeLevel() {
        testNormal.upgradeImpLevel();
        assertEquals(NORMAL, testNormal.getImportanceLevel());
    }
}
