package sample.drawingPrimitives;

import olcPGEApproach.AbstractGame;
import olcPGEApproach.GameContainer;
import olcPGEApproach.gfx.HexColors;
import olcPGEApproach.gfx.images.Image;
import olcPGEApproach.gfx.images.ImageTile;

public class TestDrawingPrimitives implements AbstractGame {

    private Image[] images;

    private float alpha = 0;

    boolean increaseAlpha = true;

    @Override
    public void initialize(GameContainer gc) {
        ImageTile imageTile = new ImageTile("/dg_features32_sorted.png", 32, 32);
        images = new Image[2];
        images[0] = imageTile.getTileImage(0, 9);
        images[1] = imageTile.getTileImage(6, 4);
    }

    @Override
    public void update(GameContainer gc, float elapsedTime) {
        if ( increaseAlpha && alpha >= 254 ) {
            increaseAlpha = false;
        }
        if ( !increaseAlpha && alpha <= 1 ) {
            increaseAlpha = true;
        }

        if ( increaseAlpha ) {
            alpha += 100 * elapsedTime;
        } else {
            alpha -= 100 * elapsedTime;
        }
    }

    @Override
    public void render(GameContainer gc) {
        gc.getRenderer().clear();

        gc.getRenderer().drawFillCircle(120, 120, 20, HexColors.GREEN);

        gc.getRenderer().drawCircle(100, 100, 40, HexColors.GREEN);

        gc.getRenderer().drawFillRect(200, 200, 30, 50, HexColors.RED);

        gc.getRenderer().drawRect(300, 105, 205, 170, HexColors.CYAN);

        gc.getRenderer().drawLine(40, 140, 200, 90, HexColors.YELLOW);

        gc.getRenderer().drawTriangle(20, 10, 30, 40, 100, 60, HexColors.BLUE);

        gc.getRenderer().drawText("Hola", 10, 10, HexColors.BLUE);

        gc.getRenderer().drawFillRectangle(500, 100, 120, 70, HexColors.WINE);

        gc.getRenderer().drawRectangle(520, 120, 120, 70, HexColors.GREY);

        gc.getRenderer().drawFillCircle(260, 90, 60, 0xaa00ff00);

        int triangleW = 500;
        int triangleH = 400;
        int separation = 100;
        int halfScreenW = gc.getRenderer().getW() / 2;
        int halfScreenH = gc.getRenderer().getH() / 2;

        int redColor = ((int)(alpha) << 24 | 0xff << 16);
        int blueColor = ((int)(alpha) << 24 | 0xff);

        gc.getRenderer().drawFillTriangle(
                halfScreenW, halfScreenH - triangleH / 2,
                halfScreenW - triangleW / 2, halfScreenH + triangleH / 2,
                halfScreenW + triangleW / 2, halfScreenH + triangleH / 2,
                redColor);
        gc.getRenderer().drawFillTriangle(
                halfScreenW + separation, halfScreenH - triangleH / 2,
                halfScreenW - triangleW / 2 + separation, halfScreenH + triangleH / 2,
                halfScreenW + triangleW / 2 + separation, halfScreenH + triangleH / 2,
                blueColor);

        int mouseX = (int)gc.getInput().getMouseX();
        int mouseY = (int)gc.getInput().getMouseY();
        gc.getRenderer().drawFillRect(mouseX - 50, mouseY - 50, 100, 100, 0x99ffff00);
        gc.getRenderer().drawImage(images[0], mouseX - images[0].getW() / 2, mouseY - images[0].getH() / 2);

        gc.getRenderer().drawImage(images[1], 400, 300, HexColors.RED, HexColors.ORANGE);

        gc.getRenderer().drawText("Hola Mundo", halfScreenW, halfScreenH, HexColors.GREEN);
    }

}
