package zombieGame;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Enemy extends GameObject {

    private final Handler handler;
    int hp = 100;
    protected SpriteSheet ss;
    private GameObject player;

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

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player){
                player = tempObject;
            }
        }

    }

    public void tick() {


        if (x < player.getX()) {
            this.setVelX(2);
        }
        if (x > player.getX()) {
            this.setVelX(-2);
        }
        if (y < player.getY()) {
            this.setVelY(2);
        }
        if (y> player.getY()) {
            this.setVelY(-2);
        }

        x += velX;
        y += velY;

        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBoundsBig().intersects(tempObject.getBounds())) {
                    x += (velX * 5) * -1;
                    y += (velY * 5) * -1;
                    velX *= -1;
                    velY *= -1;
                }
            }

        }


        for (int i = 0; i < handler.bullets.size(); i++) {
            GameObject tempObject = handler.bullets.get(i);
            if (tempObject.getId() == ID.MagicBomb) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    hp -= 50;
                    handler.removeBullet(tempObject);
                }
            }
            if (tempObject.getId() == ID.Spell) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    hp -= 25;
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
