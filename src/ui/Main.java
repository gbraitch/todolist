package ui;

import model.TodoList;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private Scanner scanner = new Scanner(System.in);
    private TodoList t;

    private void printMenu() {
        System.out.println("\nEnter 0: Create new Todo");
        System.out.println("      1: Delete Todo");
        System.out.println("      2: Edit Todo");
        System.out.println("      3: View Todo List");
        System.out.println("      4: Save Todo List to save file");
        System.out.println("      5: Load Todo List from save file");
        System.out.println("      6: Exit");
    }

    private void printEditMenu() {
        System.out.println("\n0: Change Name");
        System.out.println("1: Change Due Date");
        System.out.println("2: Change Completion Status");
    }

    public Main() {
        t = new TodoList();
        boolean loop = true;
        while (loop) {
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
                    t.printTodoList();
                    scanner.nextLine();
                    break;
                case "4":
                    try {
                        t.save();
                    } catch (IOException e) {
                        System.out.println("Save Unsuccessful");
                    }
                    break;
                case "5":
                    try {
                        t.load();
                    } catch (IOException e) {
                        System.out.println("Load Unsuccessful");
                    }
                    break;
                case "6":
                    loop = false;
                    break;
            }
        }
    }

    public void editTodo(){
        t.printTodoList();
        System.out.println("\nWhich Todo to edit?");
        String edit = scanner.nextLine();
        System.out.println("\nWhat would you like to change?");
        printEditMenu();
        String editChoice = scanner.nextLine();
        switch(editChoice){
            case "0":
                System.out.println("Enter new name for todo");
                String newName = scanner.nextLine();
                t.changeName(Integer.parseInt(edit), newName);
                break;
            case "1":
                System.out.println("Enter new due date for todo");
                String newDue = scanner.nextLine();
                t.changeDue(Integer.parseInt(edit), newDue);
                break;
            case "2":
                System.out.println("Enter 1 to finish task or 0 for unfinished task");
                String finish_status = scanner.nextLine();
                t.changeStatus(Integer.parseInt(edit), Integer.parseInt(finish_status));
                break;
        }
    }

    public void addTodo(){
        System.out.println("Choose a name for the todo!");
        String newTodoName = scanner.nextLine();
        System.out.println("Choose a due date for the todo!");
        String newTodoDue = scanner.nextLine();
        t.addTodo(newTodoName, newTodoDue);
    }

    public void deleteTodo(){
        t.printTodoList();
        System.out.println("\nWhich Todo to remove?");
        String del = scanner.nextLine();
        t.deleteTodo(Integer.parseInt(del));
    }


    public static void main(String[] args) {
        new Main();
    }
}
