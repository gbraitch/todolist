package ui.controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import model.exception.OutOfBoundListIndexException;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class Controller implements Observer {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private JFXListView<Todo> todoList;

    @FXML
    private JFXProgressBar progressBar;

    @FXML
    private Label errorLabel;

    @FXML
    private Label currentTemp;

    @FXML
    private Label weatherDescription;

    @FXML
    private Label minTemp;

    @FXML
    private Label maxTemp;

    @FXML
    private Label currentDate;



    private ObservableList<Todo> list = FXCollections.observableArrayList();
    private TodoList todos;
    private Weather weather;


    @FXML
    void deleteTodo(ActionEvent event) {
        if (todoList.getSelectionModel().isEmpty()) {
            printError("Error. Must select Todo to delete");
        } else {
            Todo temp = todoList.getSelectionModel().getSelectedItem();
            if (!temp.getType().equals("Sub")) {
                try {
                    todos.removeTodo(temp);
                } catch (OutOfBoundListIndexException e) {
                    System.out.println("Error deleting todo");
                }
                todoList.getSelectionModel().clearSelection();
            } else {
                SubTodo st = (SubTodo) temp;
                todos.removeSuperTodoSub(st);
            }
            todoList.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void setStatus(ActionEvent event) {
        if (todoList.getSelectionModel().isEmpty()) {
            printError("Error. Must select Todo to change status");
        } else {
            Todo temp = todoList.getSelectionModel().getSelectedItem();
            if (temp.getStatus()) {
                temp.setStatus(false);
            } else {
                temp.setStatus(true);
            }
            todoList.refresh();
            todoList.getSelectionModel().clearSelection();
            calculateprogress();
        }
    }

    @FXML
    void showNewItemDialog(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/gui/addWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            AddTodoController controller = fxmlLoader.getController();
            controller.setTodoList(todos);
            stage.initOwner(mainAnchorPane.getScene().getWindow());
            stage.setTitle("Add New Todo Item");
            stage.setScene(new Scene(root1));
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error loading new window");
            e.printStackTrace();
        }
    }

    @FXML
    void showEditItemDialog(ActionEvent event) {
        if (todoList.getSelectionModel().isEmpty()) {
            printError("Error. Must select Todo to edit");
        } else {
            if (!todoList.getSelectionModel().getSelectedItem().getType().equals("Super")) {
                openEditWindow();
            } else {
                openEditSuperWindow();
            }
            todoList.getSelectionModel().clearSelection();
            todoList.refresh();
        }
    }

    private void openEditWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/editWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            EditTodoController controller = fxmlLoader.getController();
            controller.setTodoList(todos, todoList.getSelectionModel().getSelectedItem());
            stage.initOwner(mainAnchorPane.getScene().getWindow());
            stage.setTitle("Edit Todo Item");
            stage.setScene(new Scene(root1));
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error loading new window");
            e.printStackTrace();
        }
    }

    private void openEditSuperWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/gui/editSuperWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            EditSuperController controller = fxmlLoader.getController();
            controller.setTodoList(todos, todoList.getSelectionModel().getSelectedItem());
            stage.initOwner(mainAnchorPane.getScene().getWindow());
            stage.setTitle("Edit Super Todo Item");
            stage.setScene(new Scene(root1));
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error loading new window");
            e.printStackTrace();
        }
    }

    private void printError(String text) {
        errorLabel.setText(text);
        errorLabel.setTextFill(Color.WHITE);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), errorLabel);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.play();
    }

    private void calculateprogress() {
        double value = todos.getCompleted() / todos.getTotalTodos();
        progressBar.setProgress(value);
        System.out.println(value);
    }

    @FXML
    void initialize() {

    }

    public void init(TodoList todos) throws IOException {
        this.todos = todos;
        todos.load(null);
        calculateprogress();
        weather = new Weather();
        currentDate.setText(LocalDate.now().toString());
        currentTemp.setText(weather.getCurrentTemp() + "°C");
        maxTemp.setText(weather.getMaxTemp() + " °C");
        minTemp.setText(weather.getMinTemp() + " °C");
        weatherDescription.setText(weather.getDescription());
    }

    @Override
    public void update(Observable o, Object arg) {
        ObservableList<Todo> temp = (ObservableList<Todo>) arg;
        transferList(temp);
        todoList.setItems(this.list);
    }

    public void transferList(ObservableList<Todo> temp) {
        this.list.clear();
        for (Todo t : temp) {
            this.list.add(t);
            if (t.getType().equals("Super")) {
                SuperTodo tempSuper = (SuperTodo) t;
                for (Todo st : tempSuper.getSubList()) {
                    this.list.add(st);
                }
            }
        }
    }
}
