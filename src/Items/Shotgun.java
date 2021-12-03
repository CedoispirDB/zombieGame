package Items;

import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Manager.ItemObject;
import Player.Inventory;
import Render.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Shotgun extends ItemObject {

    private final BufferedImage image;
    private GameObject player;
    private Inventory inventory;
    private Handler handler;

    public Shotgun(double posX, double posY, double iconX, double iconY, Inventory inventory, ID id) {
        super(posX, posY, iconX, iconY, inventory, id);

        this.inventory = inventory;

        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("/s.png");

    }

    public void tick() {

    }


    public void render(Graphics g) {
        g.drawImage(image, (int) posX, (int) posY, null);

    }

    public Rectangle getBounds() {
        return new Rectangle((int) posX, (int) posY, image.getWidth(), image.getHeight());
    }

    public void drawIcon(Graphics g) {
        g.drawImage(image, (int)iconX, (int)iconY, null);

    }

    public void increase() {

    }
}
