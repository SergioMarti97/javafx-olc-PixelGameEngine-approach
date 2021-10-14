package olcPGEApproach.gfx.images.decals;

import olcPGEApproach.gfx.images.Image;

public class Renderable {

    private Decal decal;

    private Image image;

    public Renderable() {

    }

    public void create(int width, int height, boolean filter, boolean clap) {
        image = new Image(width, height);
        decal = new Decal(image, filter, clap);
    }

    public void create(int width, int height) {
        create(width, height, false, true);
    }

    public void load(String file, boolean filter, boolean clamp) {
        image = new Image(file);
        decal = new Decal(image, filter, clamp);
    }

    public void load(String file) {
        load(file, false, true);
    }

}
