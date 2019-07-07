package ui;

import java.util.ArrayList;
import java.util.Scanner;

public class TodoList {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Todo> list = new ArrayList<>();


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
                    System.out.println("You chose 2");
                    break;
                case "3":
                    printTodoList();
                    scanner.nextLine();
                    break;
            }
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
            System.out.println(td.getName() + "   :   " + td.getDue());
            i++;
        }
    }

    public void printMenu(){
        System.out.println("\nEnter 0: Create new Todo");
        System.out.println("      1: Delete Todo");
        System.out.println("      2: Edit Todo");
        System.out.println("      3: View Todo List");
    }

    public static void main(String[] args) {
            new TodoList();
        }
    //}
}
