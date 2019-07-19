package util;

import model.RegTodo;
import model.SuperTodo;
import model.Todo;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class LoadTodoList {
    private String inputFileName;

    public LoadTodoList(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public ArrayList<Todo> load() throws IOException {
        SuperTodo tempSuper = new SuperTodo("temp", "temp");
        ArrayList<Todo> t = new ArrayList<>();
        List<String> lines = readAllLines(Paths.get(inputFileName));
        for (String line : lines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);
            int size = partsOfLine.size();
            if (partsOfLine.get(size - 1).equals("Super")) {
                tempSuper = (SuperTodo)ConvertLineToTodo(partsOfLine);
                t.add(tempSuper);
            } else {
                if (partsOfLine.get(size - 1).equals("Sub")) {
                    tempSuper.addSubTodo(ConvertLineToTodo(partsOfLine));
                } else {
                    if (partsOfLine.get(size - 1).equals("Reg")) {
                        t.add(ConvertLineToTodo(partsOfLine));
                    }
                }
            }
        }
        return t;
    }

    private Todo ConvertLineToTodo(List<String> partsOfLine) {
        int size = partsOfLine.size();
        String due = partsOfLine.get(size - 3);
        String status = partsOfLine.get(size - 2);
        String type = partsOfLine.get(size - 1);
        partsOfLine.remove(size - 1);
        partsOfLine.remove(size - 2);
        partsOfLine.remove(size - 3);
        String name = partsOfLine.get(0);
        partsOfLine.remove(0);
        for (String word : partsOfLine) {
            name += " " + word;
        }
        if (type.equals("Super")) {
            return (new SuperTodo(name, due, Boolean.parseBoolean(status)));
        }
        if (type.equals("Reg")) {
            return (new RegTodo(name, due, Boolean.parseBoolean(status)));
        }
        if (type.equals("Sub")) {
            return (new RegTodo(name, due, Boolean.parseBoolean(status), "Sub"));
        }
        return new RegTodo(name,due);
    }

    private static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }
}
