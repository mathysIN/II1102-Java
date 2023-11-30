package in.mathys.tp8;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class IsepApp {
    protected Stage stage;
    protected Stage window;

    public IsepApp(Stage Stage) {
        this.stage = Stage;
        this.window = this.stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getWindow() {
        return window;
    }

    public void setWindow(Stage window) {
        this.window = window;
    }
}
