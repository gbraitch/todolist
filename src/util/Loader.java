package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Todo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Loader {
    public Loader(ArrayList<Todo> td) throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("save.JSON")) {
            gson.toJson(td, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Todo> loadSaved() {
        Gson gson = new Gson();
        ArrayList<Todo> list = new ArrayList<>();
        Type listofTodo = new TypeToken<ArrayList<Todo>>(){}.getType();
        try (Reader reader = new FileReader("save.JSON")) {
            list = gson.fromJson(reader, listofTodo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
//        ArrayList<Todo> list = new ArrayList<>();
//        RegTodo td1 = new RegTodo("Regtodo1", "wednedsay");
//        RegTodo td2 = new RegTodo("Regtodo2", "friday");
//        SuperTodo sd1 = new SuperTodo("SUpertodo1", "next week");
//        sd1.setStatus(1);
//        RegTodo td3 = new RegTodo("Regtodo3", "thurs", true);
//        sd1.addSubTodo(td3);
//        list.add(td1);
//        list.add(td2);
//        list.add(sd1);
//        new Loader(list);
        ArrayList<Todo> list2 = new ArrayList<>();
        list2 = loadSaved();
        list2.get(0).printTodo(1);
        list2.get(1).printTodo(1);
        list2.get(2).printTodo(1);

    }
}
