package zombieGame;

import java.awt.*;

public class Player extends GameObject {

    Handler handler;
    private HUD hud;

    public Player(int x, int y, ID id, Handler handler, HUD hud) {
        super(x, y, id);

        this.handler = handler;
        this.hud = hud;

    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);

    }

    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x, 0, Game.WIDTH - 35);
        y = Game.clamp(y, 0, Game.HEIGHT - 56);

        handler.addObject(new Trail((int) x, (int) y, ID.Trial, Color.white, 32, 32, 0.05f, handler));

        collision();
    }

    private void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
//            System.out.print(handler.object.size());
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy || tempObject.getId() == ID.EnemyBullet || tempObject.getId() == ID.EnemyBoss) {
                // tempObject is now enemy
                if (getBounds().intersects(tempObject.getBounds())) {
                    // Collision code
                    HUD.HEALTH -= 2;
                    if (tempObject.getId() != ID.EnemyBoss && hud.getLevel() == 10) {
                        handler.removeObject(tempObject);
                    }
                }
            }

        }
    }


    public void render(Graphics g) {
//        Shows the hit box
//        Graphics2D g2d = (Graphics2D) g;
//        g.setColor(Color.green);
//        g2d.draw(getBounds());

        g.setColor(Color.white);
        g.fillRect((int) x, (int) y, 32, 32);
    }


}
