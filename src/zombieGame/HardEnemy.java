package zombieGame;

import java.awt.*;
import java.util.Random;

public class HardEnemy extends GameObject {

    private final Handler handler;
    private HUD hud;

    private Random r = new Random();


    public HardEnemy(int x, int y, ID id, Handler handler, HUD hud) {
        super(x, y, id);
        this.handler = handler;
        this.hud = hud;
        velX = 5;
        velY = 5;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    @Override
    public Rectangle getBounds2() {
        return null;
    }

    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Game.HEIGHT - 32) {
            if (velY < 0) {
                velY = -(r.nextInt(7) + 1) * -1;
            } else {
                velY = (r.nextInt(7) + 1) * -1;
            }
        }

        if (x <= 0 || x >= Game.WIDTH - 16) {
            if (velX < 0) {
                velX = -(r.nextInt(7) + 1) * -1;
            } else {
                velX = (r.nextInt(7) + 1) * -1;
            }
        }

        handler.addObject(new Trail(x, y, ID.Trial, Color.yellow, 16, 16, 0.01f, handler));
        hit();
    }

    public void hit() {
        if (handler.object.size() != 0) {

            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);

                if (tempObject.getId() == ID.Bullet) {
                    // tempObject is now Bullet
                    if (getBounds().intersects(tempObject.getBounds())) {
                        // Collision code
                        for (int j = 0; j < handler.object.size(); j++) {
                            GameObject enemy = handler.object.get(j);
                            if (enemy.getId() == ID.BasicEnemy){
                                handler.object.remove(enemy);
                                handler.object.remove(tempObject);
                                int x = hud.getScore();
                                hud.setScore(x + 1);
                            }
                        }
                    }

                }
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect((int) x, (int) y, 16, 16);


    }
}
