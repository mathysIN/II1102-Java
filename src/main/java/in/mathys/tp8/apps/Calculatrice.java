package in.mathys.tp8.apps;

import in.mathys.tp8.HelloApplication;
import in.mathys.tp8.IsepApp;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculatrice extends IsepApp {
    public Calculatrice(Stage Stage) {
        super(Stage);
        window = stage;
        window.setTitle("Calculatrice");

        TextField firstInput = new TextField();
        TextField secondInput = new TextField();
        Label result = new Label();

        Button addButton = new Button("< Add");
        Button substractButton = new Button("< Substract");
        Button multiplyButton = new Button("< Multiply");
        Button divideButton = new Button("< Divide");

        int buttonSize = 150;
        addButton.setPrefWidth(buttonSize);
        substractButton.setPrefWidth(buttonSize);
        multiplyButton.setPrefWidth(buttonSize);
        divideButton.setPrefWidth(buttonSize);

        HBox myLayout = new HBox(40);
        VBox vBoxLeft = new VBox(10);
        VBox vBoxRight = new VBox(10);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(addButton, 0, 0, 1, 1);
        gridPane.add(substractButton, 1, 0, 1, 1);
        gridPane.add(multiplyButton, 0, 1, 1, 1);
        gridPane.add(divideButton, 1, 1, 1, 1);

        myLayout.setPadding(new Insets(50, 20, 0, 20));

        addButton.setOnAction(actionEvent -> result.setText(
                calculate(firstInput, secondInput, Operation.ADD)
        ));
        substractButton.setOnAction(actionEvent -> result.setText(
                calculate(firstInput, secondInput, Operation.SUBSTRACT)
        ));
        multiplyButton.setOnAction(actionEvent -> result.setText(
                calculate(firstInput, secondInput, Operation.MULTIPLY)
        ));
        divideButton.setOnAction(actionEvent -> result.setText(
                calculate(firstInput, secondInput, Operation.DIVIDE)
        ));

        vBoxLeft.getChildren().addAll(firstInput, secondInput, result);
        vBoxRight.getChildren().addAll(gridPane);

        myLayout.getChildren().addAll(vBoxLeft, vBoxRight);

        Scene myScene = new Scene(myLayout, 400, 300);

        window.setScene(myScene);
        window.show();
    }

    public String calculate (TextField firstInput, TextField secondInput, Operation operation) {
        int first = Integer.valueOf(firstInput.getText());
        int second = + Integer.valueOf(secondInput.getText());
        int result = 0;
        switch (operation) {
            case ADD -> result = first + second;
            case SUBSTRACT -> result = first - second;
            case MULTIPLY -> result = first * second;
            case DIVIDE -> result = first / second;
        }
        return "Resultat = " + result;
    }

    public enum Operation {
        ADD,
        MULTIPLY,
        DIVIDE,
        SUBSTRACT
    }
}
