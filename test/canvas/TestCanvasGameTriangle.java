package canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import olcPGEApproach.AbstractGamePanAndZoom;
import olcPGEApproach.GameContainer;
import olcPGEApproach.vectors.points2d.Vec2df;
import olcPGEApproach.vectors.points2d.Vec2di;

import java.util.Random;

public class TestCanvasGameTriangle extends AbstractGamePanAndZoom {

    private Canvas canvas;

    private Image[] icons;

    private Image[] img;

    private Random rnd;

    private final float VERTEX_R = 10;

    private final Vec2df[] t = new Vec2df[] {new Vec2df(20, 20), new Vec2df(120, 200), new Vec2df(50, 250)};

    @Override
    public void initialize(GameContainer gc) {
        super.initialize(gc);
        rnd = new Random();

        icons = new Image[] {
                new Image("/icons/icon_1.png"),
                new Image("/icons/icon_2.png"),
                new Image("/icons/icon_3.png"),
                new Image("/icons/icon_4.png"),
                new Image("/icons/icon_5.png"),
        };
        img = new Image[] {
                new Image("/img/img1.png"),
                new Image("/img/img2.jpg"),
                new Image("/img/img3.jpg"),
        };

    }

    @Override
    public void update(GameContainer gc, float elapsedTime) {
        super.update(gc, elapsedTime);

        if (gc.getInput().isButtonHeld(MouseButton.PRIMARY)) {
            Vec2df mouse = screenToWorld((int) gc.getInput().getMouseX(), (int) gc.getInput().getMouseY());
            for (Vec2df v : t) {
                float diffX = v.getX() - mouse.getX();
                float diffY = v.getY() - mouse.getY();

                if (diffX * diffX + diffY * diffY <= VERTEX_R * VERTEX_R) {
                    v.set(mouse.getX(), mouse.getY());
                }
            }
        }
    }

    private void drawTriangle() {
        Vec2di[] p = new Vec2di[3];
        for (int i = 0; i < 3; i++) {
            p[i] = worldToScreen(t[i]);
        }
        canvas.getGraphicsContext2D().setFill(new ImagePattern(img[1], 0, 0, 1, 1, true));
        canvas.getGraphicsContext2D().fillPolygon(
                new double[] {p[0].getX(), p[1].getX(), p[2].getX()},
                new double[] {p[0].getY(), p[1].getY(), p[2].getY()},
                3);
        canvas.getGraphicsContext2D().setFill(Color.rgb(0x42, 0xF5, 0x57, 0.50));
        int r = (int)(VERTEX_R * scale.getX());
        for (Vec2di v : p) {
            canvas.getGraphicsContext2D().fillOval(v.getX() - r, v.getY() - r, r * 2, r * 2);
        }
    }

    @Override
    public void render(GameContainer gc) {
        super.render(gc);
        drawTriangle();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

}
