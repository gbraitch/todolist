import model.RegTodo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegTodoTest {
    RegTodo todo;

    @BeforeEach
    public void init() {
        todo = new RegTodo("CPSC 210 HWK", "08-15-2019");
    }

    @Test
    public void testOneTodo() {
        assertEquals("CPSC 210 HWK", todo.getName());
        assertEquals("08-15-2019", todo.getDue());
        assertFalse(todo.getBooleanProperty().get());
        assertFalse(todo.getStatus());
    }

    @Test
    public void testChangeName() {
        todo.setName("CPSC 210 Done");
        assertEquals(todo.getName(), "CPSC 210 Done");
    }

    @Test
    public void testChangeDue() {
        todo.setDue("08-15-2019");
        assertFalse(todo.equals(new RegTodo("CPSC 210 HWK", "08-15-2019", true)));
        assertTrue(todo.equals(new RegTodo("CPSC 210 HWK", "08-15-2019", false)));
        assertEquals(todo.getDue(), "08-15-2019");
        assertTrue(todo.equals(new RegTodo("CPSC 210 HWK", "08-15-2019")));
        assertTrue(todo.equals(todo));
        assertFalse(todo.equals(new Integer(1)));
        todo.hashCode();
    }

    @Test
    public void testChangeStatus() {
        todo.setStatus(true);
        assertTrue(todo.getStatus());
    }

    @Test
    public void testTodoSetAllParamConstructor() {
        todo = new RegTodo("HWK", "08-15-2019", true);
        todo.toString();
        assertEquals(todo.getName(), "HWK");
        assertEquals(todo.getDue(), "08-15-2019");
        assertTrue(todo.getStatus());
        todo.setStatus(false);
        todo.toString();
    }


}
