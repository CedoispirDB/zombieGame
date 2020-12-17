package zombieGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Poppy extends GameObject {

    private final Handler handler;
    protected SpriteSheet ss;
    private GameObject player;
    private final Map map;
    private boolean stop = false;
    public static boolean sit = false;
    private double speed = 4;

    protected BufferedImage stoppedPoppy;

    Animation anim;

    public Poppy(Game game, float x, float y, ID id, Handler handler, HUD hud, SpriteSheet ss, Map map) {
        super(game, x, y, id, handler, hud, ss);
        this.handler = handler;
        this.ss = ss;
        this.map = map;

        BufferedImage[] enemySkin = new BufferedImage[3];

        enemySkin[0] = map.spriteSheet.grabImage(4, 1, 32, 32);
        enemySkin[1] = map.spriteSheet.grabImage(5, 1, 32, 32);
        enemySkin[2] = map.spriteSheet.grabImage(6, 1, 32, 32);

        anim = new Animation(3, enemySkin[0], enemySkin[1], enemySkin[2]);

        stoppedPoppy = map.spriteSheet.grabImage(4, 1, 32, 32);

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                player = tempObject;
            }
        }

    }

    public void tick() {
        if (!sit) {
            double dX = player.getX() - x;
            double dY = player.getY() - y;

            // Calculate its length to normalize it
            double divider = Math.sqrt(dX * dX + dY * dY);

            // Normalize it
            dX = dX / divider;
            dY = dY / divider;

            // Do a scalar multiplication with our speed
            dX *= speed;
            dY *= speed;


            if (!stop) {
                anim.runAnimation();
            }

            if (getBounds2().intersects(player.getBounds())) {
                velX = 0;
                velY = 0;
                stop = true;
            } else {
                stop = false;
            }

            if (!stop) {
                x += dX;
                y += dY;
            }
        } else {
            velX = 0;
            velY = 0;
            stop = true;
        }


    }

    public void render(Graphics g) {
        if (stop) {
            g.drawImage(stoppedPoppy, (int) x, (int) y, null);

        } else {
            anim.drawAnimation(g, x, y, 0);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

    public Rectangle getBounds2() {
        return new Rectangle((int) x - 4, (int) y - 4, 40, 40);
    }

    public Rectangle getBoundX() {
        return null;
    }

    public Rectangle getBoundY() {
        return null;
    }

    public Rectangle getBoundsBig() {
        return new Rectangle((int) x - 16, (int) y - 16, 32, 32);

    }
}
