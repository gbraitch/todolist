package ui;

public class Task {

    private String name;
    private String due;
    private boolean status;


    public Task(String name, String due){
        this.name = name;
        this.due = due;
        this.status = false;
    }

    private void printName(){
        System.out.println("Task name: " + name);
    }

    private void printDue() {
        System.out.println("Task Due Date: " + due);
    }

    private void printStatus(){
        if(status) System.out.println("Task Finished");
        else System.out.println("Task Incomplete");
    }

    private void changeStatus(){
        this.status = !this.status;
    }

    private void changeName(String newName){
        System.out.println("Task name changed from " + "'" + this.name + "' " + "to " +
                "'" + newName + "'");
        this.name = newName;
    }

    public static void main(String[] args) {
        Task t1 = new Task("Finish CPSC210 HWK", "Saturday");
        t1.printName();
        t1.printDue();
        t1.printStatus();
        t1.changeStatus();
        t1.printStatus();
        t1.changeName("CPSC 210 HWK DONE");
        t1.printName();
    }

}

