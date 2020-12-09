package zombieGame;

import java.awt.*;

public class MagicBomb extends GameObject {

    private float x;
    private float y;
    private final int dir;

    public MagicBomb(float x, float y, ID id, int dir, SpriteSheet ss) {
        super(x, y, id, ss);
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void tick() {
    }

    public void render(Graphics g) {
        g.setColor(Color.BLUE.brighter());
        g.fillRect((int) x, (int) y, 16, 16);

    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    public Rectangle getBounds2() {
        return null;
    }
}
