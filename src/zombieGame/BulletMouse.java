package zombieGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

public class BulletMouse extends GameObject {

    private final Handler handler;
    private float x;
    private float y;

    public BulletMouse(float x, float y, ID id, Handler handler, int mx, int my, SpriteSheet ss) {
        super(x, y, id, ss);
        this.x = x;
        this.y = y;

        this.handler = handler;

        velX = (mx - x) / 10;
        velY = (my - y) / 10;
    }


    public void tick() {

        x += velX;
        y += velY;

        for (int j = 0; j < handler.object.size(); j++) {
            GameObject tempObject = handler.object.get(j);
            if (tempObject.getId() == ID.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeBullet(this);
                }
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect((int) x, (int) y, 8, 8);

    }


    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 8, 8);
    }

    public Rectangle getBounds2() {
        return null;
    }
}
