import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.RegTodo;
import model.Todo;
import model.TodoList;
import model.exception.NegativeListIndexException;
import model.exception.TooLargeListIndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.SaveLoad;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SaveLoadTest {
    SaveLoad saveList;
    SaveLoad loadList;
    ObservableList<Todo> saveTodo;
    ObservableList<Todo> loadTodo;
    TodoList td;

    @BeforeEach
    public void init() {
        loadTodo = FXCollections.observableArrayList();
        loadList = new SaveLoad();
    }


    @Test
    public void saveTodoListWith3Items() throws IOException {
        saveTodo = FXCollections.observableArrayList();
        saveTodo.add(new RegTodo("CPSC 210", "08-16-2019"));
        saveTodo.add(new RegTodo("CPSC 221", "08-16-2019"));
        saveTodo.add(new RegTodo("CPSC 213", "08-16-2019"));

        saveTodo.get(0).setStatus(true);
        saveTodo.get(2).setStatus(true);

        saveList = new SaveLoad();
        saveList.save(saveTodo,"saveTest.json");
        loadTodo = loadList.load("saveTest.json");

        assertEquals(loadTodo.get(0).getName(), "CPSC 210");
        assertEquals(loadTodo.get(0).getDue(), "08-16-2019");
        assertTrue(loadTodo.get(0).getStatus());
        assertEquals(loadTodo.get(1).getName(), "CPSC 221");
        assertEquals(loadTodo.get(1).getDue(), "08-16-2019");
        assertFalse(loadTodo.get(1).getStatus());
        assertEquals(loadTodo.get(2).getName(), "CPSC 213");
        assertEquals(loadTodo.get(2).getDue(), "08-16-2019");
        assertTrue(loadTodo.get(2).getStatus());
    }

    @Test
    public void loadTodoListWith5Items() throws IOException {

        TodoList tdlist = new TodoList();
        tdlist.addRegTodo(new RegTodo("CPSC 210", "08-16-2019", true));
        tdlist.addRegTodo(new RegTodo("CPSC 121 URGENT", "08-16-2019"));
        tdlist.addRegTodo(new RegTodo("CPSC310", "08-16-2019", true));
        tdlist.addRegTodo(new RegTodo("CPSC 221 ONLY TWO THINGS", "08-16-2019"));
        tdlist.addRegTodo(new RegTodo("FINISH LAUNDRY", "08-16-2019", true));

        tdlist.save("saveTest.json");

        loadTodo = loadList.load("saveTest.JSON");

        assertEquals(loadTodo.get(0).getName(), "CPSC 210");
        assertEquals(loadTodo.get(0).getDue(), "08-16-2019");
        assertTrue(loadTodo.get(0).getStatus());

        assertEquals(loadTodo.get(1).getName(), "CPSC 121 URGENT");
        assertEquals(loadTodo.get(1).getDue(), "08-16-2019");
        assertFalse(loadTodo.get(1).getStatus());

        assertEquals(loadTodo.get(2).getName(), "CPSC310");
        assertEquals(loadTodo.get(2).getDue(), "08-16-2019");
        assertTrue(loadTodo.get(2).getStatus());

        assertEquals(loadTodo.get(3).getName(),
                "CPSC 221 ONLY TWO THINGS");
        assertEquals(loadTodo.get(3).getDue(), "08-16-2019");
        assertFalse(loadTodo.get(3).getStatus());

        assertEquals(loadTodo.get(4).getName(), "FINISH LAUNDRY");
        assertEquals(loadTodo.get(4).getDue(), "08-16-2019");
        assertTrue(loadTodo.get(4).getStatus());
    }

    @Test
    public void testLoadableInterface() throws TooLargeListIndexException, NegativeListIndexException, IOException {
        td = new TodoList();
        td.addRegTodo(new RegTodo("CPSC 210", "08-16-2019"));
        td.changeStatus(0,true);
        td.save("saveTest.JSON");
        td.load("saveTest.JSON");
        assertEquals(td.getTodoName(0), "CPSC 210");
        assertEquals(td.getTodoDue(0), "08-16-2019");
        assertTrue(td.getTodoStatus(0));
    }

    @Test
    public void testInvalidLoadFile() throws IOException {
        td = new TodoList();
        td.addRegTodo(new RegTodo("RegTodoName", "08-16-2019"));
        TodoList td2 = new TodoList();
        td2.load("out2");
        assertEquals(td2.getSize(),0);
    }
}
