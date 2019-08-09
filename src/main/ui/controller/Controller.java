package ui.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import model.SubTodo;
import model.SuperTodo;
import model.Todo;
import model.TodoList;
import model.exception.OutOfBoundListIndexException;
import ui.api.Weather;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class Controller implements Observer {

    private ObservableList<Todo> list = FXCollections.observableArrayList();
    private TodoList todoList;
    private Weather weather;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private JFXListView<Todo> listOfTodos;

    @FXML
    private JFXProgressBar progressBar;

    @FXML
    private Label errorLabel;

    @FXML
    private Label currentTemp;

    @FXML
    private Label weatherDescription;

    @FXML
    private Label minTemp;

    @FXML
    private Label maxTemp;

    @FXML
    private Label currentDate;

    @FXML
    private ImageView imageViewer;

    //-----------------------------------//
    //-----------Initialization----------//
    //-----------------------------------//

    @FXML
    void initialize() {
        listOfTodos.setExpanded(true);
        listOfTodos.depthProperty().set(1);
    }

    public void init(TodoList todos) throws IOException {
        this.todoList = todos;
        // Load previous data
        todos.load(null);
        // Initialize progress bar
        calculateProgress();
        // Initialize weather info
        initWeather();
        initCheckBoxes();
    }

    private void initCheckBoxes() {
        listOfTodos.setCellFactory(new Callback<ListView<Todo>, ListCell<Todo>>() {
            @Override
            public ListCell<Todo> call(ListView<Todo> param) {
                return new ListCell<Todo>() {
                    JFXCheckBox checkBox = new JFXCheckBox(); {
                        setGraphic(checkBox);
                    }

                    @Override
                    protected void updateItem(Todo item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(initCheckBoxesHelper(item,empty,checkBox));
                    }
                };
            }
        });
        attachStatusListeners();
    }

    private String initCheckBoxesHelper(Todo item, boolean empty, JFXCheckBox checkBox) {
        if (item == null || empty) {
            checkBox.setVisible(false);
            return null;
        } else {
            checkBox.setVisible(true);
            checkBox.setSelected(item.getStatus());
            String save = item.toString();
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue ov, Boolean oldval, Boolean newval) {
                    item.setStatus(newval);
                }
            });
            return save;
        }
    }

    private void attachStatusListeners() {
        list.forEach(Todo -> Todo.getBooleanProperty().addListener((observable, wasSelected, isSelected) -> {
            if (isSelected) {
                updateList();
            } else {
                if (wasSelected) {
                    updateList();
                }
            }
        }));
    }

    private void initWeather() throws IOException {
        weather = new Weather();

        currentDate.setText(LocalDate.now().toString());
        currentTemp.setText(weather.getCurrentTemp() + "°C");
        maxTemp.setText("Max: " + weather.getMaxTemp() + " °C");
        minTemp.setText("Min: " + weather.getMinTemp() + " °C");
        weatherDescription.setText(weather.getDescription());
        imageViewer.setImage(chooseWeatherImage(weather.getMainDescription()));
    }

    private Image chooseWeatherImage(String description) {
        String img;
        switch (description) {
            case "Clear":
                img = "sunny.png";
                break;
            case "Cloudy":
                img = "cloudy.png";
                break;
            default:
                img = "partly_cloudy.png";
        }
        return new Image("/ui/assets/" + img);
    }

    //-----------------------------------//
    //--------Initialization Done--------//
    //-----------------------------------//

    @FXML
    void deleteTodo(ActionEvent event) {
        if (listOfTodos.getSelectionModel().isEmpty()) {
            printError("Error. Must select Todo to delete");
        } else {
            Todo temp = listOfTodos.getSelectionModel().getSelectedItem();
            if (!temp.getType().equals("Sub")) {
                try {
                    todoList.removeTodo(temp);
                } catch (OutOfBoundListIndexException e) {
                    System.out.println("Error deleting todo");
                }
            } else {
                SubTodo st = (SubTodo) temp;
                todoList.removeSuperTodoSub(st);
            }
            listOfTodos.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void showNewItemDialog(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/gui/addWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            JFXDecorator decorator = new JFXDecorator(stage, root1);
            decorator.setCustomMaximize(true);
            AddTodoController controller = fxmlLoader.getController();
            controller.setTodoList(todoList);
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
    void showEditItemDialog(ActionEvent event) {
        if (listOfTodos.getSelectionModel().isEmpty()) {
            printError("Error. Must select Todo to edit");
        } else {
            if (!listOfTodos.getSelectionModel().getSelectedItem().getType().equals("Super")) {
                openEditWindow();
            } else {
                openEditSuperWindow();
            }
            listOfTodos.getSelectionModel().clearSelection();
        }
    }

    private void openEditWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../gui/editWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            JFXDecorator decorator = new JFXDecorator(stage, root1);
            EditTodoController controller = fxmlLoader.getController();
            controller.setTodoList(todoList, listOfTodos.getSelectionModel().getSelectedItem());
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
        updateList();
    }

    private void openEditSuperWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/gui/editSuperWindow.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            JFXDecorator decorator = new JFXDecorator(stage, root1);
            decorator.setCustomMaximize(true);
            EditSuperController controller = fxmlLoader.getController();
            controller.setTodoList(todoList, listOfTodos.getSelectionModel().getSelectedItem());
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

    private void printError(String text) {
        errorLabel.setText(text);
        errorLabel.setTextFill(Color.WHITE);

        //Make test fade, start from opacity 1 -> 0
        FadeTransition ft = new FadeTransition(Duration.millis(3000), errorLabel);
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.play();
    }

    private void calculateProgress() {
        double progress = todoList.getCompleted() / todoList.getTotalTodos();
        progressBar.setProgress(progress);
    }


    @Override
    public void update(Observable o, Object arg) {
        updateList();
    }

    public void updateList() {
        this.list.clear();

        for (Todo t : todoList) {
            this.list.add(t);
            if (t.getType().equals("Super")) {
                SuperTodo tempSuper = (SuperTodo) t;
                this.list.addAll(tempSuper.getSubList());
            }
        }
        listOfTodos.setItems(this.list);

        calculateProgress();
    }

}
