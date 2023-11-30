package in.mathys.tp8;

import in.mathys.tp8.apps.Todo;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    Stage window;
    @Override
    public void start(Stage stage) {
        window = new Todo(stage).getWindow();
    }


    public static void main(String[] args) {
        launch();
    }
}