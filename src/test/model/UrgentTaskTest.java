package model;

import model.exceptions.AlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UrgentTaskTest {
    private UrgentTask ut;
    private ToDoList tdl1;
    private ToDoList tdl2;

    @BeforeEach
    public void setUp() {
        tdl1 = new ToDoList("tdl1");
        tdl2 = new ToDoList("tdl2");
        ut = new UrgentTask("ut", tdl1);
    }

    @Test
    public void testConstructor() {
        assertEquals(UrgencyLevel.I, ut.getLevel());
        assertEquals("ut", ut.getContent());
        assertEquals(tdl1, ut.getTheToDoList());
        assertFalse(ut.isDue());
        assertFalse(ut.isDone());
    }

    @Test
    public void testSetTheToDoList() {
        ut.setTheToDoList(tdl1);

        try {
            tdl2.addUrgentTask(ut);
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        }
        ut.setTheToDoList(tdl2);
        assertEquals(tdl2, ut.getTheToDoList());

        ut.setTheToDoList(null);
        assertNull(ut.getTheToDoList());
    }

    @Test
    public void testSetLevel() {
        ut.setLevel(UrgencyLevel.II);
        assertEquals(UrgencyLevel.II, ut.getLevel());
    }

    @Test
    public void testSetDate() {
        Date d = Calendar.getInstance().getTime();
        ut.setDueTime(d);
        assertEquals(d, ut.getDueTime());
    }

    @Test
    public void testSetContent() {
        ut.setContent("ut1");
        assertEquals("ut1", ut.getContent());
    }

    @Test
    public void testSetDue() {
        ut.setDue(true);
        assertTrue(ut.isDue());
    }

    @Test
    public void testSetDone() {
        ut.setDone(true);
        assertTrue(ut.isDone());
    }

    @Test
    public void testToString() {
        assertEquals("Urgent Task {"
                + "Urgency Level='" + ut.getLevel() + '\''
                + ", Content=" + ut.getContent()
                + ", Due Time='" + ut.getDueTime() + '\''
                + ", Due?='" + "false" + '\''
                + ", Done?= " + "false"
                + '}', ut.toString());
    }

    @Test
    public void testEqualsHashCode() {
        assertEquals(ut.hashCode(), ut.hashCode());
        UrgentTask ut1 = new UrgentTask("ut", tdl1);
        assertTrue(ut.equals(ut));
        assertFalse(ut.equals(null));
        assertFalse(ut.equals(0));
        ut1.setDue(true);
        assertFalse(ut.equals(ut1));
        ut1.setDue(false);
        ut1.setDone(true);
        assertFalse(ut.equals(ut1));
    }
}
