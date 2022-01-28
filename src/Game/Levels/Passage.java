package Game.Levels;

import Game.Manager.GameObject;
import Game.Manager.Handler;
import Game.Manager.ID;
import Game.Map.Node;
import Game.Render.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Passage extends GameObject {

    private final int w, h;
    private GameObject player;
    private LevelManager levelManager;
    private int currentLevel = 1;
    private BufferedImage image;
    private BufferedImage openedDoor;

    public Passage(double posX, double posY, double velX, double velY, int w, int h, Handler handler, ID id, LevelManager levelManager, ImageManager imageManager) {
        super(posX, posY, velX, velY, handler, id);
        this.w = w;
        this.h = h;
        this.levelManager = levelManager;

        image = imageManager.getTexture("cd");
        openedDoor = imageManager.getTexture("od");

    }

    public void tick() {
        if (levelManager.isButtonPressed()) {
            image = openedDoor;
        }

    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

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
