package sample.triangles;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import olcPGEApproach.AbstractGame;
import olcPGEApproach.GameContainer;
import olcPGEApproach.gfx.HexColors;
import olcPGEApproach.gfx.Renderer;
import olcPGEApproach.vectors.points2d.Vec2df;

/**
 * Triangle rasterization: https://web.archive.org/web/20141221050705/http://forum.devmaster.net/t/advanced-rasterization/6145
 * Vertices are clockwise or anticlockwise: https://algs4.cs.princeton.edu/91primitives/#:~:text=Use%20the%20following%20determinant%20formula,b%2D%3Ec%20are%20collinear.
 * Historia del scan line filling algorithm https://computergraphics.stackexchange.com/questions/5134/why-is-the-scan-line-filling-algorithm-so-seemingly-over-complicated
 * Un blog que habla de esto: https://www.scratchapixel.com/lessons/3d-basic-rendering/rasterization-practical-implementation/rasterization-practical-implementation
 * Quick inverse square root: https://www.youtube.com/watch?v=p8u_k2LIZyo&t=703s
 */
public class TestTriangles implements AbstractGame {
    
    private final int pointSize = 15;

    private Vec2df[] points;

    private boolean showText = true;

    private int numPixelsTested = 0;

    @Override
    public void initialize(GameContainer gc) {
        points = new Vec2df[] {
                new Vec2df(100, 100),
                new Vec2df(150, 150),
                new Vec2df(125, 200),

                //new Vec2df(130, 50),
        };
    }

    @Override
    public void update(GameContainer gc, float elapsedTime) {
        if (gc.getInput().isKeyDown(KeyCode.SPACE)) {
            showText = !showText;
        }
        
        if (gc.getInput().isButtonHeld(MouseButton.PRIMARY)) {
            int dist = (pointSize + 5) * (pointSize + 5);
            for (Vec2df p : points) {
                if (p.dist2((float)gc.getInput().getMouseX(), (float)gc.getInput().getMouseY()) < dist) {
                    p.setX((float) gc.getInput().getMouseX());
                    p.setY((float) gc.getInput().getMouseY());
                }
            }
        }
    }

    private void drawArrow(Renderer r, float x1, float y1, float x2, float y2, int col) {
        r.drawLine((int)x1, (int)y1, (int)x2, (int)y2, col);
        r.drawFillCircle((int)x2, (int)y2, 3, col);
    }

    private void drawArrow(Renderer r, int x1, int y1, int x2, int y2, int col) {
        r.drawLine(x1, y1, x2, y2, col);
        r.drawFillCircle(x2, y2, 3, col);
    }

    // Counter clock wise test methods

    public static float ccw(float ax, float ay, float bx, float by, float cx, float cy) {
        return (bx - ax) * (cy - ay) - (cx - ax) * (by - ay);
    }

    public static int ccw(int ax, int ay, int bx, int by, int cx, int cy) {
        return (bx - ax) * (cy - ay) - (cx - ax) * (by - ay);
    }

    /**
     * First iteration
     */
    private void triangleHalfSpace1(Renderer r, Vec2df v1, Vec2df v2, Vec2df v3, int color) {
        // This part is not needed now
        float y1 = v1.getY();
        float y2 = v2.getY();
        float y3 = v3.getY();

        float x1 = v1.getX();
        float x2 = v2.getX();
        float x3 = v3.getX();

        // Firstly: make sure the triangle's vertices are in counter-clockwise order
        if (ccw(x1, y1, x2, y2, x3, y3) > 0) {
            // then change one vertex by other vertex
            float temp = x1;
            x1 = x2;
            x2 = temp;

            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        // Bounding rectangle:
        int minX = (int)Math.min(Math.min(x1, x2), x3);
        int maxX = (int)Math.max(Math.max(x1, x2), x3);
        int minY = (int)Math.min(Math.min(y1, y2), y3);
        int maxY = (int)Math.max(Math.max(y1, y2), y3);

        // Scan through bounding rectangle
        for (int y = minY; y < maxY; y++) {
            for (int x = minX; x < maxX; x++) {
                // When all half-space functions positive, pixel is in triangle
                if (
                        (x1 - x2) * (y - y1) - (y1 - y2) * (x - x1) > 0 &&
                        (x2 - x3) * (y - y2) - (y2 - y3) * (x - x2) > 0 &&
                        (x3 - x1) * (y - y3) - (y3 - y1) * (x - x3) > 0
                ) {
                    r.setPixel(x, y, color);
                }
            }
        }

        // debug //
        // num pixels tested
        numPixelsTested = (maxX - minX) * (maxY - minY);
        // show the bounding rectangle
        r.drawRectangle(minX, minY, (maxX - minX), (maxY - minY), HexColors.WINE);
        // arrows
        drawArrow(r, x1, y1, x2, y2, HexColors.RED);
        drawArrow(r, x2, y2, x3, y3, HexColors.GREEN);
        drawArrow(r, x3, y3, x1, y1, HexColors.BLUE);
    }

    /**
     * Second iteration
     */
    private void triangleHalfSpace2(Renderer r, Vec2df v1, Vec2df v2, Vec2df v3, int color) {
        // This part is not needed now
        float y1 = v1.getY();
        float y2 = v2.getY();
        float y3 = v3.getY();

        float x1 = v1.getX();
        float x2 = v2.getX();
        float x3 = v3.getX();

        // Firstly: make sure the triangle's vertices are in counter-clockwise order
        if (ccw(x1, y1, x2, y2, x3, y3) > 0) {
            // then change one vertex by other vertex
            float temp = x1;
            x1 = x2;
            x2 = temp;

            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        // Second: Bounding rectangle. test all space is a waste of time
        int minX = (int)Math.min(Math.min(x1, x2), x3);
        int maxX = (int)Math.max(Math.max(x1, x2), x3);
        int minY = (int)Math.min(Math.min(y1, y2), y3);
        int maxY = (int)Math.max(Math.max(y1, y2), y3);

        // Third: some pre-cals can be done! call them deltas
        float dx12 = x1 - x2;
        float dx23 = x2 - x3;
        float dx31 = x3 - x1;

        float dy12 = y1 - y2;
        float dy23 = y2 - y3;
        float dy31 = y3 - y1;

        // Scan through bounding rectangle
        for (int y = minY; y < maxY; y++) {
            for (int x = minX; x < maxX; x++) {
                // When all half-space functions positive, pixel is inside triangle
                if (
                        dx12 * (y - y1) - dy12 * (x - x1) > 0 &&
                        dx23 * (y - y2) - dy23 * (x - x2) > 0 &&
                        dx31 * (y - y3) - dy31 * (x - x3) > 0
                ) {
                    r.setPixel(x, y, color);
                }
            }
        }

        // debug //
        // num pixels tested
        numPixelsTested = (maxX - minX) * (maxY - minY);
        // show the bounding rectangle
        r.drawRectangle(minX, minY, (maxX - minX), (maxY - minY), HexColors.WINE);
        // arrows
        drawArrow(r, x1, y1, x2, y2, HexColors.RED);
        drawArrow(r, x2, y2, x3, y3, HexColors.GREEN);
        drawArrow(r, x3, y3, x1, y1, HexColors.BLUE);
    }

    /**
     * Third iteration
     */
    private void triangleHalfSpace3(Renderer r, Vec2df v1, Vec2df v2, Vec2df v3, int color) {
        float y1 = v1.getY();
        float y2 = v2.getY();
        float y3 = v3.getY();

        float x1 = v1.getX();
        float x2 = v2.getX();
        float x3 = v3.getX();

        // Firstly: make sure the triangle's vertices are in counter-clockwise order
        if (ccw(x1, y1, x2, y2, x3, y3) > 0) {
            // then change one vertex by other vertex
            float temp = x1;
            x1 = x2;
            x2 = temp;

            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        // Second: Bounding rectangle. test all space is a waste of time
        int minX = (int)Math.min(Math.min(x1, x2), x3);
        int maxX = (int)Math.max(Math.max(x1, x2), x3);
        int minY = (int)Math.min(Math.min(y1, y2), y3);
        int maxY = (int)Math.max(Math.max(y1, y2), y3);

        // Third: some pre-cals can be done! call them deltas
        float dx12 = x1 - x2;
        float dx23 = x2 - x3;
        float dx31 = x3 - x1;

        float dy12 = y1 - y2;
        float dy23 = y2 - y3;
        float dy31 = y3 - y1;

        // Constant part of half-edge functions
        float c1 = dy12 * x1 - dx12 * y1;
        float c2 = dy23 * x2 - dx23 * y2;
        float c3 = dy31 * x3 - dx31 * y3;

        float cy1 = c1 + dx12 * minY - dy12 * minX;
        float cy2 = c2 + dx23 * minY - dy23 * minX;
        float cy3 = c3 + dx31 * minY - dy31 * minX;

        // Scan through bounding rectangle
        for (int y = minY; y < maxY; y++) {

            float cx1 = cy1;
            float cx2 = cy2;
            float cx3 = cy3;

            for (int x = minX; x < maxX; x++) {
                // When all half-space functions positive, pixel is inside triangle
                if (cx1 > 0 && cx2 > 0 && cx3 > 0) {
                    r.setPixel(x, y, color);
                }

                cx1 -= dy12;
                cx2 -= dy23;
                cx3 -= dy31;
            }

            cy1 += dx12;
            cy2 += dx23;
            cy3 += dx31;
        }

        // debug //
        // num pixels tested
        numPixelsTested = (maxX - minX) * (maxY - minY);
        // show the bounding rectangle
        r.drawRectangle(minX, minY, (maxX - minX), (maxY - minY), HexColors.WINE);
        // arrows
        //drawArrow(r, x1, y1, x2, y2, HexColors.RED);
        //drawArrow(r, x2, y2, x3, y3, HexColors.GREEN);
        //drawArrow(r, x3, y3, x1, y1, HexColors.BLUE);
    }

    /**
     * Four iteration
     */
    private void triangleHalfSpace4(Renderer r, Vec2df v1, Vec2df v2, Vec2df v3, int color) {
        // Now this part is needed
        int y1 = Math.round(16.0f * v1.getY());
        int y2 = Math.round(16.0f * v2.getY());
        int y3 = Math.round(16.0f * v3.getY());

        int x1 = Math.round(16.0f * v1.getX());
        int x2 = Math.round(16.0f * v2.getX());
        int x3 = Math.round(16.0f * v3.getX());

        // Firstly: make sure the triangle's vertices are in counter-clockwise order
        if (ccw(x1, y1, x2, y2, x3, y3) > 0) {
            // then change one vertex by other vertex
            int temp = x1;
            x1 = x2;
            x2 = temp;

            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        // Second: Bounding rectangle. test all space is a waste of time
        int minX = (Math.min(Math.min(x1, x2), x3) + 0xF) >> 4;
        int maxX = (Math.max(Math.max(x1, x2), x3) + 0xF) >> 4;
        int minY = (Math.min(Math.min(y1, y2), y3) + 0xF) >> 4;
        int maxY = (Math.max(Math.max(y1, y2), y3) + 0xF) >> 4;

        // Third: some pre-cals can be done!
        // Deltas
        int dx12 = x1 - x2;
        int dx23 = x2 - x3;
        int dx31 = x3 - x1;

        int dy12 = y1 - y2;
        int dy23 = y2 - y3;
        int dy31 = y3 - y1;

        // Fixed-point deltas
        int fdx12 = dx12 << 4;
        int fdx23 = dx23 << 4;
        int fdx31 = dx31 << 4;

        int fdy12 = dy12 << 4;
        int fdy23 = dy23 << 4;
        int fdy31 = dy31 << 4;

        // Constant part of half-edge functions
        // Half-edge constants
        int c1 = dy12 * x1 - dx12 * y1;
        int c2 = dy23 * x2 - dx23 * y2;
        int c3 = dy31 * x3 - dx31 * y3;

        // Correct for fill convention
        if (dy12 < 0 || (dy12 == 0 && dx12 > 0)) {
            c1++;
        }
        if (dy23 < 0 || (dy23 == 0 && dx23 > 0)) {
            c2++;
        }
        if (dy31 < 0 || (dy31 == 0 && dx31 > 0)) {
            c3++;
        }

        int cy1 = c1 + dx12 * (minY << 4) - dy12 * (minX << 4);
        int cy2 = c2 + dx23 * (minY << 4) - dy23 * (minX << 4);
        int cy3 = c3 + dx31 * (minY << 4) - dy31 * (minX << 4);

        // Scan through bounding rectangle
        for (int y = minY; y < maxY; y++) {

            int cx1 = cy1;
            int cx2 = cy2;
            int cx3 = cy3;

            for (int x = minX; x < maxX; x++) {
                // When all half-space functions positive, pixel is inside triangle
                if (cx1 > 0 && cx2 > 0 && cx3 > 0) {
                    r.setPixel(x, y, color);
                }

                cx1 -= fdy12;
                cx2 -= fdy23;
                cx3 -= fdy31;
            }

            cy1 += fdx12;
            cy2 += fdx23;
            cy3 += fdx31;
        }

        // debug //
        // num pixels tested
        numPixelsTested = (maxX - minX) * (maxY - minY);
        // show the bounding rectangle
        r.drawRectangle(minX, minY, (maxX - minX), (maxY - minY), HexColors.WINE);
        // arrows
        //drawArrow(r, x1 / 16, y1 / 16, x2 / 16, y2 / 16, HexColors.RED);
        //drawArrow(r, x2 / 16, y2 / 16, x3 / 16, y3 / 16, HexColors.GREEN);
        //drawArrow(r, x3 / 16, y3 / 16, x1 / 16, y1 / 16, HexColors.BLUE);
    }

    /**
     * Five iteration
     */
    private void triangleHalfSpace5(Renderer r, Vec2df v1, Vec2df v2, Vec2df v3, int color) {
        // Now this part is needed
        int y1 = Math.round(16.0f * v1.getY());
        int y2 = Math.round(16.0f * v2.getY());
        int y3 = Math.round(16.0f * v3.getY());

        int x1 = Math.round(16.0f * v1.getX());
        int x2 = Math.round(16.0f * v2.getX());
        int x3 = Math.round(16.0f * v3.getX());

        // Firstly: make sure the triangle's vertices are in counter-clockwise order
        if ((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1) > 0) { // ccw(x1, y1, x2, y2, x3, y3) > 0
            // change one vertex by other vertex
            int temp = x1;
            x1 = x2;
            x2 = temp;

            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        // Second: Bounding rectangle. test all space is a waste of time
        int minX = (Math.min(Math.min(x1, x2), x3) + 0xF) >> 4;
        int maxX = (Math.max(Math.max(x1, x2), x3) + 0xF) >> 4;
        int minY = (Math.min(Math.min(y1, y2), y3) + 0xF) >> 4;
        int maxY = (Math.max(Math.max(y1, y2), y3) + 0xF) >> 4;

        // Third: some pre-cals can be done!
        // Deltas
        int dx12 = x1 - x2;
        int dx23 = x2 - x3;
        int dx31 = x3 - x1;

        int dy12 = y1 - y2;
        int dy23 = y2 - y3;
        int dy31 = y3 - y1;

        // Fixed-point deltas
        int fdx12 = dx12 << 4;
        int fdx23 = dx23 << 4;
        int fdx31 = dx31 << 4;

        int fdy12 = dy12 << 4;
        int fdy23 = dy23 << 4;
        int fdy31 = dy31 << 4;

        // block size standard 8x8 (must be power of two)
        int q = 8;

        // Constant part of half-edge functions
        // Half-edge constants
        int c1 = dy12 * x1 - dx12 * y1;
        int c2 = dy23 * x2 - dx23 * y2;
        int c3 = dy31 * x3 - dx31 * y3;

        // Correct for fill convention
        if (dy12 < 0 || (dy12 == 0 && dx12 > 0)) {
            c1++;
        }
        if (dy23 < 0 || (dy23 == 0 && dx23 > 0)) {
            c2++;
        }
        if (dy31 < 0 || (dy31 == 0 && dx31 > 0)) {
            c3++;
        }

        // Scan through bounding rectangle
        for (int y = minY; y < maxY; y += q) {
            for (int x = minX; x < maxX; x += q) {
                // Corners of block
                int corner0x = x << 4;
                int corner1x = (x + q - 1) << 4;
                int corner0y = y << 4;
                int corner1y = (y + q - 1) << 4;

                // Evaluate the half-space functions
                int cy1 = c1 + dx12 * corner0y - dy12 * corner0x;
                int cy2 = c2 + dx23 * corner0y - dy23 * corner0x;
                int cy3 = c3 + dx31 * corner0y - dy31 * corner0x;

                int a00 = (cy1 > 0) ? 1 : 0;
                int a10 = (c1 + dx12 * corner0y - dy12 * corner1x > 0) ? 1 : 0;
                int a01 = (c1 + dx12 * corner1y - dy12 * corner0x > 0) ? 1 : 0;
                int a11 = (c1 + dx12 * corner1y - dy12 * corner1x > 0) ? 1 : 0;
                int a = (a00) | (a10 << 1) | (a01 << 2) | (a11 << 3);

                int b00 = (cy2 > 0) ? 1 : 0;
                int b10 = (c2 + dx23 * corner0y - dy23 * corner1x > 0) ? 1 : 0;
                int b01 = (c2 + dx23 * corner1y - dy23 * corner0x > 0) ? 1 : 0;
                int b11 = (c2 + dx23 * corner1y - dy23 * corner1x > 0) ? 1 : 0;
                int b = (b00) | (b10 << 1) | (b01 << 2) | (b11 << 3);

                int c00 = (cy3 > 0) ? 1 : 0;
                int c10 = (c3 + dx31 * corner0y - dy31 * corner1x > 0) ? 1 : 0;
                int c01 = (c3 + dx31 * corner1y - dy31 * corner0x > 0) ? 1 : 0;
                int c11 = (c3 + dx31 * corner1y - dy31 * corner1x > 0) ? 1 : 0;
                int c = (c00) | (c10 << 1) | (c01 << 2) | (c11 << 3);

                // Skip block when outside and edge
                if (a == 0x0 || b == 0x0 || c == 0x0) {
                    continue;
                }

                // Accept whole block when totally covered
                if (a == 0xF && b == 0xF && c == 0xF) {
                    for (int iy = y; iy < (y + q); iy++) {
                        for (int ix = x; ix < (x + q); ix++) {
                            r.setPixel(ix, iy, 0xFF007F00); // 0xFF007F00
                        }
                    }
                } else { // Partially covered block
                    for (int iy = y; iy < (y + q); iy++) {
                        int cx1 = cy1;
                        int cx2 = cy2;
                        int cx3 = cy3;

                        for (int ix = x; ix < (x + q); ix++) {
                            // When all half-space functions positive, pixel is inside triangle
                            if (cx1 > 0 && cx2 > 0 && cx3 > 0) {
                                r.setPixel(ix, iy, 0xFF00007F); //
                            }

                            cx1 -= fdy12;
                            cx2 -= fdy23;
                            cx3 -= fdy31;
                        }

                        cy1 += fdx12;
                        cy2 += fdx23;
                        cy3 += fdx31;
                    }
                }

            }
        }

        r.drawRectangle(minX, minY, (maxX - minX), (maxY - minY), HexColors.WINE);
    }

    private double acc1 = 0;
    private double acc2 = 0;
    private double showTime1 = 0;
    private double showTime2 = 0;
    private int count = 0;

    @Override
    public void render(GameContainer gc) {
        gc.getRenderer().clear(HexColors.BLACK);

        long s = System.nanoTime();
        triangleHalfSpace5(gc.getRenderer(),
                points[0],
                points[1],
                points[2],
                HexColors.CYAN);
        long e = System.nanoTime();
        double t1 = (e - s ) / 1000000000F;
        acc1 += t1;

        s = System.nanoTime();
        gc.getRenderer().drawFillTriangle(
                (int)points[0].getX() + 200, (int)points[0].getY(),
                (int)points[1].getX() + 200, (int)points[1].getY(),
                (int)points[2].getX() + 200, (int)points[2].getY(),
                HexColors.CYAN
        );
        e = System.nanoTime();
        double t2 = (e - s) / 1000000000F;
        acc2 += t2;

        count++;
        if (count > 20) {
            showTime1 = acc1 / count;
            showTime2 = acc2 / count;
            acc1 = 0;
            acc2 = 0;
            count = 0;
        }

        for (Vec2df p : points) {
            gc.getRenderer().drawCircle((int)p.getX(), (int)p.getY(), pointSize, HexColors.YELLOW);
            if (showText) {
                gc.getRenderer().drawText(p.toString(), (int) p.getX(), (int) p.getY(), HexColors.WHITE);
            }
        }

        int y = 10;
        final int fontHeight = gc.getRenderer().getFont().getFontImage().getH();
        //gc.getRenderer().drawText(String.format("Pixels tested: %d", numPixelsTested), 10, y, HexColors.WHITE);
        //y += fontHeight;
        gc.getRenderer().drawText(String.format("Time required for half-space algorithm: %f", showTime1), 10, y, HexColors.WHITE);
        y += fontHeight;
        gc.getRenderer().drawText(String.format("Time required for scan-line algorithm: %f", showTime2), 10, y, HexColors.WHITE);
        y += fontHeight;

        double ratio;
        boolean win;
        if (showTime1 > showTime2) {
            win = false;
            ratio = showTime2 / showTime1 * 100;
        } else {
            win = true;
            ratio = showTime1 / showTime2 * 100;
        }
        String result = (win ? "Half-space wins!" : "Scan-line wins :(") + String.format(" ratio: %f%%", ratio);
        gc.getRenderer().drawText(result, 10, y, win ? HexColors.RED : HexColors.WHITE);

        gc.getRenderer().drawFillCircle((int)gc.getInput().getMouseX(), (int)gc.getInput().getMouseY(), 5, HexColors.ORANGE);
    }

}
