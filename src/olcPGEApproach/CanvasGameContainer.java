package olcPGEApproach;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;

public class CanvasGameContainer extends GameContainer {

    public CanvasGameContainer(AbstractGame game, int screenWidth, int screenHeight, Node canvas) {
        super(game, screenWidth, screenHeight, canvas);
    }

    public Canvas getCanvas() {
        return (Canvas) getNode();
    }

}
