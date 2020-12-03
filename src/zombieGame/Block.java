package zombieGame;

import java.awt.*;

public class Block extends GameObject {
    public Block(Game game, float x, float y, ID id, Handler handler, HUD hud) {
        super(game, x, y, id, handler, hud);
    }

    public void tick() {

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
