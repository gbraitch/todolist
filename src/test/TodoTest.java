package test;

import model.Todo;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TodoTest {
    Todo t;

    @Test
    public void testOneTodo(){
        t = new Todo("CPSC 210 HWK", "Wednesday");
        assertTrue(true);
        assertFalse(false);
        assertEquals("CPSC 210 HWK", t.getName());
    }
}
