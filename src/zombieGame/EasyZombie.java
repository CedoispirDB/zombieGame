package zombieGame;

import java.awt.*;

public class EasyZombie extends GameObject {

    private final Handler handler;
    private GameObject player;
    private final HUD hud;
    private final Color zombieCol = new Color(0, 102, 0);


    public EasyZombie(int x, int y, ID id, Handler handler, HUD hud) {
        super(x, y, id);

        this.hud = hud;
        this.handler = handler;

        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.Player) {
                player = handler.object.get(i);
            }
        }

    }

    //Horizontal collision
    public Rectangle getBounds() {

        float bx = x + velX;
        float by = y;
        float bw = 32 + (velX - 5) / 2;
        float bh = 32;

        return new Rectangle((int) bx, (int) by, (int) bw, (int) bh);
    }

    //Vertical collision
    public Rectangle getBounds2() {

        float bx = x;
        float by = y + velY;
        float bw = 31;
        float bh = 31 + velY / 2;


        return new Rectangle((int) bx, (int) by, (int) bw, (int) bh);
    }

    public Rectangle getBoundX() {
        return null;
    }

    public Rectangle getBoundY() {
        return null;
    }

    public void tick() {
        x += velX;
        y += velY;

        float diffX = x - player.getX();
        float diffY = y - player.getY();
        float difference = (float) Math.sqrt((x - player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY()));


        velX = (float) ((-1.0 / difference) * diffX);
        velY = (float) ((-1.0 / difference) * diffY);

//        collision();
        hit();

    }

    private void collision() {

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Obstacles) {

                if (getBounds().intersects(tempObject.getBounds())) {

                    if (velX > 0) {// Going to the right

                        velX = 0;
                        velY = 5;
                        x = tempObject.getX() - 33;
                        velX = 5;

                    } else if (velX < 0) {// Going to the left
                        velX = 0;
                        velY = 5;
                        x = tempObject.getX() + 28;
                        velX = -5;

                    }

                }

                if (getBounds2().intersects(tempObject.getBounds())) {

                    if (velY > 0) {// Going to Down

                        velY = 0;
                        y = tempObject.getY() - 34;
                        velY = 5;

                    } else if (velY < 0) {// Going up
                        velY = 0;
                        y = tempObject.getY() + tempObject.getH() + 3;
                        velY = -5;

                    }
                }

            }

        }
    }

    public void hit() {
        if (handler.object.size() != 0) {

            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);

                if (tempObject.getId() == ID.MagicBomb) {
                    // tempObject is now Bullet
                    if (getBounds().intersects(tempObject.getBounds())) {
                        // Collision code
                        for (int j = 0; j < handler.object.size(); j++) {
                            GameObject enemy = handler.object.get(j);
                            if (enemy.getId() == ID.SmartEnemy) {
                                handler.object.remove(this);
                                handler.object.remove(tempObject);
                                int x = hud.getScore();
                                hud.setScore(x + 1);
                                return;
                            }
                        }
                    }

                }
            }
        }
    }


    public void render(Graphics g) {
        g.setColor(zombieCol.darker());
        g.fillRect((int) x, (int) y, 20, 20);

    }
}
