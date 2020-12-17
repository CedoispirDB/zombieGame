package zombieGame;


import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Crate extends GameObject {

    private final BufferedImage crateTexture;
    private final Map map;

    public Crate(Game game, float x, float y, ID id, Handler handler, HUD hud, Map map) {
        super(game, x, y, id, handler, hud);

        this.map = map;

        crateTexture = map.spriteSheet.grabImage(6, 2, 32, 32);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(crateTexture, (int) x, (int) y, null);
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
