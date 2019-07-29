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

    public void addSubTodo(Todo t) {
        subList.add(t);
    }

    public void removeSubTodo(int index) throws NegativeListIndexException, TooLargeListIndexException {
        checkIndex(index);
        subList.remove(index);
    }

    public void changeSubTodoStatus(int index, int status)
            throws NegativeListIndexException, TooLargeListIndexException {
        checkIndex(index);
        subList.get(index).setStatus(status);
    }

    public ArrayList<Todo> getSubList() {
        return subList;
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
    public void printTodo(int i) {
        //System.out.print(ConsoleColors.GREEN_UNDERLINED);
        System.out.print("★[" + i + "]  ");
        if (this.status) {
            System.out.println(this.name + "   :   " + this.due + "   " + "☑");
        } else {
            System.out.println(this.name + "   :   " + this.due + "   " + "☐");
        }
        //System.out.print(ConsoleColors.RESET);
        printSubList();
    }

    public void printSubList() {
        int j = 0;
        for (Todo s : subList) {
            System.out.print("      ");
            s.printTodo(j);
            j++;
        }
    }
}
