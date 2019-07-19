package model;

public abstract class Todo {
    protected String name;
    protected String due;
    protected boolean status;
    protected String type;

    public Todo(String name, String due, boolean status, String type) {
        this.name = name;
        this.due = due;
        this.status = status;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDue() {
        return due;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(int i) {
        this.status = (i == 1);
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setDue(String newDue) {
        this.due = newDue;
    }

    public String getType() {
        return this.type;
    }

    public abstract void printTodo(int i);
}
