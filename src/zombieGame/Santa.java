package zombieGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Santa extends GameObject {

    private final Handler handler;
    protected SpriteSheet sc;
    protected SpriteSheet ss;
    private GameObject player;
    private final Game game;
    private final BufferedImage[] enemySkin = new BufferedImage[3];
    private final Animation anim;
    private long end = 0;
    private long start = System.currentTimeMillis() / 1000;

    public Santa(Game game, float x, float y, ID id, Handler handler, HUD hud, SpriteSheet sc, SpriteSheet ss) {
        super(game, x, y, id, handler, hud, sc);
        this.handler = handler;
        this.game = game;
        this.sc = sc;
        this.ss = ss;


        enemySkin[0] = sc.grabImage2(0, 0, 57, 158);
        enemySkin[1] = sc.grabImage2(62, 0, 57, 158);
        enemySkin[2] = sc.grabImage2(120, 0, 57, 158);

        anim = new Animation(3, enemySkin[0], enemySkin[1], enemySkin[2]);

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                player = tempObject;
            }
        }

        velX = 0;
        velY = 0;

    }

    public void tick() {

        x += velX;
        y += velY;

        float x0 = x;
        float xf = player.getX();
        float y0 = y;
        float yf = player.getY();

        float distance = (float) Math.sqrt(Math.pow((xf - x0), 2) + Math.pow((yf - y0), 2));

        if (distance <= 200) {
            Game.started = true;
        }

        if (Game.started) {

            if (x < player.getX()) {
                velX = 2;
            }
            if (x > player.getX()) {
                velX = -2;
            }
            if (y < player.getY()) {
                velY = 2;
            }
            if (y > player.getY()) {
                velY = -2;
            }


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
        } else {
            velX = 0;
            velY = 0;
        }


        for (int i = 0; i < handler.bullets.size(); i++) {
            GameObject tempObject = handler.bullets.get(i);
            if (tempObject.getId() == ID.MagicBomb) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    HUD.santaHP -= 50;
                    HUD.healthColor -= 20;
                    handler.removeBullet(tempObject);
                }
            }
            if (tempObject.getId() == ID.Spell) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    HUD.santaHP -= 25;
                    HUD.healthColor -= 10;
                    handler.removeBullet(tempObject);
                }
            }
        }

        if (HUD.santaHP <= 0) {
            handler.removeObject(this);
            Game.started = false;
        }

//        System.out.println(end + " - " + start + " = " + (end - start));
        if (Game.started) {
            if (end - start == 5) {
                handler.addBullet(new SantaSpell(game, this.getX(), this.getY(), ID.SantaSpell, handler, null, ss));
                start = System.currentTimeMillis() / 1000;
            }
            end = System.currentTimeMillis() / 1000;
        }


        anim.runAnimation();

    }

    public void render(Graphics g) {
        if (velX == 0 && velY == 0) {
            g.drawImage(enemySkin[0], (int) x, (int) y, null);
        } else {
            anim.drawAnimation(g, x, y, 0);

        }


//        Graphics2D g2d = (Graphics2D) g;
//        g.setColor(Color.red);
//        g2d.draw(getBounds());
//        g.drawImage(s, (int) x, (int) y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 57, 158);
    }

    public Rectangle getBounds2() {
        return null;
    }

    @Override
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
