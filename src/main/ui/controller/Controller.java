package ui.controller;

import com.jfoenix.controls.*;
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
import model.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private AnchorPane mainAnchorPane;

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
    private JFXButton addSubTodo;

    @FXML
    private JFXTextField descriptionText;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXListView<Todo> todoList;

    @FXML
    private JFXCheckBox superTodoCheckBox;

    private ObservableList<Todo> list = FXCollections.observableArrayList();
    private TodoList todo;

    @FXML
    void addNewTodo(ActionEvent event) {
        if (addTaskValidate()) {
            if (!superTodoCheckBox.isSelected()) {
                enterNewTodo();
            } else {
                enterNewSuperTodo();
            }
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

    @FXML
    void showNewItemDialog(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/ui/gui/addTodoWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            AddTodoController controller = fxmlLoader.getController();
            controller.setList(list);
            stage.initOwner(mainAnchorPane.getScene().getWindow());
            stage.setTitle("Add New Todo Item");
            stage.setScene(new Scene(root1));
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error loading new window");
            e.printStackTrace();
        }
        todoList.setItems(list);
        System.out.println("finished");
    }


    private void enterNewSuperTodo() {
        SuperTodo temp = new SuperTodo(descriptionText.getText(), datePicker.getValue().toString());
        temp.addSubTodo(new SubTodo("test", datePicker.getValue().toString()));
        temp.addSubTodo(new SubTodo("test2", datePicker.getValue().toString()));
        list.add(temp);

        todoList.setItems(list);
        descriptionText.setText("");
        errorLabel.setText("");

        datePicker.setValue(LocalDate.now());
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
