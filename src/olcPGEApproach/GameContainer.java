package olcPGEApproach;

import javafx.scene.Node;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import olcPGEApproach.gfx.Renderer;

/**
 * This class is a game container,
 * it provides all the needed methods to
 * build a game like the applications
 * coded in olcPixelGameEngine.h library
 *
 * Is needed specify what node of
 * the javafx application is going
 * to run the game
 * So the user inputs can interact
 * with the game
 */
public class GameContainer {

    private AbstractGame game;

    private WritableImage img;

    private CustomTimer timer;

    private Renderer renderer;

    private Input input;

    private Node node;

    /**
     * Constructor
     */
    public GameContainer(AbstractGame game, int screenWidth, int screenHeight, Node node) {
        this.game = game;
        this.node = node;
        img = new WritableImage(screenWidth, screenHeight);
        renderer = new Renderer(new int[screenWidth * screenHeight], screenWidth, screenHeight);
        input = new Input(node);
        timer = new CustomTimer();
        timer.setUpdater(this::update);
        timer.setRenderer(this::render);
        game.initialize(this);
    }

    private void update(float elapsedTime) {
        input.update();
        game.update(this, elapsedTime);
        renderer.process();
    }

    private void render() {
        img.getPixelWriter().setPixels(
                0, 0,
                renderer.getW(), renderer.getH(),
                PixelFormat.getIntArgbInstance(),
                renderer.getP(),
                0, renderer.getW());
        game.render(this);
    }

    // Getters

    public WritableImage getImg() {
        return img;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public CustomTimer getTimer() {
        return timer;
    }

    public Input getInput() {
        return input;
    }

    public Node getNode() {
        return node;
    }

}
