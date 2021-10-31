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

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Player) {
                this.player = temp;
            }
        }
    }

    public void tick() {

        if (player != null) {
            if (player.getBounds().intersects(this.getBounds())) {
                // Player got to the passage
//                levelManager.loadLevel(currentLevel + 1);
                System.out.println("Changing level");
            }
        }

    }

    public void render(Graphics g) {
//        g.setColor(Color.yellow.darker());
//        g.fillRect((int) posX, (int) posY, w, h);
        g.drawImage(image, (int) posX, (int) posY, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)posX, (int) posY, w, h);
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
