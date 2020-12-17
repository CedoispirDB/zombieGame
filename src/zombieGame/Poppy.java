package zombieGame;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Poppy extends GameObject {

    private final Handler handler;
    private GameObject player;
    private final Map map;
    private boolean stop = false;
    public static boolean sit = false;
    private final double speed = 4;
    private double dX;
    private double dY;

    private final BufferedImage stoppedPoppyLeft;
    private final BufferedImage stoppedPoppyRight;

    private final Animation animLeft;
    private final Animation animRight;

    public Poppy(Game game, float x, float y, ID id, Handler handler, HUD hud, Map map) {
        super(game, x, y, id, handler, hud);
        this.handler = handler;
        this.map = map;

        BufferedImage[] poppySkin = new BufferedImage[6];

        poppySkin[0] = map.poppySkin.grabImage2(36, 0, 37, 39);
        poppySkin[1] = map.poppySkin.grabImage2(73, 0, 37, 39);
        poppySkin[2] = map.poppySkin.grabImage2(110, 0, 37, 39);

        poppySkin[3] = map.poppySkin.grabImage2(36, 39, 37, 39);
        poppySkin[4] = map.poppySkin.grabImage2(73, 39, 37, 39);
        poppySkin[5] = map.poppySkin.grabImage2(110, 39, 37, 39);

        animLeft = new Animation(3, poppySkin[0], poppySkin[1], poppySkin[2]);
        animRight = new Animation(3, poppySkin[3], poppySkin[4], poppySkin[5]);


        stoppedPoppyLeft = map.poppySkin.grabImage2(0, 0, 36, 39);
        stoppedPoppyRight = map.poppySkin.grabImage2(0, 39, 36, 39);

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                player = tempObject;
            }
        }

    }

    public void tick() {
        if (!sit) {
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

            //Going up dy -, going down dy +
            //Going right dx +, going left dx -


            if (!stop) {
                if (dX > 0) {
                    animRight.runAnimation();
                } else {
                    animLeft.runAnimation();
                }
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
            if (dX > 0) {
                g.drawImage(stoppedPoppyRight, (int) x, (int) y, null);
            } else {
                g.drawImage(stoppedPoppyLeft, (int) x, (int) y, null);
            }

        } else {
            if (dX > 0) {
                animRight.drawAnimation(g, x, y, 0);
            } else {
                animLeft.drawAnimation(g, x, y, 0);
            }
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
