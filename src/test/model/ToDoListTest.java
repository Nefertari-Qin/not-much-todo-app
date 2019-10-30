package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoListTest {
    private ToDoList testTDList;
    private UrgentTask ut1;
    private RegularTask rt1;


    @BeforeEach
    public void setUp() {
        testTDList = new ToDoList("testTDList");
        ut1 = new UrgentTask("ut1", testTDList);
        rt1 = new RegularTask("rt1");
    }

    @Test
    public void testConstructor() {
        assertEquals("testTDList", testTDList.getName());
        assertEquals(0, testTDList.countRegularTasks());
        assertEquals(0, testTDList.countUrgentTasks());
        assertEquals(0, testTDList.countTotalTasks());
    }

    @Test
    public void testSetName() {
        testTDList.setName("newTestName");
        assertEquals("newTestName", testTDList.getName());
    }

    @Test
    public void testAddUrgentTaskNotThere() {
        try {
            testTDList.addUrgentTask(ut1);
            assertTrue(testTDList.getUrgentTasks().contains(ut1));
        } catch (AlreadyExistException e) {
            fail("Don't expect AlreadyExistException.");
        }
    }

    @Test
    public void testAddUrgentTaskAlreadyThere() {
        try {
            testTDList.addUrgentTask(ut1);
            testTDList.addUrgentTask(ut1);
            fail("Expect AlreadyExistException.");
        } catch (AlreadyExistException e) {
            System.out.println("testAddUrgentTaskAlreadyThere passed!");
        }
        assertTrue(testTDList.getUrgentTasks().contains(ut1));
        assertEquals(1, testTDList.countUrgentTasks());
        assertEquals(1, testTDList.countTotalTasks());
    }

    @Test
    public void testRemoveUrgentTaskNotThere() {
        try {
            testTDList.removeUrgentTask(ut1);
            fail("Expect DoesntExistException.");
        } catch (DoesntExistException e) {
            System.out.println("testRemoveUrgentTaskNotThere passed!");
        }
    }

    @Test
    public void testRemoveUrgentTaskAlreadyThere() {
        try {
            testTDList.addUrgentTask(ut1);
            assertTrue(testTDList.getUrgentTasks().contains(ut1));
            assertEquals(1, testTDList.countUrgentTasks());
            assertEquals(1, testTDList.countTotalTasks());

            testTDList.removeUrgentTask(ut1);
            assertFalse(testTDList.getUrgentTasks().contains(ut1));
            assertEquals(0, testTDList.countUrgentTasks());
            assertEquals(0, testTDList.countTotalTasks());
        } catch (AlreadyExistException e) {
            fail("Don't expect AlreadyExistException.");
        } catch (DoesntExistException e) {
            fail("Don't expect DoesntExistException.");
        }
    }

    @Test
    public void testAddRegularTaskNotThere() {
        testTDList.addRegularTask(rt1);
        assertTrue(testTDList.getRegularTasks().contains(rt1));
        assertEquals(1, testTDList.countRegularTasks());
        assertEquals(1, testTDList.countTotalTasks());
    }

    @Test
    public void testAddRegularTaskAlreadyThere() {
        testTDList.addRegularTask(rt1);
        assertTrue(testTDList.getRegularTasks().contains(rt1));
        assertEquals(1, testTDList.countRegularTasks());
        assertEquals(1, testTDList.countTotalTasks());

        testTDList.addRegularTask(rt1);
        assertTrue(testTDList.getRegularTasks().contains(rt1));
        assertEquals(1, testTDList.countRegularTasks());
        assertEquals(1, testTDList.countTotalTasks());
    }

    @Test
    public void testRemoveRegularTaskNotThere() {
        testTDList.removeRegularTask(rt1);
        assertEquals(0, testTDList.countRegularTasks());
        assertEquals(0, testTDList.countTotalTasks());
    }

    @Test
    public void testRemoveRegularTaskAlreadyThere() {
        testTDList.addRegularTask(rt1);
        testTDList.removeRegularTask(rt1);
        assertEquals(0, testTDList.countRegularTasks());
        assertEquals(0, testTDList.countTotalTasks());

    }

    @Test
    public void testEquals() {
        assertTrue(testTDList.equals(testTDList));
        assertFalse(testTDList.equals(null));
        assertFalse(testTDList.equals(0));
        assertTrue(testTDList.equals(new ToDoList("testTDList")));
    }
}
