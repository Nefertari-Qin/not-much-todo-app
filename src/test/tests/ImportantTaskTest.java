package tests;

import model.ImportantTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static model.ImportanceLevel.HIGH;
import static model.ImportanceLevel.MID;
import static org.junit.jupiter.api.Assertions.*;

public class ImportantTaskTest {
    private ImportantTask testImportant;
    private String oc1 = "content1";
    private String lc1 = "! content1 !";
    private String mc1 = "!! content1 !!";
    private String hc1 = "!!! content1 !!!";
    private String oc2 = "content2";
    private String lc2 = "! content2 !";
    private String mc2 = "!! content2 !!";
    private String hc2 = "!!! content2 !!!";

    @BeforeEach
    public void runBefore() {
        testImportant = new ImportantTask(oc1);
    }

    @Test
    public void testConstructor() {
        assertEquals(lc1, testImportant.getTaskContent());
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
    public void testSetImportanceLevel() {
        testImportant.setImportanceLevel(HIGH);
        assertEquals(hc1, testImportant.getTaskContent());
        testImportant.setImportanceLevel(MID);
        assertEquals(mc1, testImportant.getTaskContent());
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
        testImportant.setTaskContent(oc2);
        assertEquals(lc2, testImportant.getTaskContent());
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

    @Test
    public void testUpgradeLevel() {
        testImportant.upgradeImpLevel();
        assertEquals(MID, testImportant.getImportanceLevel());
        testImportant.upgradeImpLevel();
        assertEquals(HIGH, testImportant.getImportanceLevel());
        testImportant.upgradeImpLevel();
        assertEquals(HIGH, testImportant.getImportanceLevel());
    }
}
