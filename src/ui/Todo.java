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

    public void printStatus(){
        if(status) System.out.println("Task Finished");
        else System.out.println("Task Incomplete");
    }

    public void changeStatus(){
        this.status = !this.status;
    }

    public void changeName(String newName){
        System.out.println("Todo name changed from " + "'" + this.name + "' " + "to " +
                "'" + newName + "'");
        this.name = newName;
    }
}

