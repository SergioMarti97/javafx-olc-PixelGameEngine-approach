package sample.pendulum;

import olcPGEApproach.vectors.points2d.Vec2df;

public class Pendulum {

    private Vec2df origin;

    private Vec2df end;

    private float size;

    private float angle;

    private float rotVel;

    public Pendulum(float x, float y, float size, float angle, float rotVel) {
        origin = new Vec2df(x, y);
        this.size = size;
        this.angle = angle;
        this.rotVel = rotVel;
        end = new Vec2df(size * (float) Math.cos(angle) + x, size * (float) Math.sin(angle) + y);
    }

    public Pendulum(Vec2df origin, float size, float angle, float rotVel) {
        this.origin = origin;
        this.size = size;
        this.angle = angle;
        this.rotVel = rotVel;
        end = new Vec2df(size * (float) Math.cos(angle) + this.origin.getX(),
                size * (float) Math.sin(angle) + this.origin.getY());
    }

    public void update(float t) {
        angle += rotVel * t;
        end.setX(size * (float) Math.cos(angle) + origin.getX());
        end.setY(size * (float) Math.sin(angle) + origin.getY());
    }

    /*
    * Getter and Setters
    */

    public Vec2df getOrigin() {
        return origin;
    }

    public void setOrigin(Vec2df origin) {
        this.origin = origin;
    }

    public Vec2df getEnd() {
        return end;
    }

    public void setEnd(Vec2df end) {
        this.end = end;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getRotVel() {
        return rotVel;
    }

    public void setRotVel(float rotVel) {
        this.rotVel = rotVel;
    }
}
