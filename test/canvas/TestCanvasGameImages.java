package canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.transform.Rotate;
import olcPGEApproach.AbstractGamePanAndZoom;
import olcPGEApproach.GameContainer;

public class TestCanvasGameImages extends AbstractGamePanAndZoom {

    private Canvas canvas;

    private double angle = 0.0;

    @Override
    public void update(GameContainer gc, float elapsedTime) {
        super.update(gc, elapsedTime);
        angle += 1;
    }

    @Override
    public void render(GameContainer gc) {
        super.render(gc);
        gc.getRenderer().clear(0xFFADD8E6);
        drawImage(gc);
    }

    private void drawCustom() {
        /*canvas.getGraphicsContext2D().fillArc(
                    ball.getPos().getX() - ball.getR(),
                    ball.getPos().getY() - ball.getR(),
                    ball.getR() * 2,
                    ball.getR() * 2,
                    0,
                    270,
                    ArcType.ROUND);*/
            /*canvas.getGraphicsContext2D().drawImage(
                    scale(canvas.snapshot(new SnapshotParameters(), null),
                            (int)gc.getImg().getWidth() / 10,
                            (int)gc.getImg().getHeight() / 10,
                            true),
                    ball.getPos().getX(), ball.getPos().getY());
            canvas.getGraphicsContext2D().setStroke(Color.BLACK);
            canvas.getGraphicsContext2D().strokeRect(
                    ball.getPos().getX(),
                    ball.getPos().getY(),
                    gc.getImg().getWidth() / 10,
                    gc.getImg().getHeight() / 10);*/
    }

    private void drawImage(GameContainer gc) {
        canvas.getGraphicsContext2D().getPixelWriter().setPixels(0, 0,
                1280, 720,
                PixelFormat.getIntArgbInstance(),
                gc.getRenderer().getP(),
                0, 1280);
    }

    /**
     * Sets the transform for the GraphicsContext to rotate around a pivot point.
     *
     * @param gc the graphics context the transform to applied to.
     * @param angle the angle of rotation.
     * @param px the x pivot co-ordinate for the rotation (in canvas co-ordinates).
     * @param py the y pivot co-ordinate for the rotation (in canvas co-ordinates).
     */
    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    /**
     * Draws an image on a graphics context.
     *
     * The image is drawn at (tlpx, tlpy) rotated by angle pivoted around the point:
     *   (tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2)
     *
     * @param gc the graphics context the image is to be drawn on.
     * @param angle the angle of rotation.
     * @param tlpx the top left x co-ordinate where the image will be plotted (in canvas co-ordinates).
     * @param tlpy the top left y co-ordinate where the image will be plotted (in canvas co-ordinates).
     */
    private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy, double w, double h) {
        gc.save(); // saves the current state on stack, including the current transform
        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx, tlpy, w, h);
        gc.restore(); // back to original state (before rotation)
    }

    public Image scale(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
        ImageView imageView = new ImageView(source);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        return imageView.snapshot(null, null);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}
