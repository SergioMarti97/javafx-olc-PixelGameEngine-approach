package windowManager;

import olcPGEApproach.AbstractGame;
import olcPGEApproach.GameContainer;
import olcPGEApproach.demos.BouncingBalls;
import olcPGEApproach.windowManager.ScreenController;

/**
 * This game is a simple game of balls what bounces on
 * the walls
 */
public class BallsGame implements AbstractGame {

    private BouncingBalls balls;

    private final ScreenController screenController;

    /**
     * Constructor
     * ScreenController is needed because is wanted that in this game, if
     * the player presses space, it changes to other scene
     */
    public BallsGame(ScreenController screenController) {
        this.screenController = screenController;
    }

    @Override
    public void initialize(GameContainer gc) {
        balls = new BouncingBalls(200, 25, 5, 10, gc.getRenderer().getW(), gc.getRenderer().getH());
    }

    @Override
    public void update(GameContainer gc, float elapsedTime) {
        balls.update(elapsedTime);
    }

    @Override
    public void render(GameContainer gc) {
        gc.getRenderer().clear();
        balls.render(gc.getRenderer());
    }

    public ScreenController getScreenController() {
        return screenController;
    }

}
