package windowManager.layouts;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import olcPGEApproach.GameContainer;
import olcPGEApproach.utils.windowManager.ScreenController;
import windowManager.BallsGame;
import windowManager.ScreenFramework;

import java.net.URL;
import java.util.ResourceBundle;

public class Screen3Controller extends ScreenController {

    @FXML
    private ImageView imgView;

    @FXML
    private Button btnScreen1;

    @FXML
    private Button btnScreen2;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnStop;

    private GameContainer gc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = new GameContainer(new BallsGame(this), (int)imgView.getFitWidth(), (int)imgView.getFitHeight(), imgView);
        imgView.setImage(gc.getImg());
        gc.getTimer().start();

        btnScreen1.setOnAction(event -> {
            screenParent.setScreen(ScreenFramework.screen1ID);
            gc.getTimer().stop();
        });
        btnScreen2.setOnAction(event -> {
            screenParent.setScreen(ScreenFramework.screen2ID);
            gc.getTimer().stop();
        });

        btnStart.setOnAction(event -> gc.getTimer().start());
        btnStop.setOnAction(event -> gc.getTimer().stop());
    }

    public GameContainer getGameContainer() {
        return gc;
    }

}
