package in.mathys.tp8.apps;

import in.mathys.tp8.IsepApp;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Todo extends IsepApp {
    private ListView<String> taskList = new ListView<>();
    public Todo(Stage Stage) {
        super(Stage);
        BorderPane root = new BorderPane();

        // Task input and add button
        TextField taskInput = new TextField();
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addTask(taskInput));

        HBox inputBox = new HBox(10, taskInput, addButton);
        inputBox.setPadding(new Insets(10));

        root.setTop(inputBox);
        root.setCenter(taskList);

        Scene scene = new Scene(root, 300, 200);

        stage.setTitle("To-Do App");
        stage.setScene(scene);
        stage.show();
    }

    private void addTask(TextField taskInput) {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            taskList.getItems().add(task);
            taskInput.clear();
        }
    }
}
