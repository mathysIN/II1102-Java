package in.mathys.tp8.apps;

import in.mathys.tp8.IsepApp;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ChessTiles extends IsepApp {
    public ChessTiles(Stage Stage) {
        super(Stage);
        GridPane grid = new GridPane();

        int size = 8;
        double squareSize = 60.0;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Rectangle square = new Rectangle(squareSize, squareSize,
                        (row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);
                grid.add(square, col, row);
            }
        }

        Scene scene = new Scene(grid, size * squareSize, size * squareSize);

        stage.setTitle("Chessboard");
        stage.setScene(scene);
        stage.show();
    }
}
