package Items;

import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Map.Node;

import java.awt.*;

public class Coins extends GameObject {

    public Coins(double posX, double posY, double velX, double velY, Handler handler, ID id) {
        super(posX, posY, velX, velY, handler, id);
    }

    public void tick() {

    }

    public void render(Graphics g) {

    }

    public Rectangle getBounds() {
        return null;
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
