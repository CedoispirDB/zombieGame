package Render;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class CreateImages {

    public CreateImages() {

    }

    public LinkedList<BufferedImage> createFrames(String path, int cols, int rows) {
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage spriteSheet = loader.loadImage("");
        LinkedList<BufferedImage> frames = new LinkedList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames.add(spriteSheet.getSubimage(i * 32, j * 32, 32, 32));
            }
        }

        return frames;
    }


}
