package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.exception.NegativeListIndexException;
import model.exception.OutOfBoundListIndexException;
import model.exception.TooLargeListIndexException;
import ui.controller.Controller;
import util.SaveLoad;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;


public class TodoList extends Observable implements Iterable<Todo> {
    private static String FILE_PATH = "save.json";

    private SaveLoad saveLoad;
    private ObservableList<Todo> list = FXCollections.observableArrayList();
    private Map<String, ObservableList<Todo>> map = new HashMap<>();

    public TodoList(Controller c) {
        saveLoad = new SaveLoad();
        addObserver(c);
    }

    public TodoList() {
        saveLoad = new SaveLoad();
    }

    public int getIndex(Todo t) throws OutOfBoundListIndexException {
        if (list.contains(t)) {
            return list.indexOf(t);
        } else {
            throw new OutOfBoundListIndexException();
        }
    }

    public void changeName(int edit, String newName) {
        Todo temp = list.get(edit);
        temp.setName(newName);
        setChanged();
        notifyObservers(list);
    }

    public void changeDue(int edit, String newDue) {
        Todo temp = list.get(edit);
        temp.setDue(newDue);
        setChanged();
        notifyObservers(list);
    }

    public void changeStatus(int edit, boolean status) {
        Todo temp = list.get(edit);
        temp.setStatus(status);
        setChanged();
        notifyObservers(list);
    }

    public void addRegTodo(Todo rt) {
        list.add(rt);
        setChanged();
        notifyObservers(list);
    }

    public void addSuperTodo(SuperTodo temp) {
        list.add(temp);
        setChanged();
        notifyObservers(list);
    }

    public void removeTodo(Todo t) throws OutOfBoundListIndexException {
        if (list.remove(t)) {
            setChanged();
            notifyObservers(list);
        } else {
            throw new OutOfBoundListIndexException();
        }
    }

    public void addSuperTodoSub(SuperTodo sup, SubTodo sub) {
        sup.addSubTodo(sub);
        setChanged();
        notifyObservers(list);
    }

    public void removeSuperTodoSub(SubTodo st) {
        st.getHead().removeSubTodo(st);
        setChanged();
        notifyObservers(list);
    }

    public void changeSuperTodoSubStatus(int superIndex, int subIndex, boolean status)
            throws TooLargeListIndexException, NegativeListIndexException {
        SuperTodo t = (SuperTodo) list.get(superIndex);
        t.changeSubTodoStatus(subIndex, status);
        setChanged();
        notifyObservers(list);
    }

    public String getTodoName(int index) throws TooLargeListIndexException, NegativeListIndexException {
        checkIndex(index);
        return list.get(index).getName();
    }

    public String getTodoDue(int index) throws TooLargeListIndexException, NegativeListIndexException {
        checkIndex(index);
        return list.get(index).getDue();
    }

    public boolean getTodoStatus(int index) throws TooLargeListIndexException, NegativeListIndexException {
        checkIndex(index);
        return list.get(index).getStatus();
    }

    public String getTodoType(int index) throws TooLargeListIndexException, NegativeListIndexException {
        checkIndex(index);
        return list.get(index).getType();
    }

    public SuperTodo getSuperTodo(int index) throws TooLargeListIndexException, NegativeListIndexException {
        checkIndex(index);
        return (SuperTodo)list.get(index);
    }

    public int getSize() {
        return list.size();
    }

    private void checkIndex(int index) throws TooLargeListIndexException, NegativeListIndexException {
        if (index < 0) {
            throw new NegativeListIndexException();
        }
        if (index >= getSize()) {
            throw new TooLargeListIndexException();
        }
    }

    public double getCompleted() {
        double i = 0;
        for (Todo t : list) {
            if (t.getStatus()) {
                i++;
            }
            if (t.getType().equals("Super")) {
                SuperTodo temp = (SuperTodo)t;
                i += temp.completedSubTodos();
            }
        }
        return i;
    }

    public double getTotalTodos() {
        double i = 0;
        for (Todo t : list) {
            if (t.getType().equals("Super")) {
                SuperTodo temp = (SuperTodo)t;
                i += temp.getSubList().size();
            }
            i++;
        }
        return i;
    }

    public void save(String fileName) throws IOException {
        String arg;
        if (fileName == null) {
            arg = FILE_PATH;
        } else {
            arg = fileName;
        }
        saveLoad.save(list, arg);
    }

    public void load(String fileName) {
        String arg;
        if (fileName == null) {
            arg = FILE_PATH;
        } else {
            arg = fileName;
        }
        list = saveLoad.load(arg);
        loadSubTodoProperties();
        setChanged();
        notifyObservers(list);
    }

    private void loadSubTodoProperties() {
        for (Todo t : list) {
            if (t.getType().equals("Super")) {
                SuperTodo temp = (SuperTodo)t;
                for (Todo t2 : temp.getSubList()) {
                    SubTodo st = (SubTodo)t2;
                    st.addSuper(temp);
                }
            }
        }
    }

    @Override
    public Iterator<Todo> iterator() {
        return list.iterator();
    }
}


