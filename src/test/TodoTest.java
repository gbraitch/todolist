package test;

import model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TodoTest {
    Todo t;

    @BeforeEach
    public void init(){
        t = new Todo("CPSC 210 HWK", "Wednesday");
    }

    @Test
    public void testOneTodo() {
        assertEquals("CPSC 210 HWK", t.getName());
        assertEquals("Wednesday", t.getDue());
        assertFalse(t.getStatus());
    }
    @Test
    public void tesChangeName() {
        t.setName("CPSC 210 Done");
        assertEquals(t.getName(), "CPSC 210 Done");
    }
    @Test
    public void tesChangeDue() {
        t.setDue("Friday");
        assertEquals(t.getDue(), "Friday");
    }
    @Test
    public void tesChangeStatus() {
        t.setStatus(1);
        assertTrue(t.getStatus());
    }
}
