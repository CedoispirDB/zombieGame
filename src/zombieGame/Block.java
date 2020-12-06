package zombieGame;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Block extends GameObject {

    private final Handler handler;

    private BufferedImage blockTexture;

    public Block(Game game, float x, float y, ID id, Handler handler, HUD hud, SpriteSheet ss) {
        super(game, x, y, id, handler, hud, ss);
        this.handler = handler;

        blockTexture = ss.grabImage(5, 2, 32, 32);
    }

    public void tick() {
//        for (int i = 0; i < handler.bullets.size(); i++) {
//            GameObject tempObject = handler.bullets.get(i);
//            if (tempObject.getId() == ID.Bullet) {
//                if (getBounds().intersects(tempObject.getBounds())) {
//                    handler.removeBullet(tempObject);
//                }
//            }
//        }
    }

    public void render(Graphics g) {
        g.drawImage(blockTexture, (int) x, (int) y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

    public Rectangle getBounds2() {
        return null;
    }
}
