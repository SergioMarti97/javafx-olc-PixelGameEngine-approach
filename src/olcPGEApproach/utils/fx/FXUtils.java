package olcPGEApproach.utils.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * This class contains static methods useful for some actions of javafx
 * open a new window for example
 */
public class FXUtils {

    /**
     * This method shows a window
     */
    public static void showWindow(Stage stage, String title, String fxml, String css, int width, int height) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(FXUtils.class.getResource(fxml)));

            Scene scene = new Scene(root, width, height);
            if (css != null) {
                scene.getStylesheets().add(Objects.requireNonNull(FXUtils.class.getResource(css)).toExternalForm());
            }

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method shows a window
     */
    public static void showWindow(String title, String fxml, String css, int width, int height) {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(FXUtils.class.getResource(fxml)));

            Scene scene = new Scene(root, width, height);
            if (css != null) {
                scene.getStylesheets().add(Objects.requireNonNull(FXUtils.class.getResource(css)).toExternalForm());
            }

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method shows a window
     */
    public static Object showWindowGetController(String title, String fxml, String css, int width, int height) {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(FXUtils.class.getResource(fxml)));
            Parent root = loader.load();

            Scene scene = new Scene(root, width, height);
            if (css != null) {
                scene.getStylesheets().add(Objects.requireNonNull(FXUtils.class.getResource(css)).toExternalForm());
            }

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method shows an error message
     * @param header the header for the error
     * @param message the text of the message
     */
    public static void showError(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This method shows a message
     * @param header the header for the message
     * @param message the text of the message
     */
    public static void showMessage(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
