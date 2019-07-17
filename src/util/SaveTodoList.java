package util;


import model.Todo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SaveTodoList{
    private PrintWriter writer;

    public SaveTodoList(String outputFileName) throws IOException {
        this.writer = new PrintWriter(outputFileName, "UTF-8");
    }

    public void save(ArrayList<Todo> list){
        List<String> lines = ConvertTodoToString(list);
        for(String line : lines) {
            writer.println(line);
        }
        writer.close();
    }

    private ArrayList<String> ConvertTodoToString(List<Todo> list){
        ArrayList<String> lines = new ArrayList<>();
        for(Todo t : list) {
            String name = t.getName();
            String due = t.getDue();
            boolean status = t.getStatus();
            lines.add(name + " " + due + " " + status);
        }
        return lines;
    }
}
