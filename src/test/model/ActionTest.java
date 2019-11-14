package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ActionTest {
    private Action a1;
    private Action a2;
    private Date d;

    @BeforeEach
    public void setUp() {
        a1 = new Action("A");
        a2 = new Action("A");
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testConstructor() {
        assertEquals("A", a1.getContent());
        assertEquals(d, a1.getTimeOfAction());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "A", a1.toString());
    }

    @Test
    public void testEqualsHashCode() {
        assertEquals(a1.hashCode(), a1.hashCode());
        assertTrue(a1.equals(a1));
        assertFalse(a1.equals(null));
        assertFalse(a1.equals(0));
        assertFalse(a1.equals(new Action("B")));
    }
}
