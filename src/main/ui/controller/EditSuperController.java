package ui.controller;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
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
import model.exception.OutOfBoundListIndexException;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditSuperController {

    private TodoList todoList;
    private Todo superTodo;

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
            JFXDecorator decorator = new JFXDecorator(stage, root1);
            decorator.setCustomMaximize(true);
            AddSubTodoController controller = fxmlLoader.getController();
            controller.setTodoList(todoList, superTodo);
            stage.initOwner(mainAnchorPane.getScene().getWindow());
            Scene scene = new Scene(decorator);
            String css = this.getClass().getResource("/ui/assets/subWindowsStyle.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
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
            try {
                int index = todoList.getIndex(superTodo);
                todoList.changeName(index, descriptionText.getText());
                todoList.changeDue(index, datePicker.getValue().toString());
                todoList.changeStatus(index, statusSlider.isSelected());
            } catch (OutOfBoundListIndexException e) {
                System.out.println("Error editing SuperTodo");
            } finally {
                stage.close();
            }

        }
    }

    private boolean addTaskValidate() {
        if (descriptionText.getText().equals("")) {
            printError("Error. Cannot create nameless superTodo");
            return false;
        } else
            if (datePicker.getValue().isBefore(LocalDate.now())) {
                printError("Error. Cannot create superTodo with due date in past");
                return false;
            }
        return true;
    }

    public void setTodoList(TodoList todoList, Todo superTodo) {
        this.todoList = todoList;
        this.superTodo = superTodo;
        datePicker.setValue(local_date(this.superTodo.getDue()));
        descriptionText.setText(this.superTodo.getName());
        statusSlider.setSelected(this.superTodo.getStatus());
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
