package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import model.RegTodo;
import model.SuperTodo;
import model.Todo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SaveLoad {

    private List<Todo> books = new ArrayList<>();
    private static String FILE_PATH = "save.json";
    private Gson gson;

    public SaveLoad() {
        gson = setupGson();
    }

    // Helper method to create a Gson instance that serializes abstract classes.
    private static Gson setupGson() {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();

        RuntimeTypeAdapterFactory<Todo> myAdapterFactory
                = RuntimeTypeAdapterFactory.of(Todo.class, "userType");

        myAdapterFactory.registerSubtype(RegTodo.class, "RegTodo");
        myAdapterFactory.registerSubtype(SuperTodo.class, "SuperTodo");

        gsonBuilder.registerTypeAdapterFactory(myAdapterFactory);

        return gsonBuilder.create();
    }

    public void write(List<Todo> list, String... fileName) {
        String outputFileName;

        if (fileName.length > 0) {
            outputFileName = fileName[0];
        } else {
            outputFileName = FILE_PATH;
        }

        try (FileWriter jsonWriter = new FileWriter(outputFileName)) {
            String json = gson.toJson(list.toArray(), Todo[].class);
            jsonWriter.write(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Todo> load(String... fileName) {
        ArrayList<Todo> todos = new ArrayList<>();
        String inputFileName;

        if (fileName.length > 0) {
            inputFileName = fileName[0];
        } else {
            inputFileName = FILE_PATH;
        }

        try (FileReader r = new FileReader(inputFileName))  {
            JsonReader jsonReader = new JsonReader(r);

            Todo[] todoArray = gson.fromJson(jsonReader, Todo[].class);
            todos.addAll(Arrays.asList(todoArray));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return todos;
    }
}
