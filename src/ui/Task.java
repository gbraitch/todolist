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

    private String getName(){
        return this.name;
    }

    private String getDue() {
        return this.due;
    }

    private boolean getStatus(){
        return this.status;
    }

    private void finish(){
        this.status = true;
    }

    public static void main(String[] args) {
        Task t1 = new Task("Finish CPSC210 HWK", "Saturday");
        System.out.println("Task name: " + t1.getName());
        System.out.println("Task Due Date: " + t1.getDue());
        System.out.println("Task Status: " + t1.getStatus());
        t1.finish();
        System.out.println("Task Status: " + t1.getStatus());
    }

}

