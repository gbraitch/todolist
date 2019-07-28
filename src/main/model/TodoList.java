package main.model;

import main.model.exception.NegativeListIndexException;
import main.model.exception.TooLargeListIndexException;
import main.util.SaveLoad;

import java.io.IOException;
import java.util.ArrayList;


public class TodoList {
    private static String FILE_PATH = "save.json";

    private SaveLoad saveLoad;

    private ArrayList<Todo> list;

    public TodoList() {
        list = new ArrayList<>();
        saveLoad = new SaveLoad();
    }

    public void changeName(int edit, String newName) {
        Todo temp = list.get(edit);
        temp.setName(newName);
    }

    public void changeDue(int edit, String newDue) {
        Todo temp = list.get(edit);
        temp.setDue(newDue);
    }

    public void changeStatus(int edit, int status) {
        Todo temp = list.get(edit);
        temp.setStatus(status);
    }

    public void addRegTodo(String newTodoName, String newTodoDue) {
        list.add(new RegTodo(newTodoName, newTodoDue));
    }

    public void addSuperTodo(String newTodoName, String newTodoDue) {
        list.add(new SuperTodo(newTodoName, newTodoDue));
    }

    public void deleteTodo(int del) throws NegativeListIndexException, TooLargeListIndexException {
        if (del < 0) {
            throw new NegativeListIndexException();
        } else {
            if (del >= size()) {
                throw new TooLargeListIndexException();
            }
        }
        list.remove(del);
    }

    public void printTodoList() {
        System.out.println();
        int i = 0;
        if (list.isEmpty()) {
            System.out.println("Todo List is Empty");
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

    public void addSuperTodoSub(int index, String name, String due) {
        SuperTodo t = (SuperTodo) list.get(index);
        RegTodo temp = new RegTodo(name, due, false, "Sub");
        t.addSubTodo(temp);
    }

    public void removeSuperTodoSub(int superIndex, int subIndex) throws TooLargeListIndexException,
            NegativeListIndexException {
        SuperTodo t = (SuperTodo) list.get(superIndex);
        t.removeSubTodo(subIndex);
    }

    public void changeSuperTodoSubStatus(int superIndex, int subIndex, int status) throws TooLargeListIndexException,
            NegativeListIndexException {
        SuperTodo t = (SuperTodo) list.get(superIndex);
        t.changeSubTodoStatus(subIndex, status);
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

    public void save(String fileName) throws IOException {
        String arg;
        if (fileName == null) {
            arg = FILE_PATH;
        } else {
            arg = fileName;
        }
        saveLoad.write(list, arg);
        System.out.println("Save Successful!");
    }


    public void load(String fileName) throws IOException {
        String arg;
        if (fileName == null) {
            arg = FILE_PATH;
        } else {
            arg = fileName;
        }

        list = saveLoad.load(arg);
        System.out.println("Load Successful!");
    }
}


