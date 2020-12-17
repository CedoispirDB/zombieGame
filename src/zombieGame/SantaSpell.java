package zombieGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SantaSpell extends GameObject {

    private Handler handler;
    private GameObject santa;
    private GameObject player;
    private BufferedImage sss;

    public SantaSpell(Game game, float x, float y, ID id, Handler handler, HUD hud) {
        super(game, x, y, id, handler, hud);
        this.handler = handler;

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Santa) {
                santa = tempObject;
            }
        }
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                player = tempObject;
            }
        }

//        sss = ss.grabImage2(0, 50, 16, 14);


    }


    public void tick() {

        float x0 = x;
        float xf = santa.getX();
        float y0 = y;
        float yf = santa.getY();

        float distance = (float) Math.sqrt(Math.pow((xf - x0), 2) + Math.pow((yf - y0), 2));

        if (distance >= 200) {
            velX = 10;
            velY = 10;

        } else {

            if (x < player.getX()) {
                velX = 10;
            }
            if (x > player.getX()) {
                velX = -10;
            }
            if (y < player.getY()) {
                velY = 10;
            }
            if (y > player.getY()) {
                velY = -10;
            }
        }


        x += velX;
        y += velY;

        if (getBounds().intersects(player.getBounds())) {
            handler.removeBullet(this);
            HUD.HEALTH--;
        }
    }

    public void render(Graphics g) {
//        g.drawImage(sss, (int) x, (int) y, null);
        g.setColor(Color.BLACK);
        g.fillRect((int) x + 57, (int) y + 50, 32, 32);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
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
}
