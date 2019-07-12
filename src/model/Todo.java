package model;

public class Todo {

    private String name;
    private String due;
    private boolean status;


    public Todo(String name, String due){
        this.name = name;
        this.due = due;
        this.status = false;
    }

    public Todo(){
        this.name = "";
        this.due = "";
        this.status = false;
    }

    public String getName(){
        return name;
    }

    public String getDue() {
        return due;
    }

    public boolean getStatus(){
        return status;
    }

    public void setStatus(int i){
        this.status = (i==1);
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setDue(String newDue){
        this.due = newDue;
    }

}

