package ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.SuperTodo;
import model.TodoList;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddTodoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField descriptionText;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXCheckBox superTodoCheckBox;

    @FXML
    private JFXButton addTodo;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private Label errorLabel;

    private TodoList todos;

    @FXML
    void addNewTodo(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        if (addTaskValidate()) {
            if (!superTodoCheckBox.isSelected()) {
                enterNewTodo();
            } else {
                enterNewSuperTodo();
            }
            stage.close();
        }
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
        return true;
    }

    private void enterNewSuperTodo() {
        SuperTodo temp = new SuperTodo(descriptionText.getText(), datePicker.getValue().toString());
        todos.addSuperTodo(temp);
    }

    private void enterNewTodo() {
        todos.addRegTodo(descriptionText.getText(), datePicker.getValue().toString());
    }


    private void printError(String text) {
        errorLabel.setText(text);
        errorLabel.setTextFill(Color.RED);
    }

    public void setTodoList(TodoList todos) {
        this.todos = todos;
    }

    @FXML
    void exitWindow(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        datePicker.setValue(LocalDate.now());
    }

}

