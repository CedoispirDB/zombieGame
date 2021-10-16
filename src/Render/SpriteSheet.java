package Render;

import java.awt.image.BufferedImage;

public class SpriteSheet {


    private final BufferedImage image;

    public SpriteSheet(BufferedImage image) {
        this.image = image;
    }

    // 64 x 64 if needed
    public BufferedImage grabImage(int col, int row, int width, int height) {
        return image.getSubimage((col * 32) - 32, (row * 32) - 32, width, height);
    }

    public BufferedImage grabImage2(int x, int y, int width, int height) {
        return image.getSubimage(x, y, width,  height);
    }

}
