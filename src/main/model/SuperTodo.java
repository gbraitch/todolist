package main.model;

import main.model.exception.NegativeListIndexException;
import main.model.exception.TooLargeListIndexException;

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
        if (index < 0) {
            throw new NegativeListIndexException();
        }
        if (index >= subList.size()) {
            throw new TooLargeListIndexException();
        }
        subList.remove(index);
    }

    public void changeSubTodoStatus(int index, int status)
            throws NegativeListIndexException, TooLargeListIndexException {
        if (index < 0) {
            throw new NegativeListIndexException();
        }
        if (index >= subList.size()) {
            throw new TooLargeListIndexException();
        }
        subList.get(index).setStatus(status);
    }

    public ArrayList<Todo> getSubList() {
        return subList;
    }

    public ArrayList<String> returnListofSubTodos() {
        ArrayList<String> lines = new ArrayList<>();
        for (Todo t : subList) {
            String name = t.getName();
            String due = t.getDue();
            boolean status = t.getStatus();
            String type = t.getType();
            lines.add(name + " " + due + " " + status + " " + type);
        }
        return lines;
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