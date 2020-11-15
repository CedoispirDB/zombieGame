package zombieGame;

import java.awt.*;

public class FastEnemy extends GameObject {

    private Handler handler;
    private HUD hud;

    public FastEnemy(int x, int y, ID id, Handler handler, HUD hud) {
        super(x, y, id);
        this.hud = hud;
        this.handler = handler;
        velX = 2;
        velY = 9;
    }

    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, 16,16);
    }

    @Override
    public Rectangle getBounds2() {
        return null;
    }

    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Game.HEIGHT - 32){
            velY *= -1;
        }

        if (x <= 0 || x >= Game.WIDTH - 16){
            velX *= -1;
        }

        handler.addObject(new Trail(x, y, ID.Trial,Color.cyan, 16, 16, 0.01f, handler));
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
                            if (enemy.getId() == ID.FastEnemy){
                                handler.object.remove(enemy);
                                handler.object.remove(tempObject);
                                int x = hud.getScore();
                                hud.setScore(x + 1);
                            }
                        }}

                }
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect((int)x,(int) y, 16, 16);

    }
}
