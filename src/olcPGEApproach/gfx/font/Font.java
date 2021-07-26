package olcPGEApproach.gfx.font;

import olcPGEApproach.gfx.images.Image;

/**
 * This class is the font for the characters which will be shown by the screen
 *
 * This is copied from the 2D Java Game Engine from the channel Majoolwip
 * GitHub: https://github.com/Majoolwip
 * Youtube channel: https://www.youtube.com/channel/UCYdJWlQWeuhDZicBbxM0-mg
 * Youtube video where he explains the Renderer class: "2D Java Game Engine #9 : Font Rendering"
 * https://www.youtube.com/watch?v=bNq1UxL2cmE&list=PL7dwpoQd3a8j6C9p5LqHzYFSkii6iWPZF&index=9
 *
 * @class: Font
 * @autor: Sergio Martí Torregrosa
 * @date: 2020-07-06
 */
public class Font {

    /**
     * The standard font. It is the Consolas font 24 size
     */
    //public static final Font STANDARD = new Font("/consolas24.png");

    /**
     * The image which contains the sprites for the letters and symbols
     */
    private Image fontImage;

    /**
     * the offsets in the image for each symbol in the image
     */
    private int[] offsets;

    /**
     * The widths of each symbol
     */
    private int[] widths;

    /**
     * The constructor
     *
     * @param path the string with the path where is the font
     */
    public Font(String path) {
        fontImage = new Image(path);
        offsets = new int[256];
        widths = new int[256];
        int unicode = 0;
        for ( int i = 0; i < fontImage.getW(); i++ ) {
            if ( fontImage.getP()[i] == 0xff0000ff ) {
                offsets[unicode] = i;
            }
            if ( fontImage.getP()[i] == 0xffffff00 ) {
                widths[unicode] = i - offsets[unicode];
                unicode++;
            }
        }
    }

    public Image getCharacterImage(int unicode) {
        int[] characterImage = new int[widths[unicode] * fontImage.getH()];
        for ( int y = 0; y < fontImage.getH(); y++ ) {
            for ( int x = 0; x < widths[unicode]; x++ ) {
                characterImage[x + y * widths[unicode]] = fontImage.getP()[(x + offsets[unicode]) + y * fontImage.getW()];
            }
        }
        return new Image(characterImage, widths[unicode], fontImage.getH());
    }

    public Image getFontImage() {
        return fontImage;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public int[] getWidths() {
        return widths;
    }

    public void setFontImage(Image fontImage) {
        this.fontImage = fontImage;
    }

    public void setOffsets(int[] offsets) {
        this.offsets = offsets;
    }

    public void setWidths(int[] widths) {
        this.widths = widths;
    }

}
