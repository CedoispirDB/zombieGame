package zombieGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Poppy extends GameObject {

    private final Handler handler;
    protected SpriteSheet ss;
    private GameObject player;

    Animation anim;

    public Poppy(Game game, float x, float y, ID id, Handler handler, HUD hud, SpriteSheet ss) {
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
            if (tempObject.getId() == ID.Player) {
                player = tempObject;
            }
        }

    }

    public void tick() {
        x += velX;
        y += velY;

        float diffX = x - player.getX();
        float diffY = y - player.getY();
        float difference = (float) Math.sqrt((x - player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()));


        velX = (float) ((-1.0 / difference) * diffX);
        velY = (float) ((-1.0 / difference) * diffY);


        anim.runAnimation();

        if (getBounds2().intersects(player.getBounds())) {
            velX = 0;
            velY = 0;
        }

    }

    public void render(Graphics g) {

        anim.drawAnimation(g, x, y, 0);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

    public Rectangle getBounds2() {
        return new Rectangle((int) x - 4, (int) y - 4, 40, 40);
    }

    public Rectangle getBoundsBig() {
        return new Rectangle((int) x - 16, (int) y - 16, 32, 32);

    }
}
