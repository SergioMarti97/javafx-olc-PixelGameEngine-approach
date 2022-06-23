package olcPGEApproach.demos;

import olcPGEApproach.gfx.Renderer;
import olcPGEApproach.vectors.points2d.Vec2df;

/**
 * This class represents a ball what bounces on the
 * edges of a given rectangle. It is made for test the
 * demos of the engine
 */
public class BouncingBall {

    private int id = 0;

    /**
     * Position of the ball
     */
    private Vec2df pos;

    /**
     * Velocity of the ball
     */
    private Vec2df vel;

    /**
     * Radius of the ball
     */
    private float r;

    /**
     * Color of the ball
     */
    private int col;

    /**
     * Constructor
     */
    public BouncingBall(Vec2df pos, Vec2df vel, float r, int col) {
        this.pos = pos;
        this.vel = vel;
        this.r = r;
        this.col = col;
    }

    /**
     * updates the physics of the ball
     */
    public void update(float elapsedTime, float w, float h) {
        pos.addToX(vel.getX() * elapsedTime);
        pos.addToY(vel.getY() * elapsedTime);

        if (pos.getX() - r < 0) {
            pos.setX(r);
            vel.setX(-vel.getX());
        }
        if (pos.getX() + r > w) {
            pos.setX(w - r);
            vel.setX(-vel.getX());
        }
        if (pos.getY() - r < 0 ) {
            pos.setY(r);
            vel.setY(-vel.getY());
        }
        if (pos.getY() + r > h) {
            pos.setY(h - r);
            vel.setY(-vel.getY());
        }
    }

    /**
     * This method draws the ball
     */
    public void draw(Renderer r) {
        r.drawFillCircle((int)pos.getX(), (int)pos.getY(), (int)this.r, col);
    }

    // Getters and Setters

    public Vec2df getPos() {
        return pos;
    }

    public void setPos(Vec2df pos) {
        this.pos = pos;
    }

    public Vec2df getVel() {
        return vel;
    }

    public void setVel(Vec2df vel) {
        this.vel = vel;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
