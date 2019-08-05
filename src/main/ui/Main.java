package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.TodoList;
import ui.controller.Controller;

public class Main extends Application {
    private TodoList todos;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/TodoGUI.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        primaryStage.setTitle("To-Do List Application");
        primaryStage.setScene(new Scene(root, 428, 735));
        primaryStage.setResizable(false);

        Controller controller = fxmlLoader.getController();
        todos = new TodoList(controller);
        controller.init(todos);

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        todos.save(null);
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}