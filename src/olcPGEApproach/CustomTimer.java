package olcPGEApproach;

import javafx.animation.AnimationTimer;
import javafx.beans.property.ReadOnlyStringWrapper;

/**
 * This class is the timer, it manages the game
 * loop
 *
 * Must set two methods, is possible do it as
 * lambdas: update and render
 */
public class CustomTimer extends AnimationTimer {

    interface Update {
        void update(float elapsedTime);
    }

    interface Render {
        void render();
    }

    protected ReadOnlyStringWrapper textFps = new ReadOnlyStringWrapper(this,
            "fpsText", "Frame count: 0 Average frame interval: N/A");

    protected Update updater;

    private Render renderer;

    protected long firstTime = 0;

    protected long lastTime = 0;

    protected long accumulatedTime = 0;

    protected int frames = 0;

    protected boolean showFPSOnConsole = true;

    @Override
    public void handle(long now) {
        if ( lastTime > 0 ) {
            long elapsedTime = now - lastTime;
            accumulatedTime += elapsedTime;
            updater.update(elapsedTime / 1000000000.0f);
        } else {
            firstTime = now;
        }
        lastTime = now;

        if ( accumulatedTime >= 1000000000L ) {
            accumulatedTime -= 1000000000L;
            textFps.set(String.format("FPS: %d", frames));
            if (showFPSOnConsole) {
                System.out.println("FPS: " + frames);
            }
            frames = 0;
        }
        renderer.render();
        frames++;
    }

    public ReadOnlyStringWrapper getTextFps() {
        return textFps;
    }

    public boolean isShowFPSOnConsole() {
        return showFPSOnConsole;
    }

    public void setUpdater(Update updater) {
        this.updater = updater;
    }

    public void setRenderer(Render renderer) {
        this.renderer = renderer;
    }

    public void setShowFPSOnConsole(boolean showFPSOnConsole) {
        this.showFPSOnConsole = showFPSOnConsole;
    }

}
