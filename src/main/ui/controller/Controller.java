package ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import model.RegTodo;
import model.Todo;
import model.TodoList;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXButton addTodo;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton changeStatus;

    @FXML
    private JFXTextField descriptionText;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXListView<Todo> todoList;

    private ObservableList<Todo> list = FXCollections.observableArrayList();
    private TodoList todo;

    @FXML
    void addNewTodo(ActionEvent event) {
        if (addTaskValidate()) {
            enterNewTodo();
        }
    }

    @FXML
    void deleteTodo(ActionEvent event) {
        list.remove(todoList.getSelectionModel().getSelectedItem());
        todoList.refresh();
        todoList.getSelectionModel().clearSelection();
    }

    @FXML
    void setStatus(ActionEvent event) {
        Todo temp = todoList.getSelectionModel().getSelectedItem();
        if (temp.getStatus()) {
            temp.setStatus(0);
        } else {
            temp.setStatus(1);
        }
        todoList.refresh();
        todoList.getSelectionModel().clearSelection();
    }

    private void enterNewTodo() {
        list.add(new RegTodo(descriptionText.getText(), datePicker.getValue().toString()));
        todoList.setItems(list);
        descriptionText.setText("");
        errorLabel.setText("");

        datePicker.setValue(LocalDate.now());

    }

    private boolean addTaskValidate() {
        if (descriptionText.getText().equals("")) {
            printError("Error. Cannot create nameless todo");
            return false;
        } else
            if (datePicker.getValue().isBefore(LocalDate.now())) {
                printError("Error. Cannot create todo with due date in past");
                return false;
            }

        // Check for duplicate tasks
//        if (isDuplicate()) {
//            printError("Cannot create duplicate tasks");
//            return false;
//        }
        return true;
    }



    private void printError(String text) {
        todo = new TodoList();
        errorLabel.setText(text);
        errorLabel.setTextFill(Color.RED);
    }

    @FXML
    void initialize() {

    }
}
