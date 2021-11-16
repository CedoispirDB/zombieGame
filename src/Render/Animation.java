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
    private int currentVel;
    private int count;

    public Animation() {
        currentVel = 10;
        count = 0;
    }

    public void init(int speed) {
        this.speed = speed;

    }

    public void setFrames(LinkedList<BufferedImage> frames, int vel) {
        if (vel != currentVel) {
            this.frames = frames;
            i = 0;
            currentImg = frames.get(i);
            currentVel = vel;
        }
    }

    public void setFrames(LinkedList<BufferedImage> frames) {
        this.frames = frames;
        currentImg = frames.get(i);
        i = -1;

    }


    public void runAnimation() {

        index++;
        if (index > speed) {
            index = 0;
            if (i < frames.size() - 1) {
                currentImg = frames.get(i + 1);
                i++;
            } else {
                i = -1;
            }
        }

    }

    public boolean runAnimation(int limit) {
        if (count == limit) {
            return true;
        }

        index++;
        if (index > speed) {
            index = 0;
            if (i < frames.size() - 1) {
                currentImg = frames.get(i + 1);
                i++;
            } else {
                i = 0;
                count ++;
            }
        }
        return false;
    }

    public void renderAnimation(Graphics g, int x, int y) {
        g.drawImage(currentImg, x, y, null);
    }

}
