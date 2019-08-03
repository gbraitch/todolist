package model;

import model.exception.NegativeListIndexException;
import model.exception.TooLargeListIndexException;
import util.SaveLoad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TodoList {
    private static String FILE_PATH = "save.json";

    private SaveLoad saveLoad;
    private ArrayList<Todo> list;
    private Map<String, ArrayList<Todo>> map = new HashMap<>();

    public TodoList() {
        list = new ArrayList<>();
        saveLoad = new SaveLoad();
        map.put("Super", new ArrayList<Todo>());
        map.put("Sub", new ArrayList<Todo>());
        map.put("Reg", new ArrayList<Todo>());
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
        RegTodo temp = new RegTodo(newTodoName, newTodoDue);
        list.add(temp);
        map.get("Reg").add(temp);
    }

    public void addSuperTodo(String newTodoName, String newTodoDue) {
        SuperTodo temp = new SuperTodo(newTodoName, newTodoDue);
        list.add(temp);
        map.get("Super").add(temp);
    }

    public void deleteTodo(int del) throws NegativeListIndexException, TooLargeListIndexException {
        checkIndex(del);
        list.remove(del);
    }

    public void printAllTodo() {
        System.out.println();
        printList(this.list);
    }

    public void printOnlySubTodos() {
        ArrayList<Todo> list = map.get("Sub");
        printList(list);
    }

    public void printOnlySuperTodos() {
        ArrayList<Todo> list = map.get("Super");
        printList(list);
    }

    public void printOnlyRegTodos() {
        ArrayList<Todo> list = map.get("Reg");
        printList(list);
    }

    private void printList(ArrayList<Todo> list) {
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

    public void addSuperTodoSub(int index, String name, String due) {
        SuperTodo t = (SuperTodo) list.get(index);
        SubTodo temp = new SubTodo(name, due, false);
        t.addSubTodo(temp);
        map.get("Sub").add(temp);
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

    public void save(String fileName) {
        String arg;
        if (fileName == null) {
            arg = FILE_PATH;
            saveLoad.save(map.get("Reg"), "saveHashMapReg.json");
            saveLoad.save(map.get("Super"), "saveHashMapSup.json");
            saveLoad.save(map.get("Sub"), "saveHashMapSub.json");
        } else {
            arg = fileName;
        }
        saveLoad.save(list, arg);
    }

    public void load(String fileName) {
        String arg;
        if (fileName == null) {
            arg = FILE_PATH;
            map.put("Reg",saveLoad.load("saveHashMapReg.json"));
            map.put("Super",saveLoad.load("saveHashMapSup.json"));
            map.put("Sub",saveLoad.load("saveHashMapSub.json"));
        } else {
            arg = fileName;
        }
        list = saveLoad.load(arg);
        loadSubTodoProperties();
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


