package ui;

import model.TodoList;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private Scanner scanner = new Scanner(System.in);
    private TodoList todo;

    private void printMenu() {
        System.out.print("\nEnter 0: Create new Todo");
        System.out.println("                  1: Delete Todo");
        System.out.print("      2: Edit Todo");
        System.out.println("                        3: View Todo List");
        System.out.print("      4: Save Todo List to save file");
        System.out.println("      5: Load Todo List from save file");
        System.out.println("      6: Exit");
    }

    private void printEditMenu(String type) {
        System.out.println("\n0: Change Name");
        System.out.println("1: Change Due Date");
        System.out.println("2: Change Completion Status");
        if (type.equals("Super")) {
            System.out.println("3: Delete SubTodo");
            System.out.println("4: Add SubTodo");
            System.out.println("5: Change SubTodo Status");
        }
    }

    public Main() {
        todo = new TodoList();
        boolean loop = true;
        while (loop) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "0":
                    try {
                        addTodo();
                    } catch (InvalidChoiceException e) {
                       System.out.println("Invalid Choice");
                    } finally {
                        break;
                    }
                case "1":
                    deleteTodo();
                    break;
                case "2":
                    editTodo();
                    break;
                case "3":
                    todo.printTodoList();
                    scanner.nextLine();
                    break;
                case "4":
                    try {
                        todo.save();
                    } catch (IOException e) {
                        System.out.println("Save Unsuccessful");
                    }
                    break;
                case "5":
                    try {
                        todo.load();
                    } catch (IOException e) {
                        System.out.println("Load Unsuccessful");
                    }
                    break;
                case "6":
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }

    public void editSubTodo(int index, int choice) {
        todo.printSuperTodoSubList(index);
        int edit;
        switch (choice) {
            case 3:
                System.out.println("Which sub Todo to delete?");
                edit = Integer.parseInt(scanner.nextLine());
                todo.removeSuperTodoSub(index,edit);
                break;
            case 4:
                System.out.println("Choose a name for the todo!");
                String newTodoName = scanner.nextLine();
                System.out.println("Choose a due date for the todo!");
                String newTodoDue = scanner.nextLine();
                todo.addSuperTodoSub(index, newTodoName, newTodoDue);
                break;
            case 5:
                System.out.println("Which sub Todo to change status?");
                edit = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter 1 to finish task or 0 for unfinished task");
                int finishStatus = Integer.parseInt(scanner.nextLine());
                todo.changeSuperTodoSubStatus(index, edit, finishStatus);
                break;
            default:
                System.out.println("Invalid Choice");
                break;
        }
    }

    public void editTodo() {
        todo.printTodoList();
        System.out.println("\nWhich Todo to edit?");
        String edit = scanner.nextLine();
        System.out.println("\nWhat would you like to change?");
        if (todo.getTodoType(Integer.parseInt(edit)).equals("Super")) {
            printEditMenu("Super");
            String editChoice = scanner.nextLine();
            switch (editChoice) {
                case "0":
                    System.out.println("Enter new name for todo");
                    String newName = scanner.nextLine();
                    todo.changeName(Integer.parseInt(edit), newName);
                    break;
                case "1":
                    System.out.println("Enter new due date for todo");
                    String newDue = scanner.nextLine();
                    todo.changeDue(Integer.parseInt(edit), newDue);
                    break;
                case "2":
                    System.out.println("Enter 1 to finish task or 0 for unfinished task");
                    String finishStatus = scanner.nextLine();
                    todo.changeStatus(Integer.parseInt(edit), Integer.parseInt(finishStatus));
                    break;
                case "3": editSubTodo(Integer.parseInt(edit), 3);
                          break;
                case "4": editSubTodo(Integer.parseInt(edit), 4);
                          break;
                case "5": editSubTodo(Integer.parseInt(edit), 5);
                          break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
        if (todo.getTodoType(Integer.parseInt(edit)).equals("Reg")) {
            printEditMenu("Reg");
            String editChoice = scanner.nextLine();
            switch (editChoice) {
                case "0":
                    System.out.println("Enter new name for todo");
                    String newName = scanner.nextLine();
                    todo.changeName(Integer.parseInt(edit), newName);
                    break;
                case "1":
                    System.out.println("Enter new due date for todo");
                    String newDue = scanner.nextLine();
                    todo.changeDue(Integer.parseInt(edit), newDue);
                    break;
                case "2":
                    System.out.println("Enter 1 to finish task or 0 for unfinished task");
                    String finishStatus = scanner.nextLine();
                    todo.changeStatus(Integer.parseInt(edit), Integer.parseInt(finishStatus));
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }

    public void addTodo() throws InvalidChoiceException {
        System.out.println("Which type of todo: 0 for Regular Todo");
        System.out.println("                    1 for Super Todo");
        int choice = Integer.parseInt(scanner.nextLine());
        String newTodoName;
        String newTodoDue;
        switch (choice) {
            case 0:
                System.out.println("Choose a name for the todo!");
                newTodoName = scanner.nextLine();
                System.out.println("Choose a due date for the todo!");
                newTodoDue = scanner.nextLine();
                todo.addRegTodo(newTodoName, newTodoDue);
                break;
            case 1:
                System.out.println("Choose a name for the todo!");
                newTodoName = scanner.nextLine();
                System.out.println("Choose a due date for the todo!");
                newTodoDue = scanner.nextLine();
                todo.addSuperTodo(newTodoName, newTodoDue);
                break;
            default:
                throw new InvalidChoiceException();
        }
    }

    public void deleteTodo() {
        todo.printTodoList();
        System.out.println("\nWhich Todo to remove?");
        int del = Integer.parseInt(scanner.nextLine());
        todo.deleteTodo(del);
    }


    public static void main(String[] args) {
        new Main();
    }
}
