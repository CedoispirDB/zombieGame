package Player;

import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Main.GamePanel;

import java.awt.*;

public class Bullet extends GameObject {

    private Handler handler;
    private int mx, my;

    public Bullet(double posX, double posY, double velX, double velY, Handler handler, ID id, int mx, int my) {
        super(posX, posY, velX, velY, handler, id);

        this.mx = mx;
        this.my = my;

        this.handler = handler;

        this.velX = (mx - posX) / 10;
        this.velY = (my - posY) / 10;

    }


    public void tick() {
        posX += velX;
        posY += velY;

        if (posX < 0 || posX > GamePanel.SCREEN_WIDTH || posY < 0 || posY > GamePanel.SCREEN_HEIGHT) {
            handler.removeObject(this);
        }

    }

    public void render(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillOval((int)posX, (int)posY, 10, 10);

    }

    public Rectangle getBounds() {
        return null;
    }


    public Rectangle getBoundsX() {
        return null;
    }

    public Rectangle getBoundsY() {
        return null;
    }
}
