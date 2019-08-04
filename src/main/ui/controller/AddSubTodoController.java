package ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.SuperTodo;
import model.Todo;
import model.TodoList;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddSubTodoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField descriptionText;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXButton addTodo;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Label superTodoName;

    private SuperTodo head;
    private TodoList todos;

    @FXML
    void addNewTodo(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        if (addTaskValidate()) {
            enterNewSubTodo();
            stage.close();
        }
    }

    private boolean addTaskValidate() {
        if (descriptionText.getText().equals("")) {
            printError("Error. Cannot create nameless subTodo");
            return false;
        } else
            if (datePicker.getValue().isBefore(LocalDate.now())) {
                printError("Error. Cannot create subTodo with due date in past");
                return false;
            }
        return true;
    }

    private void enterNewSubTodo() {
        todos.addSuperTodoSub(head, descriptionText.getText(), datePicker.getValue().toString());
    }

    private void printError(String text) {
        errorLabel.setText(text);
        errorLabel.setTextFill(Color.RED);
    }

    @FXML
    void exitWindow(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setTodoList(TodoList todos, Todo head) {
        this.head = (SuperTodo)head;
        this.todos = todos;
        superTodoName.setText(head.getName());
    }


    @FXML
    void initialize() {
        datePicker.setValue(LocalDate.now());
    }
}
