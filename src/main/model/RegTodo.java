package model;

public class RegTodo extends Todo {

    public RegTodo(String name, String due) {
        super(name, due, false, "Reg");
    }

    public RegTodo(String name, String due, boolean status) {
        super(name, due, status, "Reg");
    }

    @Override
    public String toString() {
        return " • " + super.toString();
    }
}

