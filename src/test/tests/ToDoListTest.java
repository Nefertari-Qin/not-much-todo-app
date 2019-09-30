package tests;

import model.NormalTask;
import model.ToDoList;
import model.ToDoTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoListTest {
    private ToDoList testToDoList;
    private ToDoTask testTask1;
    private ToDoTask testTask2;
    private ToDoTask testTask3;
    private String testName = "TestName";
    private String otherName = "OtherName";

    @BeforeEach
    public void runBefore() {
        testToDoList = new ToDoList(testName);
        testTask1 = new NormalTask("ntt1");
        testTask2 = new NormalTask("ntt2");
        testTask3 = new NormalTask("ntt3");
        testToDoList.addTask(testTask1);
        testToDoList.addTask(testTask2);
        testToDoList.addTask(testTask3);
    }

    @Test
    public void testConstructor() {
        assertEquals(testName, testToDoList.getToDoListName());
    }

    @Test
    public void testSetToDoTask() {
        List<ToDoTask> tasks = new ArrayList<>();
        tasks.add(testTask1);
        testToDoList.setToDoTasks(tasks);
        assertEquals(tasks, testToDoList.getToDoTasks());
    }

    @Test
    public void testSetToDoListName() {
        testToDoList.setToDoListName(otherName);
        assertEquals(otherName, testToDoList.getToDoListName());
    }

    @Test
    public void testGetTask() {
        assertEquals(testTask3, testToDoList.getTask("ntt3"));
        assertEquals(null, testToDoList.getTask("whatever"));
    }

    @Test
    public void testAddTask() {
        testToDoList.removeTask(testTask3);
        assertTrue(testToDoList.containTask(testTask1));
        assertTrue(testToDoList.containTask(testTask2));
        assertFalse(testToDoList.containTask(testTask3));
    }

    @Test
    public void testRemoveTask() {
        testToDoList.removeTask(testTask2);
        assertTrue(testToDoList.containTask(testTask1));
        assertFalse(testToDoList.containTask(testTask2));
        assertTrue(testToDoList.containTask(testTask3));
    }

    @Test
    public void testCountTasks() {
        testToDoList.addTask(testTask1);
        testToDoList.addTask(testTask2);
        assertEquals(5,testToDoList.countTasks());
    }

    @Test
    public void testDeleteCompleteOneTask() {
        testTask1.markCompleted();
        assertEquals(1, testToDoList.deleteCompleted());
        assertFalse(testToDoList.containTask(testTask1));
        assertTrue(testToDoList.containTask(testTask2));
        assertTrue(testToDoList.containTask(testTask3));
    }

    @Test
    public void testDeletedCompletedMultipleTasks() {
        testTask1.markCompleted();
        testTask2.markCompleted();
        testTask3.markCompleted();
        assertEquals(3, testToDoList.deleteCompleted());
        assertFalse(testToDoList.containTask(testTask1));
        assertFalse(testToDoList.containTask(testTask2));
        assertFalse(testToDoList.containTask(testTask3));
    }
}
