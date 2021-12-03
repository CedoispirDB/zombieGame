package Items;

import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Map.Node;
import Render.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Coin extends GameObject {

    private final BufferedImage image;

    public Coin(double posX, double posY, double velX, double velY, Handler handler, ID id) {
        super(posX, posY, velX, velY, handler, id);

        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("/coin.png");
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(image, (int) posX, (int) posY, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) posX, (int) posY, 32,32);
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
