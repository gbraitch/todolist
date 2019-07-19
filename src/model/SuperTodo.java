package model;

import java.util.ArrayList;

public class SuperTodo extends Todo {
    private ArrayList<Todo> SubList;

    public SuperTodo(String name, String due) {
        super(name, due, false, "Super");
        SubList = new ArrayList<>();
    }

    public SuperTodo(String name, String due, boolean status) {
        super(name, due, status, "Super");
        SubList = new ArrayList<>();
    }

    public void addSubTodo(Todo t) {
        SubList.add(t);
    }

    public void removeSubTodo(int i) {
        if (i < 0 | i >= SubList.size()) {
            //stub
        } else {
            SubList.remove(i);
        }
    }

    public void changeSubTodoStatus(int index, int status) {
        SubList.get(index).setStatus(status);
    }

    public ArrayList<String> ReturnListofSubTodos() {
        ArrayList<String> lines = new ArrayList<>();
        for (Todo t : SubList) {
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
        System.out.print("★[" + i + "]  ");
        if (this.status) {
            System.out.println(this.name + "   :   " + this.due + "   " + "☑");
        } else {
            System.out.println(this.name + "   :   " + this.due + "   " + "☐");
        }
        printSubList();
    }

    public void printSubList() {
        int j = 0;
        for (Todo s : SubList) {
            System.out.print("     ");
            s.printTodo(j);
            j++;
        }
    }
}
