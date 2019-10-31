package model;

import model.exceptions.AlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RegularTaskTest {
    private RegularTask rt;
    private ToDoList tdl1;
    private ToDoList tdl2;

    @BeforeEach
    public void setUp() {
        tdl1 = new ToDoList("tdl1");
        tdl2 = new ToDoList("tdl2");
        rt = new RegularTask("rt");
    }

    @Test
    public void testConstructor() {
        assertEquals(UrgencyLevel.R, rt.getLevel());
        assertEquals("rt", rt.getContent());
        assertFalse(rt.isDue());
        assertFalse(rt.isDone());
    }

    @Test
    public void testSetDate() {
        Date d = Calendar.getInstance().getTime();
        rt.setDueTime(d);
        assertEquals(d, rt.getDueTime());
    }

    @Test
    public void testSetDue() {
        rt.setDue(true);
        assertTrue(rt.isDue());
    }

    @Test
    public void testSetDone() {
        rt.setDone(true);
        assertTrue(rt.isDone());
    }

    @Test
    public void testEquals() {
        assertTrue(rt.equals(rt));
        assertFalse(rt.equals(null));
        assertFalse(rt.equals(0));
        assertTrue(rt.equals(new RegularTask("rt")));
        RegularTask rt2 = new RegularTask("rt");
        assertTrue(rt.equals(rt2));
        rt2.setDone(true);
        assertFalse(rt.equals(rt2));
        rt2.setDone(false);
        rt2.setDue(true);
        assertFalse(rt.equals(rt2));
    }
}

