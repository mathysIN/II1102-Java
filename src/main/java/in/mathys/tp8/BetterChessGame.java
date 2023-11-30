package in.mathys.tp8;

import in.mathys.tp3.Chess;
import in.mathys.tp3.libs.Cell;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BetterChessGame extends Chess {
    public void ok(Stage stage) {
        initialiseBoard();

        HBox myLayout = new HBox(40);

        GridPane gridPane = new GridPane();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell cell = getCell(i, j);
                gridPane.add(new Button(cell.isEmpty() ? "   " : String.valueOf(cell.getPiece().getIcon())), i, j, 1, 1);
            }
        }

        myLayout.getChildren().add(gridPane);

        stage.setScene(new Scene(myLayout, 400, 300));
        stage.show();
    }
}
