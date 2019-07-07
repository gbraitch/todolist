package ui;

import java.util.ArrayList;
import java.util.Scanner;

public class TodoList {
    private static String CHECKMARK = Character.toString((char)10003);

    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Todo> list = new ArrayList<>();

    public TodoList(){
        System.out.println("Welcome!");
        while(true) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "0":
                    addTodo();
                    break;
                case "1":
                    deleteTodo();
                    break;
                case "2":
                    editTodo();
                    break;
                case "3":
                    printTodoList();
                    scanner.nextLine();
                    break;
            }
        }
    }

    public void editTodo(){
        printTodoList();
        System.out.println("\nWhich Todo to edit?");
        String edit = scanner.nextLine();
        Todo temp = list.get(Integer.parseInt(edit));
        System.out.println("\nWhat would you like to change?");
        printEditMenu();
        String editChoice = scanner.nextLine();
        switch(editChoice){
            case "0":
                System.out.println("Enter new name for todo");
                String newName = scanner.nextLine();
                temp.changeName(newName);
                list.set(Integer.parseInt(edit), temp);
                break;
            case "1":
                System.out.println("Enter new due date for todo");
                String newDue = scanner.nextLine();
                temp.changeName(newDue);
                list.set(Integer.parseInt(edit), temp);
                break;
            case "2":
                System.out.println("Enter 1 to finish task or 0 for unfinished task");
                String finish_status = scanner.nextLine();
                temp.changeStatus(Integer.parseInt(finish_status));
                list.set(Integer.parseInt(edit), temp);
                break;
        }
    }

    public void addTodo(){
        System.out.println("Choose a name for the todo!");
        String newTodoName = scanner.nextLine();
        System.out.println("Choose a due date for the todo!");
        String newTodoDue = scanner.nextLine();
        list.add(new Todo (newTodoName, newTodoDue));
    }

    public void deleteTodo(){
        printTodoList();
        System.out.println("\nWhich Todo to remove?");
        String del = scanner.nextLine();
        list.remove(Integer.parseInt(del));
    }

    public void printTodoList(){
        System.out.println();
        int i = 0;
        for (Todo td : list)
        {
            System.out.print("["+i+"]  ");
            if(td.getFinished()){
                System.out.println(td.getName() + "   :   " + td.getDue() + "   " + CHECKMARK);
            }
            else{ System.out.println(td.getName() + "   :   " + td.getDue()); }
            i++;
        }
    }

    public void printMenu(){
        System.out.println("\nEnter 0: Create new Todo");
        System.out.println("      1: Delete Todo");
        System.out.println("      2: Edit Todo");
        System.out.println("      3: View Todo List");
    }

    public void printEditMenu(){
        System.out.println("\n0: Change Name");
        System.out.println("1: Change Due Date");
        System.out.println("2: Change Completion Status");
    }

    public static void main(String[] args) {
            new TodoList();
        }
    //}
}
