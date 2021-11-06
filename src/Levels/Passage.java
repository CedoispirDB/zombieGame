package Levels;

import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Map.Node;
import Render.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Passage extends GameObject {

    private final int w, h;
    private GameObject player;
    private LevelManager levelManager;
    private int currentLevel = 1;
    private BufferedImage image;

    public Passage(double posX, double posY, double velX, double velY, int w, int h,  Handler handler, ID id, LevelManager levelManager) {
        super(posX, posY, velX, velY, handler, id);
        this.w = w;
        this.h = h;
        this.levelManager = levelManager;

        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("/a.png");
        image = image.getSubimage(96,0,32,64);
    }

    public void tick() {


    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.red);
        g2d.draw(getBounds());
        g.drawImage(image, (int) posX, (int) posY, null);

    }

    public Rectangle getBounds() {
        return new Rectangle((int)posX, (int) posY, image.getWidth(), image.getHeight());
    }


    public Node getNode() {
        return null;
    }

    public Rectangle getBoundsX() {
        return null;
    }

    public Rectangle getBoundsY() {
        return null;
    }
}
