package ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.RegTodo;
import model.SubTodo;
import model.SuperTodo;
import model.Todo;

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

    private ObservableList<Todo> list;

    @FXML
    void addNewTodo(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        if (addTaskValidate()) {
            if (!superTodoCheckBox.isSelected()) {
                enterNewTodo();
            } else {
                enterNewSuperTodo();
            }
        }
        stage.close();
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
        temp.addSubTodo(new SubTodo("test", datePicker.getValue().toString()));
        temp.addSubTodo(new SubTodo("test2", datePicker.getValue().toString()));
        list.add(temp);
    }

    private void enterNewTodo() {
        list.add(new RegTodo(descriptionText.getText(), datePicker.getValue().toString()));
    }


    private void printError(String text) {
        errorLabel.setText(text);
        errorLabel.setTextFill(Color.RED);
    }

    public void setList(ObservableList<Todo> list) {
        this.list = list;
    }

    @FXML
    void exitWindow(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {

    }


}

