package windowManager.layouts;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import olcPGEApproach.windowManager.ScreenController;
import windowManager.ScreenFramework;

import java.net.URL;
import java.util.ResourceBundle;

public class Screen2Controller extends ScreenController {

    @FXML
    private Button btnScreen1;

    @FXML
    private Button btnScreen3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnScreen1.setOnAction(event -> screenParent.setScreen(ScreenFramework.screen1ID));
        btnScreen3.setOnAction(event -> {
            screenParent.setScreen(ScreenFramework.screen3ID);
            ((Screen3Controller)screenParent.getScreen(ScreenFramework.screen3ID).getValue()).getGameContainer().getTimer().start();
        });
    }

}
