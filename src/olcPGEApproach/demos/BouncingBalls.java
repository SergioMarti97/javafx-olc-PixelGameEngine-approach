package olcPGEApproach.demos;

import olcPGEApproach.gfx.Renderer;
import olcPGEApproach.vectors.points2d.Vec2df;
import olcPGEApproach.vectors.points2d.Vec2di;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class manages an array of bouncing balls
 * It is made for test the demos of the engine
 */
public class BouncingBalls {

    /**
     * The balls
     */
    private final ArrayList<BouncingBall> balls;

    /**
     * The dimensions of the rectangle where the balls bounce insde
     */
    private Vec2di screenSize;

    /**
     * Useful method to build a random color
     */
    private int makeRndColor(Random rnd) {
        return 0xff << 24 | rnd.nextInt(255) << 16 | rnd.nextInt(255) << 8 | rnd.nextInt(255);
    }

    /**
     * Constructor
     * @param maxVel max velocity of the balls
     * @param maxR max radius of the ball
     * @param minR min radius of the ball
     * @param numBalls number of balls
     * @param width the width of the rectangle where the balls bounce
     * @param height the height of the rectangle where the balls bounce
     */
    public BouncingBalls(int maxVel, int maxR, int minR, int numBalls, int width, int height) {
        Random rnd = new Random();
        balls = new ArrayList<>();
        for (int i = 0; i < numBalls; i++) {
            float r = rnd.nextInt(maxR - minR) + minR;
            balls.add(new BouncingBall(
                    new Vec2df(
                            rnd.nextInt((int) (width - 2 * r)) + r,
                            rnd.nextInt((int) (height - 2 * r)) + r),
                    new Vec2df(
                            rnd.nextInt(maxVel) - (maxVel / 2f),
                            rnd.nextInt(maxVel) - (maxVel / 2f)),
                    r,
                    makeRndColor(rnd)));
        }
        screenSize = new Vec2di(width, height);
    }

    /**
     * This method updates all the balls
     */
    public void update(float elapsedTime) {
        for (BouncingBall b : balls) {
            b.update(elapsedTime, screenSize.getX(), screenSize.getY());
        }
    }

    /**
     * This method draws all the balls on screen
     */
    public void render(Renderer r) {
        for (BouncingBall b : balls) {
            b.draw(r);
        }
    }

    // Getter

    public ArrayList<BouncingBall> getBalls() {
        return balls;
    }

    public Vec2di getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(Vec2di screenSize) {
        this.screenSize = screenSize;
    }
}
