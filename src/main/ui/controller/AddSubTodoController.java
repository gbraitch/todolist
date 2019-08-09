package ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.SubTodo;
import model.SuperTodo;
import model.Todo;
import model.TodoList;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

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

    private SuperTodo superTodo;
    private TodoList todoList;

    @FXML
    void addNewTodo(ActionEvent event) {
        Stage stage = (Stage) addTodo.getScene().getWindow();
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
        SubTodo newSub = new SubTodo(descriptionText.getText(), datePicker.getValue().toString());
        todoList.addSuperTodoSub(superTodo, newSub);
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

    public void setTodoList(TodoList todoList, Todo superTodo) {
        this.superTodo = (SuperTodo)superTodo;
        this.todoList = todoList;
        superTodoName.setText(superTodo.getName());
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
    void initialize() {
        datePicker.setValue(LocalDate.now());
    }
}
