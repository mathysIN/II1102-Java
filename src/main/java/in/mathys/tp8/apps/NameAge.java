package in.mathys.tp8.apps;

import in.mathys.tp8.IsepApp;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NameAge extends IsepApp {
    public NameAge(Stage Stage) {
        super(Stage);

        window = stage;
        window.setTitle("VB Layout App");

        Label nameLabel = new Label("Nom et prénom");
        TextField nameField = new TextField();
        Label ageLabel = new Label("Age");
        TextField ageField = new TextField();
        Button submitButton = new Button("< Submit");
        Button clearButton = new Button("< Clear");

        submitButton.setOnAction(actionEvent -> {
            displayInfo("Informations", "Nom : " + nameField.getText() + "\nAge : " + ageField.getText());
        });

        clearButton.setOnAction(actionEvent -> {
            nameField.clear();
            ageField.clear();
        });

        VBox myLayout = new VBox(20);
        myLayout.getChildren().addAll(nameLabel, nameField, ageLabel, ageField, submitButton, clearButton);

        Scene myScene = new Scene(myLayout, 400, 300);

        window.setScene(myScene);
        window.show();
    }

    public void displayInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
