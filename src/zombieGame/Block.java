package zombieGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {

    private final BufferedImage blockTexture;

    public Block(Game game, float x, float y, ID id, Handler handler, HUD hud, SpriteSheet ss) {
        super(game, x, y, id, handler, hud, ss);

        blockTexture = ss.grabImage(5, 2, 32, 32);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(blockTexture, (int) x, (int) y, null);
//        g.setColor(Color.BLACK);
//        g.fillRect((int) x, (int) y, 31, 32);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

    public Rectangle getBounds2() {
        return null;
    }
}
