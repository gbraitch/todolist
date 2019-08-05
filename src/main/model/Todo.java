package model;

import java.util.Objects;

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

    public void setStatus(boolean i) {
        this.status = i;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Todo)) {
            return false;
        }
        Todo todo = (Todo) o;
        return status == todo.status && name.equals(todo.name)
                && due.equals(todo.due) && type.equals(todo.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, due, status, type);
    }

    @Override
    public String toString() {
        String[] dateArray = due.toString().split("-");
        String status;
        if (this.status) {
            status = "Finished";
        } else {
            status = "Incomplete";
        }

        return name + " -- " + dateArray[2] + "/" + dateArray[1] + "/"
                + dateArray[0] + " -- " + status;
    }
}
