package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.exception.NegativeListIndexException;
import model.exception.TooLargeListIndexException;
import ui.controller.Controller;
import util.SaveLoad;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;


public class TodoList extends Observable {
    private static String FILE_PATH = "save.json";

    private SaveLoad saveLoad;
    private ObservableList<Todo> list = FXCollections.observableArrayList();
    private Map<String, ObservableList<Todo>> map = new HashMap<>();

    public TodoList(Controller c) {
        saveLoad = new SaveLoad();
        addObserver(c);
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

    public void addRegTodo(String newTodoName, String newTodoDue) {
        RegTodo temp = new RegTodo(newTodoName, newTodoDue);
        list.add(temp);
        setChanged();
        notifyObservers(list);
    }

    public void addSuperTodo(String newTodoName, String newTodoDue) {
        SuperTodo temp = new SuperTodo(newTodoName, newTodoDue);
        list.add(temp);
        setChanged();
        notifyObservers(list);
    }

    public void removeTodo(Todo t) {
        list.remove(t);
        setChanged();
        notifyObservers(list);
    }

    public void printAllTodo() {
        System.out.println();
        printList(this.list);
    }

    private void printList(ObservableList<Todo> list) {
        int i = 0;
        if (list.isEmpty()) {
            System.out.println("List is Empty");
        } else {
            for (Todo td : list) {
                td.printTodo(i);
                i++;
            }
        }
    }

    public void printSuperTodoSubList(int index) {
        System.out.println();
        SuperTodo t = (SuperTodo) list.get(index);
        t.printSubList();
    }

    public void addSuperTodoSub(SuperTodo st, String name, String due) {
        st.addSubTodo(new SubTodo(name, due));
        setChanged();
        notifyObservers(list);
    }

    public void removeSuperTodoSub(SubTodo st) {
        st.getHead().removeSubTodo(st);
        setChanged();
        notifyObservers(list);
    }

    public void changeSuperTodoSubStatus(int superIndex, int subIndex, boolean status) throws TooLargeListIndexException,
            NegativeListIndexException {
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

    public int size() {
        return list.size();
    }

    private void checkIndex(int index) throws TooLargeListIndexException, NegativeListIndexException {
        if (index < 0) {
            throw new NegativeListIndexException();
        }
        if (index >= size()) {
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

    public void save(String fileName) {
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

}


