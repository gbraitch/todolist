import model.RegTodo;
import model.SubTodo;
import model.SuperTodo;
import model.TodoList;
import model.exception.NegativeListIndexException;
import model.exception.OutOfBoundListIndexException;
import model.exception.TooLargeListIndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.controller.Controller;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TodoListTest {
    TodoList todo;

    @BeforeEach
    public void init() {
        todo = new TodoList();
        todo.addRegTodo(new RegTodo("CPSC 210", "08-15-2019"));
    }

    @Test
    public void testaddOneRegTodo() throws TooLargeListIndexException, NegativeListIndexException {
        assertEquals(todo.getTodoName(0), "CPSC 210");
        assertEquals(todo.getTodoDue(0), "08-15-2019");
        assertFalse(todo.getTodoStatus(0));
        try {
            todo.getTodoStatus(-1);
            fail();
        } catch (OutOfBoundListIndexException e) {
            //
        }
        try {
            todo.getTodoStatus(10);
            fail();
        } catch (OutOfBoundListIndexException e) {
            //
        }
    }

    @Test
    public void testaddTwoRegTodo() throws TooLargeListIndexException, NegativeListIndexException {
        todo.addRegTodo(new RegTodo("CPSC 121", "08-15-2019"));

        assertEquals(todo.getTodoName(0), "CPSC 210");
        assertEquals(todo.getTodoDue(0), "08-15-2019");
        assertFalse(todo.getTodoStatus(0));

        assertEquals(todo.getTodoName(1), "CPSC 121");
        assertEquals(todo.getTodoDue(1), "08-15-2019");
        assertFalse(todo.getTodoStatus(1));

        try {
            todo.getIndex(new RegTodo("CPSC 210", "08-15-2019"));
        } catch (OutOfBoundListIndexException e) {
            fail();
        }
        try {
            todo.getIndex(new RegTodo("CPSC", "08-15-2019"));
            fail();
        } catch (OutOfBoundListIndexException e) {
            //pass
        }
    }

    @Test
    public void testDeleteRegTodo() throws TooLargeListIndexException, NegativeListIndexException {
        todo.addRegTodo(new RegTodo("CPSC 121", "08-15-2019"));
        try {
            todo.removeTodo(new RegTodo("CPSC 121", "08-15-2019"));
        } catch (OutOfBoundListIndexException e) {
            fail();
        }
        assertEquals(todo.getSize(), 1);
        assertEquals(todo.getTodoName(0), "CPSC 210");
        assertEquals(todo.getTodoDue(0), "08-15-2019");
        assertFalse(todo.getTodoStatus(0));
        todo = new TodoList(new Controller());
    }

    @Test
    public void testEditRegTodo() throws TooLargeListIndexException, NegativeListIndexException {
        todo.changeName(0,"CPSC 210 Done");
        todo.changeStatus(0,true);
        todo.changeDue(0,"08-15-2019");
        assertEquals(todo.getTodoName(0), "CPSC 210 Done");
        assertEquals(todo.getTodoDue(0), "08-15-2019");
        assertTrue(todo.getTodoStatus(0));
    }

    @Test
    public void testaddSuperTodoandSubTodo() throws IOException {
        SuperTodo temp = new SuperTodo("SuperTodo1", "Wed");
        todo.addSuperTodo(temp);
        try {
            assertEquals(todo.getTodoType(1), "Super");
        } catch (OutOfBoundListIndexException e) {
            fail();
        }
        try {
            todo.removeTodo(new RegTodo("yeet", "yeet"));
            fail();
        } catch (OutOfBoundListIndexException e) {
            //
        }
        todo.addSuperTodoSub(temp, new SubTodo("SubTodo1", "Fri"));
        try {
            todo.changeSuperTodoSubStatus(1,0,true);
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
        todo.save("saveTest.json");
        todo.load("saveTest.json");
    }


    public void testaddSuperTodoandSubTodo2(SuperTodo t1) {
        assertEquals(t1.getName(), "SuperTodo1");
        assertEquals(t1.getDue(), "Wed");
        assertFalse(t1.getStatus());
        assertEquals(t1.getSubList().get(0).getName(), "SubTodo1");
        assertEquals(t1.getSubList().get(0).getDue(), "Fri");
        assertTrue(t1.getSubList().get(0).getStatus());
    }

    @Test
    public void testEditSuperTodo() throws TooLargeListIndexException, NegativeListIndexException {
        SuperTodo st = new SuperTodo("SuperTodo1", "Wed");
        todo.addSuperTodo(st);
        todo.addSuperTodoSub(st,new SubTodo("SubTodo1", "Fri"));
        SubTodo temp2 = (SubTodo) st.getSubList().get(0);
        todo.removeSuperTodoSub(temp2);
        st.setStatus(true);
        st.addSubTodo(temp2);
        temp2.setStatus(true);
        todo.getCompleted();
        todo.getTotalTodos();

        SuperTodo t1 = new SuperTodo("test", "test");
        try {
            t1 = todo.getSuperTodo(1);
        } catch (OutOfBoundListIndexException e) {
            fail();
        }
        assertEquals(t1.getSubList().size(),1);
        assertTrue(t1.getStatus());
        assertEquals(todo.getTodoType(1), "Super");
    }

    @Test
    public void testSave() throws TooLargeListIndexException, NegativeListIndexException, IOException {
        todo.save("saveTest.JSON");

        TodoList tt = new TodoList();
        tt.iterator();

        tt.load("saveTest.JSON");

        assertEquals(tt.getTodoName(0), "CPSC 210");
        assertEquals(tt.getTodoDue(0), "08-15-2019");
        assertFalse(tt.getTodoStatus(0));
    }

    @Test
    public void testLoad() throws OutOfBoundListIndexException, IOException {
        TodoList tdlist = new TodoList();
        tdlist.addRegTodo(new RegTodo("CPSC 210", "08-16-2019", true));
        tdlist.addRegTodo(new RegTodo("CPSC 121 URGENT", "08-16-2019"));
        tdlist.addRegTodo(new RegTodo("CPSC310", "08-16-2019", true));
        tdlist.addRegTodo(new RegTodo("CPSC 221 ONLY TWO THINGS", "08-16-2019"));
        tdlist.addRegTodo(new RegTodo("FINISH LAUNDRY", "08-16-2019", true));

        tdlist.save("saveTest.JSON");
        todo.load("saveTest.JSON");

        assertEquals(todo.getTodoName(0), "CPSC 210");
        assertEquals(todo.getTodoDue(0), "08-16-2019");
        assertTrue(todo.getTodoStatus(0));

        assertEquals(todo.getTodoName(1),
                "CPSC 121 URGENT");
        assertEquals(todo.getTodoDue(1), "08-16-2019");
        assertFalse(todo.getTodoStatus(1));

        assertEquals(todo.getTodoName(2), "CPSC310");
        assertEquals(todo.getTodoDue(2), "08-16-2019");
        assertTrue(todo.getTodoStatus(2));

        testLoad2(todo);
    }

    public void testLoad2(TodoList todo) throws OutOfBoundListIndexException {
        assertEquals(todo.getTodoName(3),
                "CPSC 221 ONLY TWO THINGS");
        assertEquals(todo.getTodoDue(3), "08-16-2019");
        assertFalse(todo.getTodoStatus(3));

        assertEquals(todo.getTodoName(4),
                "FINISH LAUNDRY");
        assertEquals(todo.getTodoDue(4), "08-16-2019");
        assertTrue(todo.getTodoStatus(4));
    }

    @Test
    public void testSaveLoadWithDefaultFilePath() throws IOException {
        TodoList todoOriginal = new TodoList();
        todoOriginal.load(null);
        todoOriginal.save(null);
    }


}
