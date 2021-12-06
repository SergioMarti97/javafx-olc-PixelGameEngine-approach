package sample.input;

import javafx.scene.input.KeyCode;
import olcPGEApproach.AbstractGame;
import olcPGEApproach.GameContainer;
import olcPGEApproach.demos.BouncingBalls;
import olcPGEApproach.gfx.HexColors;

public class TestingInput implements AbstractGame {

    private boolean isDown = false;

    private BouncingBalls balls;

    @Override
    public void initialize(GameContainer gc) {
        int vel = 200;
        int maxR = 25;
        int minR = 5;
        int numBalls = 1000;
        balls = new BouncingBalls(vel, maxR, minR, numBalls, gc.getRenderer().getW(), gc.getRenderer().getH());
    }

    @Override
    public void update(GameContainer gc, float elapsedTime) {
        isDown = gc.getInput().isKeyDown(KeyCode.SPACE);
        balls.update(elapsedTime);
    }

    @Override
    public void render(GameContainer gc) {
        gc.getRenderer().clear();

        //balls.render(gc.getRenderer());

        boolean isPressed = gc.getInput().isKey(KeyCode.SPACE);
        boolean wasPressed = gc.getInput().isKeyLast(KeyCode.SPACE);

        gc.getRenderer().drawText("Is Down " + isDown,
                10, 200, HexColors.RED);

        gc.getRenderer().drawText("Is pressed " + isPressed,
                10, 10, HexColors.WHITE);
        gc.getRenderer().drawText("Was pressed " + wasPressed,
                10, 40, HexColors.WHITE);

        if ( isPressed && !wasPressed ) {
            gc.getRenderer().drawText("Key down: " + KeyCode.SPACE, 10, 70, HexColors.WHITE);
        }

        if ( !isPressed && wasPressed ) {
            gc.getRenderer().drawText("Key Up: " + KeyCode.SPACE, 10, 100, HexColors.WHITE);
        }

        if ( isPressed && wasPressed ) {
            gc.getRenderer().drawText("You are pressing the: key " + KeyCode.SPACE, 10, 130, HexColors.WHITE);
        }

        int scroll = gc.getInput().getScroll();
        gc.getRenderer().drawText("Scroll " + scroll, 10, 160, HexColors.WHITE);
    }

}
