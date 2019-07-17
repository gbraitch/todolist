package test;

import model.Todo;
import model.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.LoadTodoList;
import util.SaveTodoList;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class Save_Load_TodoListTest {
    SaveTodoList s;
    LoadTodoList l;
    ArrayList<Todo> Savelist;
    ArrayList<Todo> Loadlist;
    TodoList td;

    @BeforeEach
    public void init(){
        Loadlist = new ArrayList<>();
    }


    @Test
    public void SaveTodoListWith3Items() throws IOException {
        Savelist = new ArrayList<>();
        Savelist.add(new Todo("CPSC 210", "WED"));
        Savelist.add(new Todo("CPSC 221", "TUE"));
        Savelist.add(new Todo("CPSC 213", "FRI"));

        Savelist.get(0).setStatus(1);
        Savelist.get(2).setStatus(1);

        s = new SaveTodoList("saveTest.txt");
        s.save(Savelist);

        l = new LoadTodoList("saveTest.txt");
        Loadlist = l.load();

        assertEquals(Loadlist.get(0).getName(), "CPSC 210");
        assertEquals(Loadlist.get(0).getDue(), "WED");
        assertTrue(Loadlist.get(0).getStatus());
        assertEquals(Loadlist.get(1).getName(), "CPSC 221");
        assertEquals(Loadlist.get(1).getDue(), "TUE");
        assertFalse(Loadlist.get(1).getStatus());
        assertEquals(Loadlist.get(2).getName(), "CPSC 213");
        assertEquals(Loadlist.get(2).getDue(), "FRI");
        assertTrue(Loadlist.get(2).getStatus());
    }

    @Test
    public void LoadTodoListWith5Items(){
        l = new LoadTodoList("loadTest.txt");

        try {
            Loadlist = l.load();
        } catch (IOException e) {
            fail();
        }

        assertEquals(Loadlist.get(0).getName(), "CPSC 210");
        assertEquals(Loadlist.get(0).getDue(), "WED");
        assertTrue(Loadlist.get(0).getStatus());

        assertEquals(Loadlist.get(1).getName(), "CPSC 121 URGENT ");
        assertEquals(Loadlist.get(1).getDue(), "FRI");
        assertFalse(Loadlist.get(1).getStatus());

        assertEquals(Loadlist.get(2).getName(), "CPSC310");
        assertEquals(Loadlist.get(2).getDue(), "THURS");
        assertTrue(Loadlist.get(2).getStatus());

        assertEquals(Loadlist.get(3).getName(), "CPSC 221 ONLY TWO THINGS");
        assertEquals(Loadlist.get(3).getDue(), "SAT");
        assertFalse(Loadlist.get(3).getStatus());

        assertEquals(Loadlist.get(4).getName(), "FINISH LAUNDRY");
        assertEquals(Loadlist.get(4).getDue(), "MON");
        assertTrue(Loadlist.get(4).getStatus());
    }
}
