package in.mathys.tp8.apps;

import in.mathys.tp8.BetterChessGame;
import in.mathys.tp8.IsepApp;
import javafx.stage.Stage;

public class Chess extends IsepApp {
    public Chess(Stage stage) {
        super(stage);
        window.setTitle("Jeu d'echec... probablement");

        new BetterChessGame().ok(window);
    }

}
