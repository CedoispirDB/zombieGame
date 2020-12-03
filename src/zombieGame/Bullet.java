package zombieGame;

import java.awt.*;

public class Bullet extends GameObject {

    private Handler handler;
    private GameObject player;
    private float x;
    private float y;
    private int dir;

    public Bullet(float x, float y, ID id, Handler handler, int dir) {
        super(x, y, id);
        this.x = x;
        this.y = y;
        this.handler = handler;
        this.dir = dir;
    }

    public void tick() {
        if (dir == 0) {
            y -= 5;
        }
        if (dir == 1) {
            y += 5;
        }
        if (dir == 2) {
            x += 5;
        }
        if (dir == 3) {
            x -= 5;
        }
        if (dir== 4){
            System.out.println("hey");
        }


        if (y >= Game.HEIGHT || y <= 0 || x <= 0 || x >= Game.WIDTH){
            handler.removeObject(this);
        }

    }

//    public void tick() {
//
//        float angle = (float) Math.atan2(my - y, mx - x);
//        int bulletVel = 5;
//
//
//        if (i == 1) {
//            velX = (float) ((bulletVel) * Math.cos(angle));
//            velY = (float) ((bulletVel) * Math.sin(angle));
//        }
////        printTools.d("velX: " + (int) velX);
////        printTools.d("velY: " + (int) velY);
//
//        x += velX;
//        y += velY;
//        i++;
////        printTools.d("x: " + (int) x);
////        printTools.d("y: " + (int) y);
//
//        if (y >= Game.HEIGHT || y <= 0 || x <= 0 || x >= Game.WIDTH) {
//            handler.removeObject(this);
//        }
//    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect((int) x, (int) y, 16, 16);

    }

    public int getDir() {
        return dir;
    }


    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    public Rectangle getBounds2() {
        return null;
    }
}
