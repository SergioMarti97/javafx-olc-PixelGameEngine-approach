package olcPGEApproach;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import olcPGEApproach.vectors.points2d.Vec2df;
import olcPGEApproach.vectors.points2d.Vec2di;

/**
 * This class is an extension from an abstract game, which one implements
 * the panning and zooming code
 */
public class AbstractGamePanAndZoom implements AbstractGame {

    protected Vec2df offset;

    protected Vec2df scale;

    protected Vec2df startPan;

    protected float zoomInc = 0.01f;

    protected MouseButton mouseButtonPan = MouseButton.PRIMARY;

    protected KeyCode keyCodeZoomIn = KeyCode.Q;

    protected KeyCode keyCodeZoomOut = KeyCode.A;

    // Methods to pan and zoom

    public int worldToScreenX(float worldX) {
        return (int)((worldX - offset.getX()) * scale.getX());
    }

    public int worldToScreenY(float worldY) {
        return (int)((worldY - offset.getY()) * scale.getY());
    }

    public Vec2di worldToScreen(float worldX, float worldY) {
        return new Vec2di(worldToScreenX(worldX), worldToScreenY(worldY));
    }

    public Vec2di worldToScreen(Vec2df world) {
        return worldToScreen(world.getX(), world.getY());
    }

    public float screenToWorldX(int screenX) {
        return ((float) screenX / scale.getX()) + offset.getX();
    }

    public float screenToWorldY(int screenY) {
        return ((float) screenY / scale.getY()) + offset.getY();
    }

    public Vec2df screenToWorld(int screenX, int screenY) {
        return new Vec2df(screenToWorldX(screenX), screenToWorldY(screenY));
    }

    public Vec2df screenToWorld(Vec2di screen) {
        return screenToWorld(screen.getX(), screen.getY());
    }

    @Override
    public void initialize(GameContainer gc) {
        offset = new Vec2df(0, 0); // -gc.getRenderer().getW() / 2.0f, -gc.getRenderer().getH() / 2.0f
        scale = new Vec2df(1, 1);
        startPan = new Vec2df();
    }

    @Override
    public void update(GameContainer gc, float elapsedTime) {
        Vec2df mouse = new Vec2df((float)gc.getInput().getMouseX(), (float)gc.getInput().getMouseY());

        if (gc.getInput().isButtonDown(mouseButtonPan)) {
            startPan.set(mouse);
        }

        if (gc.getInput().isButtonHeld(mouseButtonPan)) {
            offset.addToX(-(mouse.getX() - startPan.getX()) / scale.getX());
            offset.addToY(-(mouse.getY() - startPan.getY()) / scale.getY());

            startPan.set(mouse);
        }

        Vec2df mouseWorldBeforeZoom = screenToWorld((int)mouse.getX(), (int)mouse.getY());

        if (gc.getInput().isKeyHeld(keyCodeZoomIn)) {
            scale.multiply(1.0f + zoomInc);
        }

        if (gc.getInput().isKeyHeld(keyCodeZoomOut)) {
            scale.multiply(1.0f - zoomInc);
        }

        Vec2df mouseWorldAfterZoom = screenToWorld((int)mouse.getX(), (int)mouse.getY());
        offset.addToX(mouseWorldBeforeZoom.getX() - mouseWorldAfterZoom.getX());
        offset.addToY(mouseWorldBeforeZoom.getY() - mouseWorldAfterZoom.getY());

    }

    @Override
    public void render(GameContainer gc) {

    }
}
