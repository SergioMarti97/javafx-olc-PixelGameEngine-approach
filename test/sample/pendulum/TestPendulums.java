package sample.pendulum;

import javafx.scene.input.KeyCode;
import olcPGEApproach.AbstractGame;
import olcPGEApproach.GameContainer;
import olcPGEApproach.gfx.HexColors;
import olcPGEApproach.vectors.points2d.Vec2df;

import java.util.ArrayList;

public class TestPendulums implements AbstractGame {

    private ArrayList<Pendulum> pendulums;

    private ArrayList<Vec2df> points;

    @Override
    public void initialize(GameContainer gc) {
        points = new ArrayList<>();

        pendulums = new ArrayList<>();
        pendulums.add(new Pendulum(
                gc.getRenderer().getW() / 2.0f,
                gc.getRenderer().getH() / 2.0f,
                125,
                (float) Math.PI / 2.0f,
                2.4f));
        pendulums.add(new Pendulum(
                pendulums.get(0).getEnd(),
                75,
                (float) Math.PI / 2.0f,
                3.4f));
        pendulums.add(new Pendulum(
                pendulums.get(1).getEnd(),
                5,
                (float) Math.PI / 2.0f,
                35.3f));
        pendulums.add(new Pendulum(
                pendulums.get(2).getEnd(),
                9,
                (float) Math.PI / 2.0f,
                4.3f));
        pendulums.add(new Pendulum(
                pendulums.get(3).getEnd(),
                11,
                (float) Math.PI / 2.0f,
                10.4f));
        pendulums.add(new Pendulum(
                pendulums.get(4).getEnd(),
                12,
                (float) Math.PI / 2.0f,
                12f));
        pendulums.add(new Pendulum(
                pendulums.get(5).getEnd(),
                10,
                (float) Math.PI / 2.0f,
                10f));
    }

    @Override
    public void update(GameContainer gc, float elapsedTime) {
        for ( Pendulum p : pendulums ) {
            p.update(0.25f * elapsedTime);
        }

        points.add(new Vec2df(pendulums.get(pendulums.size() -1).getEnd()));

        if ( gc.getInput().isKeyHeld(KeyCode.U) ) {
            System.out.println("Estas apretando la 'u'");
        }
    }

    private void drawPendulum(GameContainer gc, Pendulum p, int color) {
        gc.getRenderer().drawLine(
                (int)p.getOrigin().getX(),
                (int)p.getOrigin().getY(),
                (int)p.getEnd().getX(),
                (int)p.getEnd().getY(),
                color);
    }

    @Override
    public void render(GameContainer gc) {
        gc.getRenderer().clear();
        if ( points.size() > 2 ) {
            for (int i = 0; i < points.size(); i++) {
                if ( i > 0 ) {
                    gc.getRenderer().drawLine(
                            (int) points.get(i - 1).getX(), (int) points.get(i - 1).getY(),
                            (int) points.get(i).getX(), (int) points.get(i).getY(), HexColors.BLUE);
                }
            }
        }

        float increment = 255.0f / pendulums.size();
        for ( int i = 0; i < pendulums.size(); i++ ) {
            drawPendulum(gc, pendulums.get(i), 0xff << 24 | (int)(i * increment) << 16 | (int)(i * increment) << 8);
        }
    }

}
