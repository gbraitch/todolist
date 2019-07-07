package ui;

public class Todo {

    private String name;
    private String due;
    private boolean status;


    public Todo(String name, String due){
        this.name = name;
        this.due = due;
        this.status = false;
    }

    public String getName(){
        return name;
    }

    public String getDue() {
        return due;
    }

    public boolean getFinished(){
        return status;
    }

    public void changeStatus(int i){
        this.status = (i==1);
    }

    public void changeName(String newName){
        this.name = newName;
    }

    public void changeDue(String newDue){
        this.due = newDue;
    }

}

