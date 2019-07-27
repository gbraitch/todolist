package test;

import model.exception.NegativeListIndexException;
import model.TodoList;
import model.exception.TooLargeListIndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TodoListTest {
    TodoList todo;

    @BeforeEach
    public void init() {
        todo = new TodoList();
        todo.addRegTodo("CPSC 210", "Wednesday");
    }

    @Test
    public void testaddOneTodo() throws TooLargeListIndexException, NegativeListIndexException {
        assertEquals(todo.getTodoName(0), "CPSC 210");
        assertEquals(todo.getTodoDue(0), "Wednesday");
        assertFalse(todo.getTodoStatus(0));
    }

    @Test
    public void testaddTwoTodo() throws TooLargeListIndexException, NegativeListIndexException {
        todo.addRegTodo("CPSC 121", "Friday");

        assertEquals(todo.getTodoName(0), "CPSC 210");
        assertEquals(todo.getTodoDue(0), "Wednesday");
        assertFalse(todo.getTodoStatus(0));

        assertEquals(todo.getTodoName(1), "CPSC 121");
        assertEquals(todo.getTodoDue(1), "Friday");
        assertFalse(todo.getTodoStatus(1));
    }

    @Test
    public void testDeleteTodo() throws TooLargeListIndexException, NegativeListIndexException {
        todo.addRegTodo("CPSC 121", "Friday");
        try {
            todo.deleteTodo(0);
        } catch (NegativeListIndexException e) {
            e.printStackTrace();
        } catch (TooLargeListIndexException e) {
            e.printStackTrace();
        }
        assertEquals(todo.size(), 1);
        assertEquals(todo.getTodoName(0), "CPSC 121");
        assertEquals(todo.getTodoDue(0), "Friday");
        assertFalse(todo.getTodoStatus(0));
    }

    @Test
    public void testEditTodo() throws TooLargeListIndexException, NegativeListIndexException {
        todo.changeName(0,"CPSC 210 Done");
        todo.changeStatus(0,1);
        todo.changeDue(0,"Friday");
        assertEquals(todo.getTodoName(0), "CPSC 210 Done");
        assertEquals(todo.getTodoDue(0), "Friday");
        assertTrue(todo.getTodoStatus(0));
    }

    @Test
    public void testSave() throws TooLargeListIndexException, NegativeListIndexException {
        try {
            todo.save("saveTest.JSON");
        } catch (IOException e) {
            fail();
        }
        TodoList tt = new TodoList();
        try {
            tt.load("saveTest.JSON");
        } catch (IOException e) {
            fail();
        }
        assertEquals(tt.getTodoName(0), "CPSC 210");
        assertEquals(tt.getTodoDue(0), "Wednesday");
        assertFalse(tt.getTodoStatus(0));
    }

    @Test
    public void testLoad() throws TooLargeListIndexException, NegativeListIndexException {
        try {
            todo.load("loadTest.JSON");
        } catch (IOException e) {
            fail();
        }

        assertEquals(todo.getTodoName(0), "CPSC 210");
        assertEquals(todo.getTodoDue(0), "WED");
        assertTrue(todo.getTodoStatus(0));

        assertEquals(todo.getTodoName(1),
                "CPSC 121 URGENT");
        assertEquals(todo.getTodoDue(1), "FRI");
        assertFalse(todo.getTodoStatus(1));

        assertEquals(todo.getTodoName(2), "CPSC310");
        assertEquals(todo.getTodoDue(2), "THURS");
        assertTrue(todo.getTodoStatus(2));

        assertEquals(todo.getTodoName(3),
                "CPSC 221 ONLY TWO THINGS");
        assertEquals(todo.getTodoDue(3), "SAT");
        assertFalse(todo.getTodoStatus(3));

        assertEquals(todo.getTodoName(4),
                "FINISH LAUNDRY");
        assertEquals(todo.getTodoDue(4), "MON");
        assertTrue(todo.getTodoStatus(4));

    }
}
