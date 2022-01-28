package Game.Levels;

import Game.Manager.GameObject;
import Game.Manager.Handler;
import Game.Manager.ID;
import Game.Map.Node;
import Game.Render.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Button extends GameObject {

    private final int w, h;
    private BufferedImage image;


    public Button(double posX, double posY, double velX, double velY, int w, int h, Handler handler, ID id, ImageManager imageManager) {
        super(posX, posY, velX, velY, handler, id);

        image = imageManager.getTexture("b");
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
