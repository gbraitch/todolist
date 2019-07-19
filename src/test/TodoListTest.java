package test;

import model.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TodoListTest {
    TodoList t;

    @BeforeEach
    public void init() {
        t = new TodoList();
        t.addRegTodo("CPSC 210", "Wednesday");
    }

    @Test
    public void testaddOneTodo() {
        assertEquals(t.getTodoName(0), "CPSC 210");
        assertEquals(t.getTodoDue(0), "Wednesday");
        assertFalse(t.getTodoStatus(0));
    }

    @Test
    public void testaddTwoTodo() {
        t.addRegTodo("CPSC 121", "Friday");

        assertEquals(t.getTodoName(0), "CPSC 210");
        assertEquals(t.getTodoDue(0), "Wednesday");
        assertFalse(t.getTodoStatus(0));

        assertEquals(t.getTodoName(1), "CPSC 121");
        assertEquals(t.getTodoDue(1), "Friday");
        assertFalse(t.getTodoStatus(1));
    }

    @Test
    public void testDeleteTodo() {
        t.addRegTodo("CPSC 121", "Friday");
        t.deleteTodo(0);
        assertEquals(t.size(), 1);
        assertEquals(t.getTodoName(0), "CPSC 121");
        assertEquals(t.getTodoDue(0), "Friday");
        assertFalse(t.getTodoStatus(0));
    }

    @Test
    public void testEditTodo() {
        t.changeName(0,"CPSC 210 Done");
        t.changeStatus(0,1);
        t.changeDue(0,"Friday");
        assertEquals(t.getTodoName(0), "CPSC 210 Done");
        assertEquals(t.getTodoDue(0), "Friday");
        assertTrue(t.getTodoStatus(0));
    }

    @Test
    public void testSave(){
        try {
            t.save();
        } catch (IOException e) {
            fail();
        }
        TodoList tt = new TodoList();
        try {
            tt.load();
        } catch (IOException e) {
            fail();
        }
        assertEquals(tt.getTodoName(0), "CPSC 210");
        assertEquals(tt.getTodoDue(0), "Wednesday");
        assertFalse(tt.getTodoStatus(0));
    }

    @Test
    public void testLoad(){
        try {
            t.load("loadTest.txt");
        } catch (IOException e) {
            fail();
        }

        assertEquals(t.getTodoName(0), "CPSC 210");
        assertEquals(t.getTodoDue(0), "WED");
        assertTrue(t.getTodoStatus(0));

        assertEquals(t.getTodoName(1), "CPSC 121 URGENT ");
        assertEquals(t.getTodoDue(1), "FRI");
        assertFalse(t.getTodoStatus(1));

        assertEquals(t.getTodoName(2), "CPSC310");
        assertEquals(t.getTodoDue(2), "THURS");
        assertTrue(t.getTodoStatus(2));

        assertEquals(t.getTodoName(3), "CPSC 221 ONLY TWO THINGS");
        assertEquals(t.getTodoDue(3), "SAT");
        assertFalse(t.getTodoStatus(3));

        assertEquals(t.getTodoName(4), "FINISH LAUNDRY");
        assertEquals(t.getTodoDue(4), "MON");
        assertTrue(t.getTodoStatus(4));

    }
}
