package model;

public class RegTodo extends Todo {

    public RegTodo(String name, String due) {
        super(name, due, false, "Reg");
    }

    public RegTodo(String name, String due, boolean status) {
        super(name, due, status, "Reg");
    }

    public RegTodo(String name, String due, boolean status, String type) {
        super(name, due, status, type);
    }

    @Override
    public void printTodo(int i) {
        System.out.print("☆[" + i + "]  ");
        if (this.status) {
            System.out.println(this.name + "   :   " + this.due + "   " + "☑");
        } else {
            System.out.println(this.name + "   :   " + this.due + "   " + "☐");
        }
    }

}

