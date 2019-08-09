package model;

import model.exception.NegativeListIndexException;
import model.exception.TooLargeListIndexException;

import java.util.ArrayList;

public class SuperTodo extends Todo {
    private ArrayList<Todo> subList;

    public SuperTodo(String name, String due) {
        super(name, due, false, "Super");
        subList = new ArrayList<>();
    }

    public SuperTodo(String name, String due, boolean status) {
        super(name, due, status, "Super");
        subList = new ArrayList<>();
    }

    public ArrayList<Todo> getSubList() {
        return subList;
    }

    public void addSubTodo(SubTodo t) {
        if (!subList.contains(t)) {
            subList.add(t);
            t.addSuper(this);
        }
    }

    public void removeSubTodo(SubTodo st) {
        subList.remove(st);
    }

    public void changeSubTodoStatus(int index, boolean status)
            throws NegativeListIndexException, TooLargeListIndexException {
        checkIndex(index);
        subList.get(index).setStatus(status);
    }

    public int completedSubTodos() {
        int j = 0;
        for (Todo t : subList) {
            if (t.getStatus()) {
                j++;
            }
        }
        return j;
    }

    private void checkIndex(int index) throws NegativeListIndexException, TooLargeListIndexException {
        if (index < 0) {
            throw new NegativeListIndexException();
        }
        if (index >= subList.size()) {
            throw new TooLargeListIndexException();
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
