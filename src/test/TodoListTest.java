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
    }

    @Test
    public void testaddOneTodo(){
        t.addTodo("CPSC 210", "Wednesday");
        assertEquals(t.getTodoName(0), "CPSC 210");
        assertEquals(t.getTodoDue(0), "Wednesday");
        assertFalse(t.getTodoStatus(0));
    }

    public void testaddTwoTodo(){
        t.addTodo("CPSC 210", "Wednesday");
        t.addTodo("CPSC 121", "Friday");

        assertEquals(t.getTodoName(0), "CPSC 210");
        assertEquals(t.getTodoDue(0), "Wednesday");
        assertFalse(t.getTodoStatus(0));

        assertEquals(t.getTodoName(1), "CPSC 121");
        assertEquals(t.getTodoDue(1), "Friday");
        assertFalse(t.getTodoStatus(1));

    }
}
