package olcPGEApproach.gfx.images;

/**
 * The request which has the image
 *
 * @class: ImageRequest
 * @autor: Sergio Martí Torregrosa
 * @date: 2020-07-06
 */
public class ImageRequest {

    /**
     * The image
     */
    private Image image;

    /**
     * The depth in Z axis
     */
    private int zDepth;

    /**
     * Offset X
     */
    private int offX;

    /**
     * Offset Y
     */
    private int offY;

    /**
     * Constructor
     *
     * @param image the image
     * @param zDepth the "height" on axis Z
     * @param offX Offset X
     * @param offY Offset Y
     */
    public ImageRequest(Image image, int zDepth, int offX, int offY) {
        this.image = image;
        this.zDepth = zDepth;
        this.offX = offX;
        this.offY = offY;
    }

    public Image getImage() {
        return image;
    }

    public int getzDepth() {
        return zDepth;
    }

    public int getOffX() {
        return offX;
    }

    public int getOffY() {
        return offY;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setzDepth(int zDepth) {
        this.zDepth = zDepth;
    }

    public void setOffX(int offX) {
        this.offX = offX;
    }

    public void setOffY(int offY) {
        this.offY = offY;
    }

}
