package zombieGame;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class Minion extends GameObject {

    private final Handler handler;
    int hp = 100;
    protected SpriteSheet ss;
    private GameObject player;
    private GameObject obstacle;
    private final BufferedImage s;
    private boolean collide = false;
    private Tuples tuples;
    private int i = 0;
    private double speed = 2;
    private float initialX;
    private float initialY;

    private Animation anim;

    public Minion(Game game, float x, float y, ID id, Handler handler, HUD hud, SpriteSheet ss) {
        super(game, x, y, id, handler, hud, ss);
        this.handler = handler;
        this.ss = ss;

        tuples = new Tuples();
        tuples.addTuple(520, 520);
        tuples.addTuple(540, 540);
        tuples.addTuple(565, 565);
//        tuples.print();


        BufferedImage[] enemySkin = new BufferedImage[3];

        enemySkin[0] = ss.grabImage(4, 1, 32, 32);
        enemySkin[1] = ss.grabImage(5, 1, 32, 32);
        enemySkin[2] = ss.grabImage(6, 1, 32, 32);
        s = ss.grabImage(4, 1, 32, 32);
//        anim = new Animation(3, enemySkin[0], enemySkin[1], enemySkin[2]);

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                player = tempObject;
            }
        }
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Obstacles) {
                obstacle = tempObject;
            }
        }

        initialX = x;
        initialY = y;
    }

    public void tick() {

        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBoundsBig().intersects(tempObject.getBounds())) {
                    collide = true;
                }
            }

        }

        // Calculate our P_g - P_e
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
//
//        x += dX;
//        y += dY;


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
        if (i < 3) {
            if (x == initialX && y == initialY) {
                System.out.println("i: " + i);
                path(i);
                i++;
            } else {
                if ((x > tuples.getX(i - 1) && y > tuples.getY(i - 1))) {
                    System.out.println("i: " + i);
                    path(i);
                    i++;
                }
            }
            System.out.println(x);
            System.out.println(tuples.getX(i - 1));
        }
        x += velX;
        y += velY;

//        anim.runAnimation();

    }

    public void path(int i) {
        float xt = tuples.getX(i) - this.getX();
        float yt = tuples.getY(i) - this.getY();
        float distance = (float) Math.sqrt(Math.pow(xt, 2) + Math.pow(yt, 2));
        velX = (xt / distance) * 2;
        velY = (yt / distance) * 2;
    }

    public void findPath() {
        float xp;
        float yp;
        float xt = 0;
        float yt = 0;
        float xb = 0;
        float yb = 0;
        float xa;
        float ya;
        float angleP;
        float angleT;
        float angleB;
        float adjustedAngle;

        // enemy - player angle
        xp = player.getX() - this.getX();
        yp = player.getY() - this.getY();
        if (player.getX() > this.getX()) {
            angleP = (float) Math.atan2(xp, yp);
        } else {
            angleP = (float) Math.atan2(-1 * (xp), -1 * (yp));
        }

        // Calculate top and bottom coordinates
        if (this.getX() < obstacle.getX() && this.getY() > obstacle.getY()) { // Bottom left
            xt = (obstacle.getX() - 32) - this.getX();
            yt = this.getY() - (obstacle.getY() - 48);
            xb = (obstacle.getX() + 32) - this.getX();
            yb = this.getY() - (obstacle.getY() + 52);
        } else if (this.getX() < obstacle.getX() && this.getY() < obstacle.getY()) { // Top Left
            xt = (obstacle.getX() + 64) - this.getX();
            yt = (obstacle.getY() - 48) - this.getY();
            xb = (obstacle.getX()) - this.getX();
            yb = (obstacle.getY() + 52) - this.getY();
        } else if (this.getX() > obstacle.getX() && this.getY() > obstacle.getY()) { // Bottom right
            xt = this.getX() - (obstacle.getX() + 64);
            yt = this.getY() - (obstacle.getY() - 48);
            xb = this.getX() - (obstacle.getX());
            yb = this.getY() - (obstacle.getY() + 52);
        } else if (this.getX() > obstacle.getX() && this.getY() < obstacle.getY()) { // Top right
            xt = this.getX() - (obstacle.getX() - 32);
            yt = (obstacle.getY() - 48) - this.getY();
            xb = this.getX() - (obstacle.getX() + 64);
            yb = (obstacle.getY() + 32 + 48
            ) - this.getY();
        }


        // enemy - top angle
        if (this.getX() < obstacle.getX()) {
            angleT = (float) Math.atan2(xt, yt);
        } else {
            angleT = (float) Math.atan2(-1 * (xt), -1 * (yt));
        }

        // enemy - bottom angle
        if (this.getX() < obstacle.getX()) {
            angleB = (float) Math.atan2(xb, yb);
        } else {
            angleB = (float) Math.atan2(-1 * (xb), -1 * (yb));

        }

        if (angleP > angleB && angleP < angleT) {
            // Calculate adjusted angle
            xa = player.getX() - xt;
            ya = player.getY() - yb;
            velX = 0;
            velY = 0;
        } else {
            // Velocity logic without obstacle
            float xTotal = player.getX() - this.getX();
            float yTotal = player.getY() - this.getY();
            float distance = (float) Math.sqrt(Math.pow(xTotal, 2) + Math.pow(yTotal, 2));
            velX = (xTotal / distance) * 2;
            velY = (yTotal / distance) * 2;
        }

        System.out.println("player angle: " + angleP);
        System.out.println("Top angle: " + angleT);
        System.out.println("Bottom angle: " + angleB);

//        player angle: 2.3758893
//        Top angle: 3.1352887
//        Bottom angle: 1.0049853

//        player angle: 0.8722065
//        Top angle: 2.8016226
//        Bottom angle: -2.9759424


    }


    public void render(Graphics g) {
//        anim.drawAnimation(g, x, y, 0);
        if (Game.showBounds) {
            Graphics2D g2d = (Graphics2D) g;
            g.setColor(Color.red);
            g2d.draw(getBounds());
        }
        g.drawImage(s, (int) x, (int) y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

    public Rectangle getBounds2() {
        return null;
    }

    public Rectangle getBoundX() {
        return null;
    }

    @Override
    public Rectangle getBoundY() {
        return null;
    }

    public Rectangle getBoundsBig() {
        return new Rectangle((int) x - 16, (int) y - 16, 64, 64);

    }
}
