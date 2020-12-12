package zombieGame;

import java.awt.*;
import java.util.LinkedList;

public class Obstacles extends GameObject {

    private final Color obsCol = new Color(64, 64, 64);
    private GameObject obstacle;
    private GameObject player;
    private Handler handler;
    private int w;
    private int h;
    private Game game;


    public Obstacles(Game game, float x, float y, ID id, Handler handler, HUD hud, SpriteSheet ss) {
        super(game, x, y, id, handler, hud, ss);

        this.handler = handler;
        this.game = game;
        obstacle = this;

        h = 32;
        w = 32;


    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(obsCol.darker());
        g.fillRect((int) x , (int) y, w, h);

    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y , w, h);
    }

    public Rectangle getBounds2() {
        return null;
    }

    public Rectangle getBoundX() {
        return null;
    }
    public Rectangle getBoundY() {
        return null;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }
}
