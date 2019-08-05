import model.SubTodo;
import model.SuperTodo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SuperTodoTest {
    SuperTodo todo;

    @BeforeEach
    public void init() {
        todo = new SuperTodo("CPSC 210 HWK", "08-15-2019");
    }

    @Test
    public void testOneTodo() {
        assertEquals("CPSC 210 HWK", todo.getName());
        assertEquals("08-15-2019", todo.getDue());
        assertFalse(todo.getStatus());
    }

    @Test
    public void testChangeName() {
        todo.setName("CPSC 210 Done");
        assertEquals(todo.getName(), "CPSC 210 Done");
    }

    @Test
    public void testChangeDue() {
        todo.setDue("09-15-2019");
        assertEquals(todo.getDue(), "09-15-2019");
    }

    @Test
    public void testChangeStatus() {
        todo.setStatus(true);
        assertTrue(todo.getStatus());
    }

    @Test
    public void testTodoSetAllParamConstructor() {
        todo = new SuperTodo("HWK", "08-15-2019", true);
        assertEquals(todo.getName(), "HWK");
        assertEquals(todo.getDue(), "08-15-2019");
        assertTrue(todo.getStatus());
    }

    @Test
    public void testSubTodoNegIndexException() {
        todo.setStatus(true);
        todo.addSubTodo(new SubTodo("SubTodo1", "08-15-2019"));
        todo.addSubTodo(new SubTodo("SubTodo2", "08-15-2019"));
        assertTrue(todo.getStatus());
        assertEquals(todo.getName(), "CPSC 210 HWK");
        assertEquals(todo.getDue(), "08-15-2019");
    }

    @Test
    public void testSubTodoLargeIndexExceptions() {
        todo.setStatus(true);
        todo.addSubTodo(new SubTodo("SubTodo1", "08-15-2019"));
        assertEquals(todo.getSubList().size(),1);
        todo.toString();
        todo.removeSubTodo(new SubTodo("SubTodo1", "08-15-2019"));
        assertEquals(todo.getSubList().size(),0);
        try {
            todo.changeSubTodoStatus(-1,true);
            fail();
        } catch (Exception e) {
            // pass
        }
        try {
            todo.changeSubTodoStatus(20,true);
            fail();
        } catch (Exception e) {
            // pass
        }
    }

    @Test
    public void testChangeSubTodoStatus() {
        todo.setStatus(false);
        todo.addSubTodo(new SubTodo("SubTodo1", "08-15-2019", true));
        todo.addSubTodo(new SubTodo("SubTodo2", "08-15-2019", true));
        assertEquals(2, todo.completedSubTodos());
        try {
            todo.changeSubTodoStatus(0,false);
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, todo.completedSubTodos());
    }
}
