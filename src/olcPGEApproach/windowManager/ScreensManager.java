package olcPGEApproach.windowManager;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

/**
 * This class manages the screens to be displayed
 */
public class ScreensManager extends StackPane {

    /**
     * The screens to be displayed
     * String = id of that particular screen
     * Node = represents the root of the scene graph for that particular screen
     */
    private final HashMap<String, Node> screens = new HashMap<>();

    /**
     * Constructor
     */
    public ScreensManager() {
        super();
    }

    /**
     * Adds a screen to the collection
     * @param name the id of the new screen
     * @param screen the root of the scene graph for the new screen
     */
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    /**
     * Returns the node with the appropriate name
     * @param name the id of a present screen
     */
    public Node getScreen(String name) {
        return screens.get(name);
    }

    /**
     * Load the fxml file, add the screen to the screens collection
     * and finally injects the ScreenPane to the controller
     * @param name the name of the new screen
     * @param resource the fxml is wanted with the screen layout
     * @return true if is all ok, false if the loading process fail
     */
    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = loader.load();
            ControlledScreen screenController = loader.getController();
            screenController.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * This method tries to displayed the screen with a predefined name.
     * First it makes sure the screen has been already loaded. Then if there is more
     * than one screen the new screen has been added second, and then the current screen
     * is removed. If there isn't any screen being displayed, the new screen is
     * just added to the root
     * @param name the name of the screen
     */
    public boolean setScreen(final String name) {
        if (screens.get(name) != null) { // screen loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) { // if there is more than one screen
                Timeline fade = new Timeline( // Transition between two screens
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000), event -> {
                            getChildren().remove(0); // Remove the displayed screen
                            getChildren().add(0, screens.get(name)); // add the screen
                            Timeline fadeIn = new Timeline(
                                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                    new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
                            fadeIn.play();
                        }, new KeyValue(opacity, 0.0)));
                fade.play();
            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("Screen hasn't been loaded!!!\n");
            return false;
        }
    }

    /**
     * This method will remove the screen with the given name form the collection of
     * screens
     */
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen \"" + name + "\"didn't exist");
            return false;
        } else {
            return true;
        }
    }

    // Getters and setters

    public HashMap<String, Node> getScreens() {
        return screens;
    }

}
