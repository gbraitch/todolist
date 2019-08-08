package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Objects;

public abstract class Todo {
    protected String name;
    protected String due;
    protected String type;
    protected BooleanProperty status = new SimpleBooleanProperty();

    public Todo(String name, String due, boolean status, String type) {
        this.name = name;
        this.due = due;
        this.status.set(status);
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDue() {
        return due;
    }

    public boolean getStatus() {
        return this.status.get();
    }

    public void setStatus(boolean i) {
        this.status.set(i);
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

    public BooleanProperty getBooleanProperty() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Todo todo = (Todo) o;
        return name.equals(todo.name) && due.equals(todo.due) && type.equals(todo.type)
                && (status.get() == todo.status.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, due, type, status);
    }

    @Override
    public String toString() {
        String[] dateArray = due.toString().split("-");
        String status;
        if (this.status.get()) {
            status = "Finished";
        } else {
            status = "Incomplete";
        }
        String namer = this.name;
        while (namer.length() < 21) {
            namer = namer + " ";
        }
        String date = dateArray[1] + "/" + dateArray[2] + "/"
                + dateArray[0];
        return String.format("%-22s %20s", namer, date);


    }
}
