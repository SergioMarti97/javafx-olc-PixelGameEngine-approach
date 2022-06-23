package canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import olcPGEApproach.AbstractGamePanAndZoom;
import olcPGEApproach.GameContainer;
import olcPGEApproach.demos.BouncingBall;
import olcPGEApproach.demos.BouncingBalls;
import olcPGEApproach.gfx.CustomColor;
import olcPGEApproach.vectors.points2d.Vec2dd;
import olcPGEApproach.vectors.points2d.Vec2df;
import olcPGEApproach.vectors.points2d.Vec2di;

import java.util.ArrayList;
import java.util.Random;

public class TestCanvasGameBalls extends AbstractGamePanAndZoom {

    private BouncingBalls balls;

    private ArrayList<CustomBouncingBall> customBouncingBalls;

    private Canvas canvas;

    private Image[] img;

    private Random rnd;

    private BouncingBall selectedBall = null;

    private CustomBouncingBall selectedCustomBouncingBall = null;

    private final Vec2df offsetPrev = new Vec2df();

    private boolean drawTriangles = true;

    private boolean triangleTexture = true;

    private boolean showText = true;

    private boolean runTime = false;

    private static class CustomBouncingBall extends BouncingBall {

        private float time = 10;

        private final int MAX_POINTS = 3;

        private final Vec2df[] points;

        private final float[] alphas;

        private final float[] incAlphas;

        public CustomBouncingBall(BouncingBall b) {
            super(b.getPos(), b.getVel(), b.getR(), b.getCol());
            setId(b.getId());
            Random rnd = new Random();
            alphas = new float[MAX_POINTS];
            for (int i = 0; i < MAX_POINTS; i++) {
                alphas[i] = 2.0f * (float)Math.PI * rnd.nextFloat();
            }
            points = new Vec2df[MAX_POINTS];
            for (int i = 0; i < MAX_POINTS; i++) {
                points[i] = generateP(alphas[i]);
            }
            incAlphas = new float[MAX_POINTS];
            for (int i = 0; i < MAX_POINTS; i++) {
                incAlphas[i] = rnd.nextFloat();
            }
        }

        public void update(float dt) {
            for (int i = 0; i < MAX_POINTS; i++) {
                alphas[i] += dt * incAlphas[i];
                if (alphas[i] >= Float.MAX_VALUE) {
                    alphas[i] = 0.0f;
                }
                updateP(points[i], alphas[i]);
            }
        }

        private Vec2df generateP(float alpha) {
            return new Vec2df(getR() * (float)Math.cos(alpha),
                    getR() * (float)Math.sin(alpha));
        }

        private void updateP(Vec2df p, float alpha) {
            p.set(getR() * (float)Math.cos(alpha),
                    getR() * (float)Math.sin(alpha));
        }

        public Vec2df[] getP() {
            return points;
        }

        public float getTime() {
            return time;
        }

        public void setTime(float time) {
            this.time = time;
        }

    }

    @Override
    public void initialize(GameContainer gc) {
        super.initialize(gc);

        rnd = new Random();
        balls = new BouncingBalls(100, 25, 15, 0, 1280, 720);
        customBouncingBalls = new ArrayList<>();

        img = new Image[] {
                new Image("/img/img1.png"),
                new Image("/img/img2.jpg"),
                new Image("/img/img3.jpg"),
        };
    }

    @Override
    public void update(GameContainer gc, float elapsedTime) {
        super.update(gc, elapsedTime);

        balls.update(elapsedTime);

        for (CustomBouncingBall b : customBouncingBalls) {
            b.update(elapsedTime);
        }

        if (gc.getInput().isButtonHeld(MouseButton.SECONDARY)) {
            BouncingBall ball = balls.rndBall();
            ball.setId(balls.getNumBalls());
            ball.getPos().set(screenToWorld((int)gc.getInput().getMouseX(), (int)gc.getInput().getMouseY()));
            balls.add(ball);
            customBouncingBalls.add(new CustomBouncingBall(ball));
        }

        if (gc.getInput().isKeyHeld(KeyCode.DOWN)) {
            if (balls.getNumBalls() > 0) {
                int index = rnd.nextInt(balls.getNumBalls());
                balls.remove(index);
                customBouncingBalls.remove(index);
            }
        }

        if (gc.getInput().isKeyDown(KeyCode.P)) { // picture
            triangleTexture = !triangleTexture;
        }

        if (gc.getInput().isKeyDown(KeyCode.T)) { // triangle
            drawTriangles = !drawTriangles;
        }

        if (gc.getInput().isKeyDown(KeyCode.Y)) {
            showText = !showText;
        }

        if (gc.getInput().isButtonDown(MouseButton.MIDDLE)) {
            Vec2df mouse = screenToWorld((int) gc.getInput().getMouseX(), (int) gc.getInput().getMouseY());

            selectedBall = null;
            selectedCustomBouncingBall = null;

            for (BouncingBall b : balls.getBalls()) {
                float diffX = b.getPos().getX() - mouse.getX();
                float diffY = b.getPos().getY() - mouse.getY();

                if (diffX * diffX + diffY * diffY <= b.getR() * b.getR()) {
                    offsetPrev.set(offset);
                    selectedBall = b;
                    for (CustomBouncingBall cbb : customBouncingBalls) {
                        if (cbb.getId() == selectedBall.getId()) {
                            selectedCustomBouncingBall = cbb;
                            break;
                        }
                    }
                }
            }
        }

        if (gc.getInput().isKeyDown(KeyCode.TAB)) {
            runTime = !runTime;
        }

        // update time of the balls
        if (runTime) {
            ArrayList<Integer> indexToDie = new ArrayList<>();
            for (CustomBouncingBall b : customBouncingBalls) {
                b.setTime(b.getTime() - elapsedTime);

                if (b.getTime() <= 0.0f) {
                    indexToDie.add(b.getId());
                }
            }
            balls.getBalls().removeIf(n -> indexToDie.contains(n.getId()));
            customBouncingBalls.removeIf(n -> indexToDie.contains(n.getId()));
        }

    }

    private void drawBalls() {
        for (BouncingBall b : balls.getBalls()) {
            CustomColor c = new CustomColor(b.getCol());
            canvas.getGraphicsContext2D().setFill(Color.rgb(c.getR(), c.getG(), c.getB(), 0.5f));

            Vec2di pos = worldToScreen(b.getPos());
            int r = (int)(b.getR() * scale.getX());

            canvas.getGraphicsContext2D().fillOval(pos.getX() - r, pos.getY() - r, r * 2, r * 2);

            canvas.getGraphicsContext2D().setFill(Color.BLACK);
            canvas.getGraphicsContext2D().fillRect(pos.getX(), pos.getY(), 2 * scale.getX(), 2 * scale.getY());
        }
    }

    private void drawTriangleBalls() {
        for (CustomBouncingBall b : customBouncingBalls) {
            // transform points world to screen
            Vec2di[] p = new Vec2di[b.MAX_POINTS];
            for (int i = 0; i < b.MAX_POINTS; i++) {
                p[i] = worldToScreen(b.getP()[i].getX() + b.getPos().getX(), b.getP()[i].getY() + b.getPos().getY());
            }

            // set the fill
            if (triangleTexture) {
                canvas.getGraphicsContext2D().setFill(new ImagePattern(img[(b.getCol() >> 16 & 255) % img.length], 0, 0, 1, 1, true));
            } else {
                CustomColor c = new CustomColor(b.getCol());
                canvas.getGraphicsContext2D().setFill(Color.rgb(c.getR(), c.getG(), c.getB(), 0.75));
            }

            // draw the triangle
            canvas.getGraphicsContext2D().fillPolygon(
                    new double[] {p[0].getX(), p[1].getX(), p[2].getX()},
                    new double[] {p[0].getY(), p[1].getY(), p[2].getY()},
                    b.MAX_POINTS);

            // draw the text
            if (showText) {
                canvas.getGraphicsContext2D().setFill(Color.BLACK);
                Vec2di o = worldToScreen(b.getPos());
                String text = String.format("%.3f", b.getTime());
                o.addToX(-(int) (canvas.getGraphicsContext2D().getFont().getSize() * text.length()) / 4);
                canvas.getGraphicsContext2D().fillText(text, o.getX(), o.getY());
            }
        }
    }

    private void draw() {
        // background
        canvas.getGraphicsContext2D().setFill(Color.rgb(0xAD, 0xD8, 0xE6));
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // rectangle of the balls
        Vec2di s = worldToScreen(0, 0);
        Vec2di e = new Vec2di((int)(balls.getScreenSize().getX() * scale.getX()), (int)(balls.getScreenSize().getY() * scale.getY()));

        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(s.getX(), s.getY(), e.getX(), e.getY());

        canvas.getGraphicsContext2D().setFill(Color.BLACK);
        canvas.getGraphicsContext2D().strokeRect(s.getX(), s.getY(), e.getX(), e.getY());

        // draw balls
        drawBalls();

        // draw triangles of the balls
        if (drawTriangles) {
            drawTriangleBalls();
        }

        // draw some text
        s = new Vec2di(5, 5);
        e = new Vec2di(120, 120);
        canvas.getGraphicsContext2D().setFill(Color.rgb(255, 255, 255, 0.25));
        canvas.getGraphicsContext2D().fillRect(s.getX(), s.getY(), e.getX(), e.getY());

        canvas.getGraphicsContext2D().setFill(Color.BLACK);
        canvas.getGraphicsContext2D().strokeRect(s.getX(), s.getY(), e.getX(), e.getY());

        String text = String.format("Num balls: %d", balls.getNumBalls());

        canvas.getGraphicsContext2D().setFill(Color.BLACK);
        canvas.getGraphicsContext2D().fillText(text,s.getX() + 2, s.getY() + 15);

        if (selectedBall != null) {
            CustomColor c = new CustomColor(selectedBall.getCol());
            Vec2dd o = new Vec2dd(
                    (e.getX() / 2.0) + s.getX() - selectedBall.getR(),
                    (s.getY() + e.getY()) - 2 * selectedBall.getR() - 10
            );
            canvas.getGraphicsContext2D().setFill(Color.WHITE);
            canvas.getGraphicsContext2D().fillOval(
                    (e.getX() / 2.0) + s.getX() - selectedBall.getR(),
                    (s.getY() + e.getY()) - 2 * selectedBall.getR() - 10,
                    selectedBall.getR() * 2, selectedBall.getR() * 2);
            canvas.getGraphicsContext2D().setFill(Color.rgb(c.getR(), c.getG(), c.getB(), 0.5));
            canvas.getGraphicsContext2D().fillOval(
                    (e.getX() / 2.0) + s.getX() - selectedBall.getR(),
                    (s.getY() + e.getY()) - 2 * selectedBall.getR() - 10,
                    selectedBall.getR() * 2, selectedBall.getR() * 2);

            if (drawTriangles) {
                CustomBouncingBall b = selectedCustomBouncingBall;
                o.add(b.getR());
                if (triangleTexture) {
                    canvas.getGraphicsContext2D().setFill(new ImagePattern(img[(b.getCol() >> 16 & 255) % img.length], 0, 0, 1, 1, true));
                } else {
                    canvas.getGraphicsContext2D().setFill(Color.rgb(c.getR(), c.getG(), c.getB(), 0.75));
                }
                canvas.getGraphicsContext2D().fillPolygon(
                        new double[]{b.getP()[0].getX() + o.getX(), b.getP()[1].getX() + o.getX(), b.getP()[2].getX() + o.getX()},
                        new double[]{b.getP()[0].getY() + o.getY(), b.getP()[1].getY() + o.getY(), b.getP()[2].getY() + o.getY()},
                        b.MAX_POINTS);
            }
        }
    }

    @Override
    public void render(GameContainer gc) {
        draw();
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

}
