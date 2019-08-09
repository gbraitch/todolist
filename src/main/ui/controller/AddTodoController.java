package ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.RegTodo;
import model.SuperTodo;
import model.TodoList;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

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
        Stage stage = (Stage) addTodo.getScene().getWindow();
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
        SuperTodo newSuper = new SuperTodo(descriptionText.getText(), datePicker.getValue().toString());
        todos.addSuperTodo(newSuper);
    }

    private void enterNewTodo() {
        RegTodo newReg = new RegTodo(descriptionText.getText(), datePicker.getValue().toString());
        todos.addRegTodo(newReg);
    }


    private void printError(String text) {
        errorLabel.setText(text);
        errorLabel.setTextFill(Color.RED);
    }

    public void setTodoList(TodoList todos) {
        this.todos = todos;
        addValidator();
    }

    private void addValidator() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        warnIcon.getStyleClass().add("error");
        validator.setIcon(warnIcon);
        descriptionText.getValidators().add(validator);
        descriptionText.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                descriptionText.validate();
            }
        });
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

