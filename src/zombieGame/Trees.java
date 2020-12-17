package zombieGame;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

public class Trees extends GameObject {

    private Random r = new Random();
    private Color woodColor = new Color(89, 49, 0);

    public Trees(Game game, float x, float y, ID id, Handler handler, HUD hud) {
        super(game, x, y, id, handler, hud);
    }


    private void createTree() {

    }

    private int possiblePositions() {
        float x = r.nextInt(Game.WIDTH);
        float y = r.nextInt(Game.HEIGHT);
        return 0;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(woodColor);
        g.fillRect((int) x, (int) y, 32, 100);

    }


    public Rectangle getBounds() {
        return null;
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
}
