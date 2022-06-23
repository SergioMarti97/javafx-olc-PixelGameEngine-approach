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

    private final Random rnd;

    private final int maxR;

    private final int minR;

    private final int width;

    private final int height;

    private final int maxVel;

    /**
     * Useful method to build a random color
     */
    private int makeRndColor(Random rnd) {
        return 0xff << 24 | rnd.nextInt(255) << 16 | rnd.nextInt(255) << 8 | rnd.nextInt(255);
    }

    public BouncingBall rndBall() {
        float r = rnd.nextInt(maxR - minR) + minR;
        return new BouncingBall(
                new Vec2df(
                        rnd.nextInt((int) (width - 2 * r)) + r,
                        rnd.nextInt((int) (height - 2 * r)) + r),
                new Vec2df(
                        rnd.nextInt(maxVel) - (maxVel / 2f),
                        rnd.nextInt(maxVel) - (maxVel / 2f)),
                r,
                makeRndColor(rnd));
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
        rnd = new Random();
        balls = new ArrayList<>();
        this.maxVel = maxVel;
        this.maxR = maxR;
        this.minR = minR;
        this.width = width;
        this.height = height;
        for (int i = 0; i < numBalls; i++) {
            balls.add(rndBall());
        }
        screenSize = new Vec2di(width, height);
    }

    /**
     * This method updates all the balls
     */
    public void update(float dt) {
        for (BouncingBall b : balls) {
            /*if ( (b.getPos().getX() + b.getVel().getX() * dt) < b.getR() ) {
                // left edge
                b.getPos().addToY(b.getVel().getY() * dt);
                float x = (b.getPos().getX() + b.getR()) + b.getVel().getX() * dt;
                b.getPos().setX(x - b.getR());
                b.getVel().setX(-b.getVel().getX());
            } else if ( (b.getPos().getX() + b.getVel().getX() * dt) + b.getR() > width ) {
                // right edge
                b.getPos().addToY(b.getVel().getY() * dt);
                float x = (b.getPos().getX() + b.getR()) + b.getVel().getX() * dt;
                b.getPos().setX(2 * width - x - b.getR());
                b.getVel().setX(-b.getVel().getX());
            } else if ( (b.getPos().getY() + b.getVel().getY() * dt) < b.getR() ) {
                // top edge
                b.getPos().addToX(b.getVel().getX() * dt);
                float y = (b.getPos().getY() + b.getR()) + b.getVel().getY() * dt;
                b.getPos().setY(y - b.getR());
                b.getVel().setY(-b.getVel().getY());
            } else if ((b.getPos().getY() + b.getVel().getY() * dt) + b.getR() > height) {
                // bottom edge
                b.getPos().addToX(b.getVel().getX() * dt);
                float y = (b.getPos().getY() + b.getR()) + b.getVel().getY() * dt;
                b.getPos().setY(2 * height - y - b.getR());
                b.getVel().setY(-b.getVel().getY());
            } else {
                // normal case
                b.getPos().addToX(b.getVel().getX() * dt);
                b.getPos().addToY(b.getVel().getY() * dt);
            }*/

            // old code
            b.getPos().addToX(b.getVel().getX() * dt);
            b.getPos().addToY(b.getVel().getY() * dt);

            if (b.getPos().getX() - b.getR() < 0) {
                b.getPos().setX(b.getR());
                b.getVel().setX(-b.getVel().getX());
            }
            if (b.getPos().getX() + b.getR() > width) {
                b.getPos().setX(width - b.getR());
                b.getVel().setX(-b.getVel().getX());
            }
            if (b.getPos().getY() - b.getR() < 0 ) {
                b.getPos().setY(b.getR());
                b.getVel().setY(-b.getVel().getY());
            }
            if (b.getPos().getY() + b.getR() > height) {
                b.getPos().setY(height - b.getR());
                b.getVel().setY(-b.getVel().getY());
            }
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

    public int getNumBalls() {
        return balls.size();
    }

    public void add(BouncingBall b) {
        balls.add(b);
    }

    public boolean remove(BouncingBall b) {
        return balls.remove(b);
    }

    public BouncingBall remove(int idx) {
        return balls.remove(idx);
    }

}
