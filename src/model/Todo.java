package model;

public class Todo {

    private String name;
    private String due;
    private boolean status;
    private static String CHECKMARK = Character.toString((char)10003);

    public Todo(String name, String due){
        this.name = name;
        this.due = due;
        this.status = false;
    }

    public Todo(String name, String due, boolean status){
        this.name = name;
        this.due = due;
        this.status = status;
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

    public void printTodo(int i){
        System.out.print("[" + i + "]  ");
        if (this.status) {
            System.out.println(this.name + "   :   " + this.due + "   " + CHECKMARK);
        } else {
            System.out.println(this.name + "   :   " + this.due);
        }
    }

}

