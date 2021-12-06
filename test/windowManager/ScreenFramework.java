package windowManager;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import olcPGEApproach.windowManager.ScreensManager;

public class ScreenFramework extends Application {

    public static String screen1ID = "main";
    public static String screen1File = "/windowManager/layouts/Screen1.fxml";
    public static String screen2ID = "screen2";
    public static String screen2File = "/windowManager/layouts/Screen2.fxml";
    public static String screen3ID = "screen3";
    public static String screen3File = "/windowManager/layouts/Screen3.fxml";

    @Override
    public void start(Stage primaryStage) {
        ScreensManager mainContainer = new ScreensManager();
        mainContainer.loadScreen(ScreenFramework.screen1ID, ScreenFramework.screen1File);
        mainContainer.loadScreen(ScreenFramework.screen2ID, ScreenFramework.screen2File);
        mainContainer.loadScreen(ScreenFramework.screen3ID, ScreenFramework.screen3File);

        mainContainer.setScreen(ScreenFramework.screen1ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
