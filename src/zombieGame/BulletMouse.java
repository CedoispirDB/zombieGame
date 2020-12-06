package zombieGame;

import java.awt.*;

import utils.printTools;

public class BulletMouse extends GameObject {

    private Handler handler;
    private float x;
    private float y;
    private int mx;
    private int my;
    private int i = 1;

    public BulletMouse(float x, float y, ID id, Handler handler, int mx, int my, SpriteSheet ss) {
        super(x, y, id, ss);
        this.x = x;
        this.y = y;
        this.mx = mx;
        this.my = my;
        this.handler = handler;
    }


    public void tick() {

        x += velX;
        y += velY;
//        float angle = (float) Math.atan2(my - y - 8, mx - x - 8);
//        int bulletVel = 5;
//
//
//        if (i == 1) {
//            velX = (float) ((bulletVel) * Math.cos(angle));
//            velY = (float) ((bulletVel) * Math.sin(angle));
//        }
//
//
//
//        x += velX;
//        y += velY;
//        i++;
//
//        if (y >= Game.HEIGHT || y <= 0 || x <= 0 || x >= Game.WIDTH) {
//            handler.removeObject(this);
//        }
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect((int) x, (int) y, 16, 16);

    }


    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    public Rectangle getBounds2() {
        return null;
    }
}
