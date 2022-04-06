package olcPGEApproach.utils.windowManager;

import javafx.fxml.Initializable;

/**
 * This class is an abstraction of commons behaviour of
 * the screens managed by the ScreensManager class
 *
 * It is a controller for one screen layout
 */
public abstract class ScreenController implements Initializable, ControlledScreen {

    protected ScreensManager screenParent;

    @Override
    public void setScreenParent(ScreensManager screenParent) {
        this.screenParent = screenParent;
    }

    public ScreensManager getScreenParent() {
        return screenParent;
    }

}
