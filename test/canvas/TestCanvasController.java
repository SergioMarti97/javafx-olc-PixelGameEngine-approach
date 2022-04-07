package canvas;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import olcPGEApproach.GameContainer;

import java.net.URL;
import java.util.ResourceBundle;

public class TestCanvasController implements Initializable {

    public Canvas canvas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TestCanvasGame game = new TestCanvasGame();
        GameContainer gc = new GameContainer(game,
                (int)canvas.getWidth(),
                (int)canvas.getHeight(),
                canvas);
        game.setCanvas(canvas);

        gc.getTimer().setShowFPSOnConsole(true);
        gc.getTimer().start();
    }

}
