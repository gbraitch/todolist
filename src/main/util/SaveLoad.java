package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import model.Todo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SaveLoad {
    private Gson gson;

    public SaveLoad() {
        gson = setupGson();
    }

    // Helper method to create a Gson instance that serializes abstract classes.
    private static Gson setupGson() {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Todo.class, new InterfaceAdapter<Todo>());

        return gsonBuilder.create();
    }

    public void save(List<Todo> list, String fileName) {
        try (FileWriter jsonWriter = new FileWriter(fileName)) {
            String json = gson.toJson(list.toArray(), Todo[].class);
            jsonWriter.write(json);
            System.out.println("Write operation SUCCESS");
        } catch (IOException e) {
            System.out.println("Write operation FAIL");
        }
    }

    public ArrayList<Todo> load(String fileName) {
        ArrayList<Todo> todos = new ArrayList<>();

        try (FileReader r = new FileReader(fileName))  {
            JsonReader jsonReader = new JsonReader(r);
            Todo[] todoArray = gson.fromJson(jsonReader, Todo[].class);
            todos.addAll(Arrays.asList(todoArray));
            System.out.println("Read operation SUCCESS");
        } catch (IOException e) {
            System.out.println("Read operation FAIL");
        }

        return todos;
    }
}
