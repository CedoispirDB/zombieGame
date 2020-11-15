package zombieGame;

import java.awt.*;
import java.util.LinkedList;

public class Obstacles extends GameObject {

    private final Color obsCol = new Color(64, 64, 64);
    private GameObject obstacle;
    private GameObject player;
    private Handler handler;
    LinkedList<Integer> oldCoordinates = new LinkedList<>();
    private int w;
    private int h;
    private Game game;


    public Obstacles(Game game, float x, float y, ID id, Handler handler, HUD hud) {
        super(game, x, y, id, handler, hud);

        this.handler = handler;
        this.game = game;
        obstacle = this;

        h = 100;
        w = 26;


    }

    public void tick() {
        if (handler.object.size() != 0) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getId() == ID.Bullet) {
                    // tempObject is now Bullet
                    if (getBounds().intersects(tempObject.getBounds())) {
                        // Collision code
                        for (int j = 0; j < handler.object.size(); j++) {
                            GameObject enemy = handler.object.get(j);
                            if (enemy.getId() == ID.Obstacles) {
                                handler.object.remove(tempObject);

                            }
                        }
                    }

                }
            }
        }
    }

    public void render(Graphics g) {

//      Shows the hit box
//        Graphics2D g2d = (Graphics2D) g;
//        g.setColor(Color.black);
//        g2d.draw(getBounds());

//        g.setColor(obsCol.darker());
//        g.setColor(obsCol);
//        g.fillRect((int) x, (int) y, w, h);

//        if (k % 2 == 0) {
//            g.setColor(obsCol.darker());
//            g.fillRect((int) x, (int) y, 26, 100);
//        }
//        for (GameObject tempObject : handler.object) {
//            if (tempObject.getId() == this.getId()) {
//                oldCoordinates.add((int) tempObject.getX() + 15 + (int) x);
//                oldCoordinates.add((int) tempObject.getY() + 15 + (int) y);
//            }
//
//        }
//        if (!(oldCoordinates.contains((int) this.getX()) && oldCoordinates.contains((int) this.getY())) && !(w <= 0 || w > Game.WIDTH || y <= 0 || y > Game.HEIGHT)) { {
//                g.setColor(obsCol.darker());
//                g.fillRect((int) x , (int) y + 150, h, w);
//            }
//        }

        g.setColor(obsCol.darker());
        g.fillRect((int) x , (int) y, w, h);

    }

    public Rectangle getBounds() {
//        if (!(oldCoordinates.contains((int) this.getX()) && oldCoordinates.contains((int) this.getY()))) {
//            if (k % 2 == 0) {
//                return new Rectangle((int) x + 100, (int) y + 100, h, w);
//            }
//        }
//        return new Rectangle((int) x, (int) y, w, h);
        return new Rectangle((int) x, (int) y , w, h);
    }

    public Rectangle getBounds2() {
        return null;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }
}
