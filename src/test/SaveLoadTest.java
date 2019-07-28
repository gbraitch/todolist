package test;

import main.model.*;
import main.model.exception.NegativeListIndexException;
import main.model.exception.TooLargeListIndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.util.SaveLoad;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SaveLoadTest {
    SaveLoad saveList;
    SaveLoad loadList;
    ArrayList<Todo> saveTodo;
    ArrayList<Todo> loadTodo;
    TodoList td;

    @BeforeEach
    public void init() {
        loadTodo = new ArrayList<>();
        loadList = new SaveLoad();
    }


    @Test
    public void saveTodoListWith3Items() throws IOException {
        saveTodo = new ArrayList<>();
        saveTodo.add(new RegTodo("CPSC 210", "WED"));
        saveTodo.add(new RegTodo("CPSC 221", "TUE"));
        saveTodo.add(new RegTodo("CPSC 213", "FRI"));

        saveTodo.get(0).setStatus(1);
        saveTodo.get(2).setStatus(1);

        saveList = new SaveLoad();
        saveList.write(saveTodo,"saveTest.JSON");

        loadTodo = loadList.load("saveTest.JSON");

        assertEquals(loadTodo.get(0).getName(), "CPSC 210");
        assertEquals(loadTodo.get(0).getDue(), "WED");
        assertTrue(loadTodo.get(0).getStatus());
        assertEquals(loadTodo.get(1).getName(), "CPSC 221");
        assertEquals(loadTodo.get(1).getDue(), "TUE");
        assertFalse(loadTodo.get(1).getStatus());
        assertEquals(loadTodo.get(2).getName(), "CPSC 213");
        assertEquals(loadTodo.get(2).getDue(), "FRI");
        assertTrue(loadTodo.get(2).getStatus());
    }

    @Test
    public void loadTodoListWith5Items() {
        loadTodo = loadList.load("loadTest.JSON");

        assertEquals(loadTodo.get(0).getName(), "CPSC 210");
        assertEquals(loadTodo.get(0).getDue(), "WED");
        assertTrue(loadTodo.get(0).getStatus());

        assertEquals(loadTodo.get(1).getName(), "CPSC 121 URGENT");
        assertEquals(loadTodo.get(1).getDue(), "FRI");
        assertFalse(loadTodo.get(1).getStatus());

        assertEquals(loadTodo.get(2).getName(), "CPSC310");
        assertEquals(loadTodo.get(2).getDue(), "THURS");
        assertTrue(loadTodo.get(2).getStatus());

        assertEquals(loadTodo.get(3).getName(),
                "CPSC 221 ONLY TWO THINGS");
        assertEquals(loadTodo.get(3).getDue(), "SAT");
        assertFalse(loadTodo.get(3).getStatus());

        assertEquals(loadTodo.get(4).getName(), "FINISH LAUNDRY");
        assertEquals(loadTodo.get(4).getDue(), "MON");
        assertTrue(loadTodo.get(4).getStatus());
    }

    @Test
    public void testLoadableInterface() throws TooLargeListIndexException, NegativeListIndexException {
        td = new TodoList();
        td.addRegTodo("CPSC 210", "WED");
        td.changeStatus(0,1);
        try {
            td.save("saveTest.JSON");
        } catch (IOException e) {
            fail();
        }
        assertTrue(loadableInput(td));
        assertEquals(td.getTodoName(0), "CPSC 210");
        assertEquals(td.getTodoDue(0), "WED");
        assertTrue(td.getTodoStatus(0));
    }

    public boolean loadableInput(TodoList l) {
        try {
            l.load("saveTest.JSON");
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
