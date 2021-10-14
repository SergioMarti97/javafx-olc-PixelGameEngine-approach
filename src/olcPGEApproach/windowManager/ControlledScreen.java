package olcPGEApproach.windowManager;

/**
 * Common type for every controller
 * We can do things like inserting the parent
 * @author Sergio
 */
public interface ControlledScreen {

    /**
     * This method will allow the injection of the Parent ScreenPane
     */
    void setScreenParent(ScreensManager screenPage);

}
