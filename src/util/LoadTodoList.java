package util;

import model.Todo;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class LoadTodoList{
    private String inputFileName;

    public LoadTodoList(String inputFileName){
        this.inputFileName = inputFileName;
    }

    public ArrayList<Todo> load() throws IOException {
        ArrayList<Todo> t = new ArrayList<>();
        List<String> lines = readAllLines(Paths.get(inputFileName));
        for(String line : lines){
            ArrayList<String> partsOfLine = splitOnSpace(line);
            t.add(ConvertLineToTodo(partsOfLine));
        }
        return t;
    }

    private Todo ConvertLineToTodo(List<String> partsOfLine) {
        int size = partsOfLine.size();
        String due = partsOfLine.get(size - 2);
        String status = partsOfLine.get(size - 1);
        partsOfLine.remove(size - 1);
        partsOfLine.remove(size - 2);
        String name = partsOfLine.get(0);
        partsOfLine.remove(0);
        for (String word : partsOfLine) {
            name += " " + word;
        }
        return (new Todo(name, due, Boolean.parseBoolean(status)));
    }

    private static ArrayList<String> splitOnSpace(String line){
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }
}
