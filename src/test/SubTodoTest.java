import model.SubTodo;
import model.SuperTodo;
import model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SubTodoTest {
    SubTodo todo;
    SuperTodo st;

    @BeforeEach
    public void init() {
        todo = new SubTodo("CPSC 210 HWK", "08-15-2019");
        st = new SuperTodo("testSuper", "08-15-2019");
    }

    @Test
    public void testSetAllThreeParamConstrutor() {
        todo = new SubTodo("test", "08-15-2019", true);
        st.addSubTodo(todo);
        assertTrue(todo.getHead().equals(st));
        todo.addSuper(st);
        assertEquals("test", todo.getName());
        assertEquals("08-15-2019", todo.getDue());
        assertTrue(todo.getStatus());
        todo.setStatus(false);
        assertFalse(todo.getStatus());
    }

    @Test
    public void testTodoEqualsAndHashCode() {
        todo = new SubTodo("test", "08-15-2019", true);
        assertEquals(todo.getType(), "Sub");
        Map<String, Todo> map = new HashMap<>();
        map.put("subtodo1",todo);
        SubTodo todo2 = new SubTodo("test", "08-15-2019", false);
        SubTodo todo3 = new SubTodo("test", "08-15-2019", true);
        assertFalse(map.containsValue(todo2));
        assertTrue(map.containsValue(todo3));
        assertTrue(map.get("subtodo1").equals(todo));
        todo.toString();
        todo.hashCode();
    }


}
