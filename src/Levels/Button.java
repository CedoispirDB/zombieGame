package Levels;

import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Map.Node;

import java.awt.*;

public class Button extends GameObject {

    private final int w, h;


    public Button(double posX, double posY, double velX, double velY, int w, int h, Handler handler, ID id) {
        super(posX, posY, velX, velY, handler, id);

        this.w = w;
        this.h = h;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(Color.red.darker());
        g.fillRect((int) posX, (int) posY, w, h);
    }

    public Rectangle getBounds() {
        return null;
    }

    @Override
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
