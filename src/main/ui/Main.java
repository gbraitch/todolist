package ui;

import com.jfoenix.controls.JFXDecorator;
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
        Stage stage = new Stage();

        JFXDecorator decorator = new JFXDecorator(stage, root);
        decorator.setCustomMaximize(true);

        Scene scene = new Scene(decorator);

        String css = this.getClass().getResource("/ui/assets/styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("Todo List");
        stage.setScene(scene);
        stage.setResizable(true);

        Controller controller = fxmlLoader.getController();
        todos = new TodoList(controller);
        controller.init(todos);

        stage.show();
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