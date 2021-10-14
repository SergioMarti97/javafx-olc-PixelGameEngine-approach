package olcPGEApproach.windowManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * @deprecated
 */
public class WindowManager {

    private static void showWindowStage(Stage stage, Parent root) {
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    public static <T> T openWindow(String title, FXMLLoader loader) {
        Stage stage = new Stage();
        stage.setTitle(title);
        Parent root;
        try {
            root = loader.load();
            T controller = loader.getController();
            showWindowStage(stage, root);
            return controller;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
