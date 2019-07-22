package test;

import model.RegTodo;
import model.Todo;
import model.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.LoadTodoList;
import util.SaveTodoList;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SaveLoadTest {
    SaveTodoList saveList;
    LoadTodoList loadList;
    ArrayList<Todo> saveTodo;
    ArrayList<Todo> loadTodo;
    TodoList td;

    @BeforeEach
    public void init() {
        loadTodo = new ArrayList<>();
    }


    @Test
    public void saveTodoListWith3Items() throws IOException {
        saveTodo = new ArrayList<>();
        saveTodo.add(new RegTodo("CPSC 210", "WED"));
        saveTodo.add(new RegTodo("CPSC 221", "TUE"));
        saveTodo.add(new RegTodo("CPSC 213", "FRI"));

        saveTodo.get(0).setStatus(1);
        saveTodo.get(2).setStatus(1);

        saveList = new SaveTodoList("saveTest.txt");
        saveList.save(saveTodo);

        loadList = new LoadTodoList("saveTest.txt");
        loadTodo = loadList.load();

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
        loadList = new LoadTodoList("loadTest.txt");

        try {
            loadTodo = loadList.load();
        } catch (IOException e) {
            fail();
        }

        assertEquals(loadTodo.get(0).getName(), "CPSC 210");
        assertEquals(loadTodo.get(0).getDue(), "WED");
        assertTrue(loadTodo.get(0).getStatus());

        assertEquals(loadTodo.get(1).getName(),
                "CPSC 121 URGENT ");
        assertEquals(loadTodo.get(1).getDue(), "FRI");
        assertFalse(loadTodo.get(1).getStatus());

        assertEquals(loadTodo.get(2).getName(), "CPSC310");
        assertEquals(loadTodo.get(2).getDue(), "THURS");
        assertTrue(loadTodo.get(2).getStatus());

        assertEquals(loadTodo.get(3).getName(),
                "CPSC 221 ONLY TWO THINGS");
        assertEquals(loadTodo.get(3).getDue(), "SAT");
        assertFalse(loadTodo.get(3).getStatus());

        assertEquals(loadTodo.get(4).getName(),
                "FINISH LAUNDRY");
        assertEquals(loadTodo.get(4).getDue(), "MON");
        assertTrue(loadTodo.get(4).getStatus());
    }

    @Test
    public void testLoadableInterface() {
        td = new TodoList();
        td.addRegTodo("CPSC 210", "WED");
        td.changeStatus(0,1);
        try {
            td.save();
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
            l.load();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
