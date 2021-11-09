package Player;

import Enemies.BasicZombie;
import Manager.EnemyObject;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Main.GamePanel;
import Map.Node;

import java.awt.*;

public class Bullet extends GameObject {

    private Handler handler;
    private GameObject player;

    public Bullet(double posX, double posY, double velX, double velY, Handler handler, ID id, String dir) {
        super(posX, posY, velX, velY, handler, id);

        this.handler = handler;

        int speed = 10;

        switch (dir) {
            case "w" -> {
                this.velX = 0;
                this.velY = -speed;
            }
            case "s" -> {
                this.velX = 0;
                this.velY = speed;
            }
            case "d" -> {
                this.velX = speed;
                this.velY = 0;
            }
            case "a" -> {
                this.velX = -speed;
                this.velY = 0;
            }
        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Player) {
               player = temp;
            }
        }

    }


    public void tick() {
//        System.out.println(velX);
//        System.out.println(velY);
        posX += velX;
        posY += velY;

        if (posX < 0 || posX > GamePanel.SCREEN_WIDTH || posY < 0 || posY > GamePanel.SCREEN_HEIGHT) {
            handler.removeObject(this);
        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Wall) {
                if (temp.getBounds().intersects(getBounds())) {
                    handler.removeObject(this);
                }
            }
        }

        for (int i = 0; i < handler.enemies.size(); i++) {
            EnemyObject temp = handler.enemies.get(i);
            if (temp.getId() == ID.BasicZombie) {
                if (temp.getBounds().intersects(getBounds())) {
                    temp.setEnemyHealth(temp.getEnemyHealth() - ((Player)player).getDamage());
                    handler.removeObject(this);
                }
            }
        }

    }

    public void render(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillOval((int) posX, (int) posY, 10, 10);
        g.setColor(Color.red);
//        ((Graphics2D) g).draw(getBounds());

    }

    public Rectangle getBounds() {
        return new Rectangle((int) posX, (int) posY, 10, 10);
    }

    public Node getNode() {
        return null;
    }


    public Rectangle getBoundsX() {
        return null;
    }

    public Rectangle getBoundsY() {
        return null;
    }
}
