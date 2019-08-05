package ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Todo;
import model.TodoList;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditSuperController {

    private TodoList todos;
    private Todo todo;

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
    private JFXButton addSubTodo;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    void addNewSubTodo(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/addSubWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            AddSubTodoController controller = fxmlLoader.getController();
            controller.setTodoList(todos, todo);
            stage.initOwner(mainAnchorPane.getScene().getWindow());
            stage.setTitle("Add SubTodo");
            stage.setScene(new Scene(root1));
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error loading new window");
            e.printStackTrace();
        }
    }

    @FXML
    void exitWindow(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveChanges(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        if (addTaskValidate()) {
            todo.setStatus(statusSlider.isSelected());
            todo.setName(descriptionText.getText());
            todo.setDue(datePicker.getValue().toString());
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

    public void setTodoList(TodoList todos, Todo todo) {
        this.todos = todos;
        this.todo = todo;
        datePicker.setValue(local_date(todo.getDue()));
        descriptionText.setText(todo.getName());
        statusSlider.setSelected(todo.getStatus());
    }

    private void printError(String text) {
        errorLabel.setText(text);
        errorLabel.setTextFill(Color.RED);
    }

    @FXML
    void initialize() {
    }

    public LocalDate local_date(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
}
