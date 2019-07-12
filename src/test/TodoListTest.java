package test;

import model.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TodoListTest {
    TodoList t;

    @BeforeEach
    public void init(){
        t = new TodoList();
        t.addTodo("CPSC 210", "Wednesday");
    }

    @Test
    public void testaddOneTodo(){
        assertEquals(t.getTodoName(0), "CPSC 210");
        assertEquals(t.getTodoDue(0), "Wednesday");
        assertFalse(t.getTodoStatus(0));
    }

    @Test
    public void testaddTwoTodo(){
        t.addTodo("CPSC 121", "Friday");

        assertEquals(t.getTodoName(0), "CPSC 210");
        assertEquals(t.getTodoDue(0), "Wednesday");
        assertFalse(t.getTodoStatus(0));

        assertEquals(t.getTodoName(1), "CPSC 121");
        assertEquals(t.getTodoDue(1), "Friday");
        assertFalse(t.getTodoStatus(1));
    }

    @Test
    public void testDeleteTodo(){
        t.addTodo("CPSC 121", "Friday");
        t.deleteTodo(0);
        assertEquals(t.size(), 1);
        assertEquals(t.getTodoName(0), "CPSC 121");
        assertEquals(t.getTodoDue(0), "Friday");
        assertFalse(t.getTodoStatus(0));
    }

    public void testEditTodo(){
        t.changeName(0,"CPSC 210 Done");
        t.changeStatus(0,1);
        t.changeDue(0,"Friday");
        assertEquals(t.getTodoName(0), "CPSC 210 Done");
        assertEquals(t.getTodoDue(0), "Friday");
        assertTrue(t.getTodoStatus(0));

    }
}
