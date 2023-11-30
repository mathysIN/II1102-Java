package in.mathys.tp8.apps;

import in.mathys.tp8.IsepApp;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Demo extends IsepApp {
    public Demo(Stage Stage) {
        super(Stage);

        window = stage;
        window.setTitle("Ayo");

        Label myLabel = new Label("Hello world");
        Label suprise = new Label();
        Button myButton = new Button("CLick moi");
        myButton.setOnAction(actionEvent -> {
            suprise.setText("Surprise!");
        });

        HBox myLayout = new HBox(40);
        myLayout.getChildren().addAll(myLabel, suprise, myButton);

        Scene myScene = new Scene(myLayout, 400, 300);

        window.setScene(myScene);
        window.show();
    }
}
