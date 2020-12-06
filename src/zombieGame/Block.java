package zombieGame;

import java.awt.*;

public class Block extends GameObject {

    private final Handler handler;

    public Block(Game game, float x, float y, ID id, Handler handler, HUD hud, SpriteSheet ss) {
        super(game, x, y, id, handler, hud, ss);
        this.handler = handler;
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
        g.setColor(Color.BLACK);
        g.fillRect((int) x, (int) y, 32, 32);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

    public Rectangle getBounds2() {
        return null;
    }
}
