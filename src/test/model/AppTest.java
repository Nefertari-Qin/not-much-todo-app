package model;

import model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    private App testApp;
    private ToDoList tl1;
    private ToDoList tl2;

    @BeforeEach
    public void setUp() {
        testApp = new App();
        tl1 = new ToDoList("tl1");
        tl2 = new ToDoList("tl2");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testApp.countList());
        assertNull(testApp.getListCurrentIn());
    }

    @Test
    public void testAddToDoListThrowException() {
        try {
            testApp.addToDoList(tl1);
            testApp.addToDoList(tl1);
            fail("Expect AlreadyExistException.");
        } catch (AlreadyExistException e) {
            System.out.println("testAddToDoListThrowException passed!");
        }
        assertEquals(1, testApp.countList());
        assertTrue(testApp.containList(tl1));
    }

    @Test
    public void testAddToDoListOnceNoException() {
        try {
            testApp.addToDoList(tl1);

            assertEquals(1, testApp.countList());
            assertTrue(testApp.containList(tl1));
        } catch (AlreadyExistException e) {
            fail("Don't expect AlreadyExistException.");
        }
    }

    @Test
    public void testAddToDoListTwiceNoException() {
        try {
            testApp.addToDoList(tl1);
            testApp.addToDoList(tl2);

            assertEquals(2, testApp.countList());
            assertTrue(testApp.containList(tl1));
            assertTrue(testApp.containList(tl2));
        } catch (AlreadyExistException e) {
            fail("Don't expect ListAlreadyExistException.");
        }
    }

    @Test
    public void testRemoveToDoListThrowException() {
        try {
            testApp.removeToDoList("tl1");
            fail("Expect DoesntExistException.");
        } catch (DoesntExistException e) {
            System.out.println("testRemoveToDoListThrowException passed!");
        }
        assertEquals(0, testApp.countList());
        assertFalse(testApp.containList(tl1));
    }

    @Test
    public void testRemoveToDoListOnceNoException() {
        try {
            testApp.addToDoList(tl1);
            assertEquals(1, testApp.countList());
            assertTrue(testApp.containList(tl1));
            testApp.removeToDoList("tl1");

            assertEquals(0, testApp.countList());
            assertFalse(testApp.containList(tl1));
        } catch (AlreadyExistException e) {
            fail("Don't expect AlreadyExistException.");
        } catch (DoesntExistException e) {
            fail("Don't expect DoesntExistException.");
        }
    }

    @Test
    public void testRemoveToDoListTwiceNoException() {
        try {
            testApp.addToDoList(tl1);
            testApp.addToDoList(tl2);
            assertEquals(2, testApp.countList());
            assertTrue(testApp.containList(tl1));
            assertTrue(testApp.containList(tl2));
            testApp.removeToDoList("tl1");
            testApp.removeToDoList("tl2");

            assertEquals(0, testApp.countList());
            assertFalse(testApp.containList(tl1));
            assertFalse(testApp.containList(tl2));
        } catch (AlreadyExistException e) {
            fail("Don't expect ListAlreadyExistException.");
        } catch (DoesntExistException e) {
            fail("Don't expect ListDoesntExistException.");
        }
    }

    @Test
    public void testGetToDoListThrowException() {
        try {
            assertEquals(0, testApp.countList());
            assertFalse(testApp.containList(tl1));

            testApp.getToDoList("tl1");
            fail("Expect ListDoesntExistException.");
        } catch (DoesntExistException e) {
            System.out.println("testGetToDoListThrowException passed!");
        }

    }

    @Test
    public void testGetToDoListOnceNoException() {
        try {
            testApp.addToDoList(tl1);
            assertEquals(1, testApp.countList());
            assertTrue(testApp.containList(tl1));

            assertEquals(tl1, testApp.getToDoList("tl1"));

            assertEquals(1, testApp.countList());
            assertTrue(testApp.containList(tl1));
        } catch (AlreadyExistException e) {
            fail("Don't expect ListAlreadyExistException.");
        } catch (DoesntExistException e) {
            fail("Don't expect ListDoesntExistException.");
        }
    }

    @Test
    public void testGetToDoListTwiceNoException() {
        try {
            testApp.addToDoList(tl1);
            testApp.addToDoList(tl2);
            assertEquals(2, testApp.countList());
            assertTrue(testApp.containList(tl1));
            assertTrue(testApp.containList(tl2));

            assertEquals(tl1, testApp.getToDoList("tl1"));
            assertEquals(tl2, testApp.getToDoList("tl2"));

            assertEquals(2, testApp.countList());
            assertTrue(testApp.containList(tl1));
            assertTrue(testApp.containList(tl2));
        } catch (AlreadyExistException e) {
            fail("Don't expect ListAlreadyExistException.");
        } catch (DoesntExistException e) {
            fail("Don't expect ListDoesntExistException.");
        }
    }

    @Test
    public void testCountToDoListZero() {
        assertEquals(0, testApp.countList());
    }

    @Test
    public void testCountToDoListNotZero() {
        try {
            testApp.addToDoList(tl1);
            testApp.addToDoList(tl2);

            assertEquals(2, testApp.countList());
        } catch (AlreadyExistException e) {
            fail("Don't expect ListAlreadyExistException.");
        }
    }

    @Test
    public void testEnterToDoListThrowDoesntExistException() {
        try {
            testApp.enterToDoList("tl1");
            fail("Expect ListDoesntExistException.");
        } catch (DoesntExistException e) {
            System.out.println("testEnterToDoListThrowException passed!");
        }
        assertNull(testApp.getListCurrentIn());
    }

    @Test
    public void testEnterToDoListThrowInsideListException() {
        try {
            testApp.addToDoList(tl1);
            testApp.addToDoList(tl2);
            testApp.enterToDoList("tl2");

            testApp.enterToDoList("tl1");
        } catch (DoesntExistException e) {
            fail("Don't expect ListDoesntExistException");
        } catch (AlreadyExistException e) {
            fail("Don't expect ListAlreadyExistException");
        }
        assertEquals(tl1, testApp.getListCurrentIn());
    }

    @Test
    public void testEnterToDoListNoException() {
        try {
            testApp.addToDoList(tl1);
            testApp.addToDoList(tl2);

            testApp.enterToDoList("tl2");

            assertEquals(tl2, testApp.getListCurrentIn());
        } catch (AlreadyExistException e) {
            fail("Don't expect ListAlreadyExistException.");
        } catch (DoesntExistException e) {
            fail("Don't expect ListDoesntExistException.");
        }
    }

    @Test
    public void testExitToDoListOut() {
        testApp.exitToDoList();
        assertNull(testApp.getListCurrentIn());
    }

    @Test
    public void testExitToDoListIn() {
        try {
            testApp.addToDoList(tl1);
            testApp.addToDoList(tl2);
            testApp.enterToDoList("tl2");
            assertEquals(tl2, testApp.getListCurrentIn());

            testApp.exitToDoList();
            assertNull(testApp.getListCurrentIn());
        } catch (AlreadyExistException e) {
            fail("Don't expect ListAlreadyExistException.");
        } catch (DoesntExistException e) {
            fail("Don't expect ListDoesntExistException.");
        }
    }

    @Test
    public void testEditCurrentToDoListNameThrowOutsideException() {
        try {
            testApp.addToDoList(tl1);
            testApp.addToDoList(tl2);

            testApp.editCurrentToDoListName("edited-tl2");

            assertEquals("tl2", testApp.getListCurrentIn().getName());
        } catch (AlreadyExistException e) {
            fail("Don't expect ListAlreadyExistException.");
        } catch (SameNameException e) {
            fail("Don't expect SameNameException.");
        } catch (OutsideListException e) {
            System.out.println("testEditCurrentToDoListNameThrowOutsideException passed!");
        }
    }

    @Test
    public void testEditCurrentToDoListNameThrowNameException() {
        try {
            testApp.addToDoList(tl1);
            testApp.addToDoList(tl2);
            testApp.enterToDoList("tl2");

            testApp.editCurrentToDoListName("tl2");
        } catch (AlreadyExistException e) {
            fail("Don't expect ListAlreadyExistException.");
        } catch (OutsideListException e) {
            fail("Don't expect OutsideListException.");
        } catch (DoesntExistException e) {
            fail("Don't expect ListDoesntExistException.");
        } catch (SameNameException e) {
            System.out.println("testEditCurrentToDoListNameThrowNameException passed!");
        }
    }

    @Test
    public void testEditCurrentToDoListNameNoException() {
        try {
            testApp.addToDoList(tl1);
            testApp.addToDoList(tl2);
            testApp.enterToDoList("tl2");

            testApp.editCurrentToDoListName("edited-tl2");
            assertEquals("edited-tl2", testApp.getListCurrentIn().getName());
        } catch (AlreadyExistException e) {
            fail("Don't expect ListAlreadyExistException.");
        } catch (OutsideListException e) {
            fail("Don't expect OutsideListException.");
        } catch (DoesntExistException e) {
            fail("Don't expect ListDoesntExistException.");
        } catch (SameNameException e) {
            System.out.println("testEditCurrentToDoListNameThrowNameException passed!");
        }
    }
}
