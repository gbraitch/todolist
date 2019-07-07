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
                    System.out.println("Choose a name for the todo!");
                    String newTodoName = scanner.nextLine();
                    System.out.println("Choose a due date for the todo!");
                    String newTodoDue = scanner.nextLine();
                    list.add(new Todo (newTodoName, newTodoDue));
                    break;
                case "1":
                    System.out.println("You chose 1");
                    break;
                case "2":
                    System.out.println("You chose 2");
                    break;
                case "3":
                    int i = 0;
                    for (Todo td : list)
                    {
                        System.out.print("["+i+"]  ");
                        System.out.println(td.getName() + "   :   " + td.getDue());
                        i++;
                    }
                    scanner.nextLine();
                    break;
            }
        }
    }

    public static void printMenu(){
        System.out.println("Enter 0: Create new Todo");
        System.out.println("      1: Delete Todo");
        System.out.println("      2: Edit Todo");
        System.out.println("      3: View Todo List");
    }

    public static void main(String[] args) {
            new TodoList();
        }
    //}
}
