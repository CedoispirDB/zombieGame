package zombieGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {

    private final BufferedImage blockTexture;
    private final Map map;

    public Block(Game game, float x, float y, ID id, Handler handler, HUD hud, Map map) {
        super(game, x, y, id, handler, hud);

        this.map = map;

        blockTexture = map.spriteSheet.grabImage(5, 2, 32, 32);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(blockTexture, (int) x, (int) y, null);
//        g.setColor(Color.BLACK);
//        g.fillRect((int) x, (int) y, 31, 32);
        if (Game.showBounds) {
            Graphics2D g2d = (Graphics2D) g;
            g.setColor(Color.red);
            g2d.draw(getBoundX());
            g.setColor(Color.blue);
            g2d.draw(getBoundY());
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

    public Rectangle getBounds2() {
        return null;
    }

    public Rectangle getBoundX() {
        return new Rectangle((int) x - 10, (int) y - 10, 55, 55);
    }

    public Rectangle getBoundY() {
        return new Rectangle((int) x - 10, (int) y - 15, 55, 65);
    }

}
