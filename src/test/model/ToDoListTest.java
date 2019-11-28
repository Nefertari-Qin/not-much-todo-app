package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoListTest {
    private ToDoList testToDoList;
    private ToDoTask testToDoTask;
    private String name = "test list";

    @BeforeEach
    public void runBefore() {
        testToDoList = new ToDoList(name);
        testToDoTask = new ToDoTask("1", "1","1","1");
    }

    @Test
    public void testConstructor() {
        assertEquals(name, testToDoList.getName());
        assertEquals(0, testToDoList.size());
    }

    @Test
    public void testSetName() {
        testToDoList.setName(name + "1");
        assertEquals(name +  "1", testToDoList.getName());
    }

    @Test
    public void testAddToDoTask() {
        testToDoList.addToDoTask(testToDoTask);
        assertEquals(1, testToDoList.size());
        assertTrue(testToDoList.contains(testToDoTask));
    }

    @Test
    public void testHashEqualsIterator() {
        assertEquals(testToDoList.hashCode(), new ToDoList(name).hashCode());
        assertTrue(testToDoList.equals(testToDoList));
        assertFalse(testToDoList.equals(null));
        assertFalse(testToDoList.equals(testToDoTask));
        assertTrue(testToDoList.equals(new ToDoList(name)));
        assertNotEquals(testToDoList.iterator(), new ToDoList(name).iterator());
    }
}
