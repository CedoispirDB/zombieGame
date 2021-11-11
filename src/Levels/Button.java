package Levels;

import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Map.Node;
import Render.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Button extends GameObject {

    private final int w, h;
    private BufferedImage image;


    public Button(double posX, double posY, double velX, double velY, int w, int h, Handler handler, ID id) {
        super(posX, posY, velX, velY, handler, id);
        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("/texture.png");
        image = image.getSubimage(64,0,32,32);
        this.w = w;
        this.h = h;
    }

    public void tick() {

    }

    public void render(Graphics g) {
//        g.setColor(Color.red.darker());
//        g.fillRect((int) posX, (int) posY, w, h);
        g.drawImage(image, (int) posX, (int) posY, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)posX, (int)posY, image.getWidth(), image.getHeight());
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
