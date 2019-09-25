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
        testTask1 = new NormalTask("ntt1","c1", "2000-08-18");
        testTask2 = new NormalTask("ntt2","c2", "2000-07-19");
        testTask3 = new NormalTask("ntt3", "c3", "2019-09-25");
        testToDoList.addTask(testTask1);
        testToDoList.addTask(testTask2);
        testToDoList.addTask(testTask3);
    }

    @Test
    public void testConstructor() {
        assertEquals(testName, testToDoList.getToDoListName());
    }

    @Test
    public void testGetToDoTasks() {
        List<ToDoTask> tasksListGot = testToDoList.getToDoTasks();
        assertTrue(tasksListGot.contains(testTask1));
        assertTrue(tasksListGot.contains(testTask2));
        assertTrue(tasksListGot.contains(testTask3));
        assertEquals(3, tasksListGot.size());
    }

    @Test
    public void testSetToDoListName() {
        testToDoList.setToDoListName(otherName);
        assertEquals(otherName, testToDoList.getToDoListName());
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
