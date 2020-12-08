package zombieGame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject {

    private final Handler handler;
    Random r = new Random();
    int choose = 0;
    int hp = 100;
    protected SpriteSheet ss;

    Animation anim;

    public Enemy(Game game, float x, float y, ID id, Handler handler, HUD hud, SpriteSheet ss) {
        super(game, x, y, id, handler, hud, ss);
        this.handler = handler;
        this.ss = ss;

        BufferedImage[] enemySkin = new BufferedImage[3];

        enemySkin[0] = ss.grabImage(4, 1, 32, 32);
        enemySkin[1] = ss.grabImage(5, 1, 32, 32);
        enemySkin[2] = ss.grabImage(6, 1, 32, 32);

        anim = new Animation(3, enemySkin[0], enemySkin[1], enemySkin[2]);

    }

    public void tick() {
        x += velX;
        y += velY;

        choose = r.nextInt(10);

        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBoundsBig().intersects(tempObject.getBounds())) {
                    x += (velX * 5) * -1;
                    y += (velY * 5) * -1;
                    velX *= -1;
                    velY *= -1;
                } else if (choose == 0) {
                    velX = (r.nextInt(4 - -4) + -4);
                    velY = (r.nextInt(4 - -4) + -4);
                }
            }


        }

        for (int i = 0; i < handler.bullets.size(); i++) {
            GameObject tempObject = handler.bullets.get(i);
            if (tempObject.getId() == ID.Bullet) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    hp -= 50;
                    handler.removeBullet(tempObject);
                }
            }
        }

        if (hp <= 0) {
            handler.removeObject(this);
        }

        anim.runAnimation();

    }

    public void render(Graphics g) {
        anim.drawAnimation(g, x, y, 0);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

    public Rectangle getBounds2() {
        return null;
    }

    public Rectangle getBoundsBig() {
        return new Rectangle((int) x - 16, (int) y - 16, 64, 64);

    }
}
