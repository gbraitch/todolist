//import model.RegTodo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class RegTodoTest {
//    RegTodo todo;
//
//    @BeforeEach
//    public void init() {
//        todo = new RegTodo("CPSC 210 HWK", "Wednesday");
//    }
//
//    @Test
//    public void testOneTodo() {
//        assertEquals("CPSC 210 HWK", todo.getName());
//        assertEquals("Wednesday", todo.getDue());
//        assertFalse(todo.getStatus());
//    }
//
//    @Test
//    public void testChangeName() {
//        todo.setName("CPSC 210 Done");
//        assertEquals(todo.getName(), "CPSC 210 Done");
//    }
//
//    @Test
//    public void testChangeDue() {
//        todo.setDue("Friday");
//        assertEquals(todo.getDue(), "Friday");
//    }
//
//    @Test
//    public void testChangeStatus() {
//        todo.setStatus(1);
//        assertTrue(todo.getStatus());
//    }
//
//    @Test
//    public void testTodoSetAllParamConstructor() {
//        todo = new RegTodo("HWK", "WED", true);
//        assertEquals(todo.getName(), "HWK");
//        assertEquals(todo.getDue(), "WED");
//        assertTrue(todo.getStatus());
//        todo.printTodo(1);
//        todo.setStatus(0);
//        todo.printTodo(1);
//    }
//}
