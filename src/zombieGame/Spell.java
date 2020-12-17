package zombieGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Spell extends GameObject {

    private final Handler handler;
    private Map map;
    private GameObject player;
    private float x;
    private float y;

    protected BufferedImage bulletSS;

    public Spell(float x, float y, ID id, Handler handler, int mx, int my, SpriteSheet ss, Map map) {
        super(x, y, id, ss);
        this.x = x;
        this.y = y;

        this.handler = handler;
        this.map = map;

        velX = (mx - x) / 10;
        velY = (my - y) / 10;

        bulletSS = map.spriteSheet.grabImage2(0, 50, 16, 14);


        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                player = tempObject;
            }
        }
//        if (mx > player.getX()) {
//            // Shot to the right
//            bulletSS = ss.grabImage2(0, 0, 21, 13);
//        } else if (mx < player.getX()) {
//            // Shot to the left
//            bulletSS = ss.grabImage2(0, 14, 21, 13);
//        } else if (my > player.getY()) {
//            // Shot up
//            bulletSS = ss.grabImage2(22, 0, 14, 21);
//        } else {
//            // Shot down
//            bulletSS = ss.grabImage2(36, 0, 14, 21);
//        }

        float y0 = player.getY();
        float yf = player.getY() + my;
        float x0 = player.getX();
        float xf = player.getX() + mx;

        float d = (float) Math.sqrt(Math.pow((xf - x0), 2) - Math.pow((yf - y0), 2));


//            System.out.println("For: " + (xf - x0)  + " and " + (yf - y0) +  ": " + d);

//        if (mx > player.getX()) {
//            System.out.println("Going right");
//        }
//        if (mx < player.getX()) {
//            System.out.println("Going left");
//        }
//        if (my > player.getY()) {
//            System.out.println("Going up");
//        }
//        if (my < player.getY()) {
//            System.out.println("Going down");
//        }

    }


    public void tick() {

        x += velX;
        y += velY;


        for (int j = 0; j < handler.object.size(); j++) {
            GameObject tempObject = handler.object.get(j);
            if (tempObject.getId() == ID.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeBullet(this);
                }
            }
        }

//        handler.addObject(new Trail(x, y, ID.Trial,Color.cyan, 16, 16, 0.01f, handler, null));
    }

    public void render(Graphics g) {
        g.drawImage(bulletSS, (int) x, (int) y, null);


    }


    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 8, 8);
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
