package zombieGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MagicBomb extends GameObject {

    private float x;
    private float y;
    private final int dir;
    private Map map;

    private BufferedImage bombTexture;

    public MagicBomb(float x, float y, ID id, int dir, Map map) {
        super(x, y, id);
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.map = map;

        bombTexture = map.spriteSheet.grabImage2(64, 49, 16, 15);


    }

    public void tick() {
    }

    public void render(Graphics g) {
        g.drawImage(bombTexture, (int) x, (int) y, null);


    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    public Rectangle getBounds2() {
        return null;
    }

    public Rectangle getBoundX() {
        return null;
    }

    public Rectangle getBoundY() {
        return null;
    }
}
