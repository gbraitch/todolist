package ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Todo;
import model.TodoList;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditTodoController {

    private Todo todo;
    private TodoList todoList;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField descriptionText;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXToggleButton statusSlider;

    @FXML
    void exitWindow(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveChanges(ActionEvent event) {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        if (addTaskValidate()) {
            //try {
                //int index = todoList.getIndex(todo);
            todo.setName(descriptionText.getText());
            todo.setDue(datePicker.getValue().toString());
            todo.setStatus(statusSlider.isSelected());
//                todoList.changeName(index, descriptionText.getText());
//                todoList.changeDue(index, datePicker.getValue().toString());
//                todoList.changeStatus(index, statusSlider.isSelected());
//            } catch (OutOfBoundListIndexException e) {
//                System.out.println("Error editing SuperTodo");
//            } finally {
            stage.close();
            //}
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

    public void setTodoList(TodoList todoList, Todo todo) {
        this.todoList = todoList;
        this.todo = todo;
        datePicker.setValue(local_date(this.todo.getDue()));
        descriptionText.setText(this.todo.getName());
        statusSlider.setSelected(this.todo.getStatus());
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

    private void printError(String text) {
        errorLabel.setText(text);
        errorLabel.setTextFill(Color.RED);
    }

    @FXML
    void initialize() {
    }

    public LocalDate local_date(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
}
