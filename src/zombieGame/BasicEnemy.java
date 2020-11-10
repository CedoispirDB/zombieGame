package zombieGame;

import java.awt.*;

public class BasicEnemy extends GameObject {

    private final Handler handler;

    public BasicEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        velX = 5;
        velY = 5;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Game.HEIGHT - 32) {
            velY *= -1;
        }

        if (x <= 0 || x >= Game.WIDTH - 16) {
            velX *= -1;
        }

        handler.addObject(new Trail(x, y, ID.Trial, Color.red, 16, 16, 0.01f, handler));
        hit();
    }

    private void hit() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Bullet) {
                // tempObject is now Bullet
                if (getBounds().intersects(tempObject.getBounds())) {
                    // Collision code
                    handler.object.remove(this);
                    handler.object.remove(tempObject);
                }

            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) x, (int) y, 16, 16);


    }
}
