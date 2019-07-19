package test;

import model.Loadable;
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

public class saveLoadTodoListTest {
    SaveTodoList saveList;
    LoadTodoList loadList;
    ArrayList<Todo> Savelist;
    ArrayList<Todo> LoadList;
    TodoList td;

    @BeforeEach
    public void init() {
        LoadList = new ArrayList<>();
    }


    @Test
    public void SaveTodoListWith3Items() throws IOException {
        Savelist = new ArrayList<>();
        Savelist.add(new RegTodo("CPSC 210", "WED"));
        Savelist.add(new RegTodo("CPSC 221", "TUE"));
        Savelist.add(new RegTodo("CPSC 213", "FRI"));

        Savelist.get(0).setStatus(1);
        Savelist.get(2).setStatus(1);

        saveList = new SaveTodoList("saveTest.txt");
        saveList.save(Savelist);

        loadList = new LoadTodoList("saveTest.txt");
        LoadList = loadList.load();

        assertEquals(LoadList.get(0).getName(), "CPSC 210");
        assertEquals(LoadList.get(0).getDue(), "WED");
        assertTrue(LoadList.get(0).getStatus());
        assertEquals(LoadList.get(1).getName(), "CPSC 221");
        assertEquals(LoadList.get(1).getDue(), "TUE");
        assertFalse(LoadList.get(1).getStatus());
        assertEquals(LoadList.get(2).getName(), "CPSC 213");
        assertEquals(LoadList.get(2).getDue(), "FRI");
        assertTrue(LoadList.get(2).getStatus());
    }

    @Test
    public void LoadTodoListWith5Items() {
        loadList = new LoadTodoList("loadTest.txt");

        try {
            LoadList = loadList.load();
        } catch (IOException e) {
            fail();
        }

        assertEquals(LoadList.get(0).getName(), "CPSC 210");
        assertEquals(LoadList.get(0).getDue(), "WED");
        assertTrue(LoadList.get(0).getStatus());

        assertEquals(LoadList.get(1).getName(),
                "CPSC 121 URGENT ");
        assertEquals(LoadList.get(1).getDue(), "FRI");
        assertFalse(LoadList.get(1).getStatus());

        assertEquals(LoadList.get(2).getName(), "CPSC310");
        assertEquals(LoadList.get(2).getDue(), "THURS");
        assertTrue(LoadList.get(2).getStatus());

        assertEquals(LoadList.get(3).getName(),
                "CPSC 221 ONLY TWO THINGS");
        assertEquals(LoadList.get(3).getDue(), "SAT");
        assertFalse(LoadList.get(3).getStatus());

        assertEquals(LoadList.get(4).getName(),
                "FINISH LAUNDRY");
        assertEquals(LoadList.get(4).getDue(), "MON");
        assertTrue(LoadList.get(4).getStatus());
    }

    @Test
    public void TestLoadableInterface() {
        td = new TodoList();
        td.addRegTodo("CPSC 210", "WED");
        td.changeStatus(0,1);
        try {
            td.save();
        } catch (IOException e) {
            fail();
        }
        assertTrue(LoadableInput(td));
        assertEquals(td.getTodoName(0), "CPSC 210");
        assertEquals(td.getTodoDue(0), "WED");
        assertTrue(td.getTodoStatus(0));
    }

    public boolean LoadableInput(Loadable l) {
        try {
            l.load();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
