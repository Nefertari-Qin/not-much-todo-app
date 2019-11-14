package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActionHistoryTest {

    private Action a1;
    private Action a2;
    private Action a3;

    @BeforeEach
    public void setUp() {
        a1 = new Action("A1");
        a2 = new Action("A2");
        a3 = new Action("A3");
        ActionHistory ah = ActionHistory.getInstance();
        ah.recordAction(a1);
        ah.recordAction(a2);
        ah.recordAction(a3);
    }

    @Test
    public void testRecordAction() {
        List<Action> l = new ArrayList<Action>();
        ActionHistory ah = ActionHistory.getInstance();
        for (Action next : ah) {
            l.add(next);
        }

        assertTrue(l.contains(a1));
        assertTrue(l.contains(a2));
        assertTrue(l.contains(a3));
    }

    @Test
    public void testClear() {
        ActionHistory ah = ActionHistory.getInstance();
        ah.clear();
        Iterator<Action> itr = ah.iterator();
        assertTrue(itr.hasNext());   // After log is cleared, the clear log event is added
        assertEquals("All Action History cleared.", itr.next().getContent());
        assertFalse(itr.hasNext());
    }


}
