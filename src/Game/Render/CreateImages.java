package Game.Render;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class CreateImages {


    public CreateImages() {
    }

    public LinkedList<BufferedImage> createFrames(BufferedImage spriteSheet, int jump, int cols, int rows) {
        LinkedList<BufferedImage> frames = new LinkedList<>();

        for (int i = jump; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames.add(spriteSheet.getSubimage(j * 32, i * 32, 32, 32));
            }
        }

        return frames;
    }

    public LinkedList<BufferedImage> createFrames(BufferedImage spriteSheet, int jump, int cols, int rows, String name) {
        LinkedList<BufferedImage> frames = new LinkedList<>();

        for (int i = jump; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.println("for: " + name + "x: " + i * 32 + " y: " + j *32);
                frames.add(spriteSheet.getSubimage(j * 32, i * 32, 32, 32));
            }
        }

        return frames;
    }
}
