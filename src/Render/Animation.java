package Render;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.BufferOverflowException;
import java.util.LinkedList;

public class Animation {

    private LinkedList<BufferedImage> frames;
    private BufferedImage currentImg;
    private int index;
    private int i;
    private int speed;

    public Animation(LinkedList<BufferedImage> frames, int speed) {
        this.frames = frames;
        this.speed = speed;
        i = 0;
        currentImg = frames.get(i);
    }

    public void runAnimation() {
        index++;
        if (index > speed) {
            index = 0;
            if (i > frames.size() - 1) {
                currentImg = frames.get(i + 1);
            } else {
                i = 0;
            }
        }

    }

    public void renderAnimation(Graphics g, int x, int y) {
        g.drawImage(currentImg, x, y, null);
    }

}
