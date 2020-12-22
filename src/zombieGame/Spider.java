package zombieGame;

import java.awt.*;

public class Spider extends GameObject {


    private GameObject player;
    private final Handler handler;

    private double dX;
    private double dY;
    private final double speed = 5;
    private int spiderHP = 250;


    private final Color spiderColor = new Color(128, 0, 128);

    public Spider(Game game, float x, float y, ID id, Handler handler, HUD hud) {
        super(game, x, y, id, handler, hud);

        this.handler = handler;

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                player = tempObject;
            }
        }

    }

    public void tick() {

        dX = player.getX() - x;
        dY = player.getY() - y;

        // Calculate its length to normalize it
        double divider = Math.sqrt(dX * dX + dY * dY);

        // Normalize it
        dX = dX / divider;
        dY = dY / divider;

        // Do a scalar multiplication with our speed
        dX *= speed;
        dY *= speed;

        x += dX;
        y += dY;

        for (int i = 0; i < handler.bullets.size(); i++) {
            GameObject tempObject = handler.bullets.get(i);
            if (tempObject.getId() == ID.MagicBomb) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    spiderHP -= 50;
                    handler.removeBullet(tempObject);
                }
            }
            if (tempObject.getId() == ID.Spell) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    spiderHP -= 25;
                    handler.removeBullet(tempObject);
                }
            }
        }

        if (spiderHP <= 0) {
            handler.removeObject(this);
        }

    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.red);
        g2d.draw(getBounds());

        g.setColor(spiderColor);
        g.fillRect((int) x, (int) y, 48, 48);

    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 48, 48);
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
