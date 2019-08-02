import model.SuperTodo;
import model.TodoList;
import model.exception.NegativeListIndexException;
import model.exception.OutOfBoundListIndexException;
import model.exception.TooLargeListIndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TodoListTest {
    TodoList todo;

    @BeforeEach
    public void init() {
        todo = new TodoList();
        todo.addRegTodo("CPSC 210", "Wednesday");
    }

    @Test
    public void testaddOneRegTodo() throws TooLargeListIndexException, NegativeListIndexException {
        assertEquals(todo.getTodoName(0), "CPSC 210");
        assertEquals(todo.getTodoDue(0), "Wednesday");
        assertFalse(todo.getTodoStatus(0));
    }

    @Test
    public void testaddTwoRegTodo() throws TooLargeListIndexException, NegativeListIndexException {
        todo.addRegTodo("CPSC 121", "Friday");

        assertEquals(todo.getTodoName(0), "CPSC 210");
        assertEquals(todo.getTodoDue(0), "Wednesday");
        assertFalse(todo.getTodoStatus(0));

        assertEquals(todo.getTodoName(1), "CPSC 121");
        assertEquals(todo.getTodoDue(1), "Friday");
        assertFalse(todo.getTodoStatus(1));
    }

    @Test
    public void testDeleteRegTodo() throws TooLargeListIndexException, NegativeListIndexException {
        todo.addRegTodo("CPSC 121", "Friday");
        try {
            todo.deleteTodo(0);
        } catch (OutOfBoundListIndexException e) {
            fail();
        }
        assertEquals(todo.size(), 1);
        assertEquals(todo.getTodoName(0), "CPSC 121");
        assertEquals(todo.getTodoDue(0), "Friday");
        assertFalse(todo.getTodoStatus(0));
    }

    @Test
    public void testEditRegTodo() throws TooLargeListIndexException, NegativeListIndexException {
        todo.changeName(0,"CPSC 210 Done");
        todo.changeStatus(0,1);
        todo.changeDue(0,"Friday");
        assertEquals(todo.getTodoName(0), "CPSC 210 Done");
        assertEquals(todo.getTodoDue(0), "Friday");
        assertTrue(todo.getTodoStatus(0));
    }

    @Test
    public void testaddSuperTodoandSubTodo() {
        todo.addSuperTodo("SuperTodo1", "Wed");
        todo.addSuperTodoSub(1,"SubTodo1", "Fri");
        try {
            todo.changeSuperTodoSubStatus(1,0,1);
        } catch (OutOfBoundListIndexException e) {
            fail();
        }
        SuperTodo t1 = new SuperTodo("test", "test");
        try {
            t1 = todo.getSuperTodo(1);
        } catch (OutOfBoundListIndexException e) {
            fail();
        }
        testaddSuperTodoandSubTodo2(t1);
    }


    public void testaddSuperTodoandSubTodo2(SuperTodo t1) {
        assertEquals(t1.getName(), "SuperTodo1");
        assertEquals(t1.getDue(), "Wed");
        assertFalse(t1.getStatus());
        assertEquals(t1.getSubList().get(0).getName(), "SubTodo1");
        assertEquals(t1.getSubList().get(0).getDue(), "Fri");
        assertTrue(t1.getSubList().get(0).getStatus());
        todo.printSuperTodoSubList(1);
    }

    @Test
    public void testEditSuperTodo() throws TooLargeListIndexException, NegativeListIndexException {
        todo.addSuperTodo("SuperTodo1", "Wed");
        todo.addSuperTodoSub(1,"SubTodo1", "Fri");
        todo.changeStatus(1,1);
        try {
            todo.removeSuperTodoSub(1,0);
        } catch (OutOfBoundListIndexException e) {
            fail();
        }
        SuperTodo t1 = new SuperTodo("test", "test");
        try {
            t1 = todo.getSuperTodo(1);
        } catch (OutOfBoundListIndexException e) {
            fail();
        }
        assertEquals(t1.getSubList().size(),0);
        assertTrue(t1.getStatus());
        assertEquals(todo.getTodoType(1), "Super");
    }

    @Test
    public void testSave() throws TooLargeListIndexException, NegativeListIndexException, IOException {
        todo.save("saveTest.JSON");

        TodoList tt = new TodoList();

        tt.load("saveTest.JSON");

        assertEquals(tt.getTodoName(0), "CPSC 210");
        assertEquals(tt.getTodoDue(0), "Wednesday");
        assertFalse(tt.getTodoStatus(0));
    }

    @Test
    public void testLoad() throws OutOfBoundListIndexException {
        todo.load("loadTest.JSON");

        assertEquals(todo.getTodoName(0), "CPSC 210");
        assertEquals(todo.getTodoDue(0), "WED");
        assertTrue(todo.getTodoStatus(0));

        assertEquals(todo.getTodoName(1),
                "CPSC 121 URGENT");
        assertEquals(todo.getTodoDue(1), "FRI");
        assertFalse(todo.getTodoStatus(1));

        assertEquals(todo.getTodoName(2), "CPSC310");
        assertEquals(todo.getTodoDue(2), "THURS");
        assertTrue(todo.getTodoStatus(2));

        testLoad2(todo);

        todo.printAllTodo();
    }

    public void testLoad2(TodoList todo) throws OutOfBoundListIndexException {
        assertEquals(todo.getTodoName(3),
                "CPSC 221 ONLY TWO THINGS");
        assertEquals(todo.getTodoDue(3), "SAT");
        assertFalse(todo.getTodoStatus(3));

        assertEquals(todo.getTodoName(4),
                "FINISH LAUNDRY");
        assertEquals(todo.getTodoDue(4), "MON");
        assertTrue(todo.getTodoStatus(4));
        todo.printAllTodo();
    }

    @Test
    public void testCheckIndex() {
        try {
            todo.deleteTodo(-2);
        } catch (NegativeListIndexException e) {
            // continue as normal
        } catch (TooLargeListIndexException e) {
            fail();
        }
        try {
            todo.deleteTodo(5);
            fail();
        } catch (NegativeListIndexException e) {
            fail();
        } catch (TooLargeListIndexException e) {
            // continue as normal
        }
        try {
            todo.deleteTodo(0);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testSaveLoadWithDefaultFilePath() {
        TodoList todoOriginal = new TodoList();
        todoOriginal.load(null);
        todoOriginal.save(null);
    }

    @Test
    public void testPrints() {
        todo.printAllTodo();
        todo.printOnlyRegTodos();
        todo.printOnlySubTodos();
        todo.printOnlySuperTodos();
    }
}
