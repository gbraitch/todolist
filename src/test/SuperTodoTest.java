import model.SubTodo;
import model.SuperTodo;
import model.exception.NegativeListIndexException;
import model.exception.TooLargeListIndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SuperTodoTest {
    SuperTodo todo;

    @BeforeEach
    public void init() {
        todo = new SuperTodo("CPSC 210 HWK", "Wednesday");
    }

    @Test
    public void testOneTodo() {
        assertEquals("CPSC 210 HWK", todo.getName());
        assertEquals("Wednesday", todo.getDue());
        assertFalse(todo.getStatus());
    }

    @Test
    public void testChangeName() {
        todo.setName("CPSC 210 Done");
        assertEquals(todo.getName(), "CPSC 210 Done");
    }

    @Test
    public void testChangeDue() {
        todo.setDue("Friday");
        assertEquals(todo.getDue(), "Friday");
    }

    @Test
    public void testChangeStatus() {
        todo.printTodo(1);
        todo.setStatus(1);
        assertTrue(todo.getStatus());
    }

    @Test
    public void testTodoSetAllParamConstructor() {
        todo = new SuperTodo("HWK", "WED", true);
        assertEquals(todo.getName(), "HWK");
        assertEquals(todo.getDue(), "WED");
        assertTrue(todo.getStatus());
    }

    @Test
    public void testSubTodoNegIndexException() {
        todo.setStatus(1);
        todo.addSubTodo(new SubTodo("SubTodo1", "Always"));
        todo.addSubTodo(new SubTodo("SubTodo2", "Always"));
        todo.printTodo(0);
        assertTrue(todo.getStatus());
        assertEquals(todo.getName(), "CPSC 210 HWK");
        assertEquals(todo.getDue(), "Wednesday");
        try {
            todo.removeSubTodo(-2);
            fail();
        } catch (NegativeListIndexException e) {
            //continue
        } catch (TooLargeListIndexException e) {
            fail();
        }
    }

    @Test
    public void testSubTodoLargeIndexExceptions() {
        todo.setStatus(1);
        todo.addSubTodo(new SubTodo("SubTodo1", "Always"));
        try {
            todo.removeSubTodo(2);
            fail();
        } catch (NegativeListIndexException e) {
            fail();
        } catch (TooLargeListIndexException e) {
            //continue
        }
        try {
            todo.removeSubTodo(0);
        } catch (Exception e) {
            fail();
        }
        assertEquals(todo.getSubList().size(),0);
    }

    @Test
    public void testChangeSubTodoStatus() {
        todo.setStatus(0);
        todo.addSubTodo(new SubTodo("SubTodo1", "Always", true));
        todo.addSubTodo(new SubTodo("SubTodo2", "Always", true));
        assertEquals(2, todo.completedSubTodos());
        try {
            todo.changeSubTodoStatus(0,0);
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, todo.completedSubTodos());
    }
}
