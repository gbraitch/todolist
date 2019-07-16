package model;

import util.Loadable;
import util.Saveable;

import java.io.IOException;
import java.util.ArrayList;


public class TodoList {
    private static String CHECKMARK = Character.toString((char)10003);
    private static String inputFileName = "saveFile.txt";
    private static String outputFileName= "saveFile.txt";

    private ArrayList<Todo> list;

    public TodoList(){
        list = new ArrayList<>();
    }

    public void changeName(int edit, String newName){
        Todo temp = list.get(edit);
        temp.setName(newName);
    }
    public void changeDue(int edit, String newDue){
        Todo temp = list.get(edit);
        temp.setDue(newDue);
    }
    public void changeStatus(int edit, int status){
        Todo temp = list.get(edit);
        temp.setStatus(status);
    }

    public void addTodo(String newTodoName, String newTodoDue){
        list.add(new Todo (newTodoName, newTodoDue));
    }

    public void deleteTodo(int del){
        list.remove(del);
    }

    public void printTodoList(){
        System.out.println();
        int i = 0;
        if(list.isEmpty()){
            System.out.println("Todo List is Empty");
        }
        else {
            for (Todo td : list) {
                System.out.print("[" + i + "]  ");
                if (td.getStatus()) {
                    System.out.println(td.getName() + "   :   " + td.getDue() + "   " + CHECKMARK);
                } else {
                    System.out.println(td.getName() + "   :   " + td.getDue());
                }
                i++;
            }
        }
    }

    public String getTodoName(int index){
        return list.get(index).getName();
    }

    public String getTodoDue(int index){
        return list.get(index).getDue();
    }

    public boolean getTodoStatus(int index){
        return list.get(index).getStatus();
    }

    public int size(){
        return list.size();
    }

    public void save() throws IOException {
        Saveable s = new Saveable(outputFileName);
        ArrayList<String> lines = new ArrayList<>();
        for(Todo t : list) {
            String name = t.getName();
            String due = t.getDue();
            boolean status = t.getStatus();
            lines.add(name + " " + due + " " + status);
        }
        s.save(lines);
        System.out.println("Save Successful!");
    }

    public void load() throws IOException {
        Loadable l = new Loadable(outputFileName);
        list = l.load();
        System.out.println("Load Successful!");
    }
}

