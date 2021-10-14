package olcPGEApproach.gfx.images.decals;

import olcPGEApproach.gfx.images.Image;
import olcPGEApproach.vectors.points2d.Vec2df;

public class Decal {

    private int id = -1;

    private Image image;

    private Vec2df uvScale = new Vec2df(1.0f, 1.0f);

    public Decal(Image image, boolean filter, boolean clamp) {
        id = -1;
        if ( image == null ) {
            return;
        }
        this.image = image;
        //id = renderer.createTexture(image.getW(), image.getH(), filter, clamp);
    }

    public Decal(Image image) {
        this(image, false, true);
    }

    public Decal(int existingTextureResource, Image image) {
        if ( image == null ) {
            return;
        }
        id = existingTextureResource;
    }

    public void update() {
        if ( image == null ) {
            return;
        }
        uvScale.setX(1.0f / (float) image.getW());
        uvScale.setY(1.0f / (float) image.getH());
        //renderer.applyTexture(id);
        //renderer.updateTexture(id, image);
    }

    public void updateSprite() {
        if ( image == null ) {
            return;
        }
        //renderer.applyTexture(id);
        //renderer.updateTexture(id, image);
    }

}
