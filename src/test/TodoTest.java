import model.RegTodo;
import model.SubTodo;
import model.SuperTodo;
import model.Todo;
import model.exception.NegativeListIndexException;
import model.exception.TooLargeListIndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TodoTest {
    Todo todo;

    @BeforeEach
    public void init() {
        todo = new RegTodo("CPSC 210 HWK", "Wednesday");
    }

    @Test
    public void testOneTodo() {
        assertEquals("CPSC 210 HWK", todo.getName());
        assertEquals("Wednesday", todo.getDue());
        assertFalse(todo.getStatus());
    }

    @Test
    public void tesChangeName() {
        todo.setName("CPSC 210 Done");
        assertEquals(todo.getName(), "CPSC 210 Done");
    }

    @Test
    public void tesChangeDue() {
        todo.setDue("Friday");
        assertEquals(todo.getDue(), "Friday");
    }

    @Test
    public void tesChangeStatus() {
        todo.setStatus(1);
        assertTrue(todo.getStatus());
    }

    @Test
    public void testTodoSetAllParamConstructor() {
        todo = new RegTodo("HWK", "WED", true);
        assertEquals(todo.getName(), "HWK");
        assertEquals(todo.getDue(), "WED");
        assertTrue(todo.getStatus());
    }

    @Test
    public void testSuperTodo() {
        SuperTodo st = new SuperTodo("SuperTodo", "Never", true);
        st.addSubTodo(new SubTodo("SubTodo1", "Always"));
        st.printTodo(100);
        assertTrue(st.getStatus());
        assertEquals(st.getName(), "SuperTodo");
        assertEquals(st.getDue(), "Never");
        try {
            st.removeSubTodo(-2);
            fail();
        } catch (NegativeListIndexException e) {
            //continue
        } catch (TooLargeListIndexException e) {
            fail();
        }
        try {
            st.removeSubTodo(2);
            fail();
        } catch (NegativeListIndexException e) {
            fail();
        } catch (TooLargeListIndexException e) {
            //continue
        }
        try {
            st.removeSubTodo(0);
        } catch (Exception e) {
            fail();
        }
        assertEquals(st.getSubList().size(),0);
        st.setStatus(0);
        st.printTodo(2);
    }
}
