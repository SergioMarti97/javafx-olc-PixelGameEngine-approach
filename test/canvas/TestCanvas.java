package canvas;

import javafx.application.Application;
import javafx.stage.Stage;
import olcPGEApproach.utils.fx.FXUtils;

public class TestCanvas extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXUtils.showWindow(primaryStage, "Test Canvas", "/views/testCanvasView.fxml",null, 1280, 720);
    }

}
