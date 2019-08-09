package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Todo;
import org.hildan.fxgson.FxGson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class SaveLoad {
    private Gson gson;

    public SaveLoad() {
        gson = setupGson();
    }

    // Helper method to create a Gson instance that serializes abstract classes.
    private static Gson setupGson() {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Todo.class, new InterfaceAdapter<Todo>());

        return FxGson.addFxSupport(gsonBuilder).create();
    }

    public void save(ObservableList<Todo> list, String fileName) throws IOException {
        try (FileWriter jsonWriter = new FileWriter(fileName)) {
            String json = gson.toJson(list.toArray(), Todo[].class);
            jsonWriter.write(json);
            System.out.println("Write operation SUCCESS");
        }
    }

    public ObservableList<Todo> load(String fileName) {
        ObservableList<Todo> todos = FXCollections.observableArrayList();

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
