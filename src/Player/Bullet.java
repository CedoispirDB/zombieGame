package Player;

import Enemies.BasicZombie;
import Manager.EnemyObject;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Main.GamePanel;
import Map.Node;
import Render.Animation;
import Render.CreateImages;
import Render.ImageManager;
import Sound.SoundManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Bullet extends GameObject {

    private final Handler handler;
    private GameObject player;
    private final ImageManager imageManager;
    private BufferedImage image;
    private Animation animation;
    private boolean hit;
    private boolean destroy;

    public Bullet(double posX, double posY, double velX, double velY, Handler handler, ID id, String dir, ImageManager imageManager) {
        super(posX, posY, velX, velY, handler, id);

        this.handler = handler;
        this.imageManager = imageManager;


        hit = false;
        destroy = false;


        BufferedImage sprite = imageManager.getSprite("b");

        int speed = 10;

        switch (dir) {
            case "w" -> {
                this.velX = 0;
                this.velY = -speed;
                image = sprite.getSubimage(16, 16, 16, 16);
            }
            case "s" -> {
                this.velX = 0;
                this.velY = speed;
                image = sprite.getSubimage(0, 16, 16, 16);
            }
            case "d" -> {
                this.velX = speed;
                this.velY = 0;
                image = sprite.getSubimage(0, 0, 16, 16);
            }
            case "a" -> {
                this.velX = -speed;
                this.velY = 0;
                image = sprite.getSubimage(16, 0, 16, 16);
            }
        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Player) {
               player = temp;
            }
        }


        animation = new Animation();
        CreateImages createImages = new CreateImages();

        LinkedList<BufferedImage> frames = createImages.createFrames(imageManager.getSprite("e"), 0, 13, 1);

        animation.init(1);
        animation.setFrames(frames);

    }


    public void tick() {
//        System.out.println(velX);
//        System.out.println(velY);

        if (destroy) {
            handler.removeObject(this);
        }

        if (!hit) {
            posX += velX;
            posY += velY;
        }

        if (posX < 0 || posX >= GamePanel.SCREEN_WIDTH - 30|| posY < 0 || posY >= GamePanel.SCREEN_HEIGHT - 32) {
            hit = true;

        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Wall) {
                if (temp.getBounds().intersects(getBounds())) {
                    hit = true;

                }
            }
        }

        for (int i = 0; i < handler.enemies.size(); i++) {
            EnemyObject temp = handler.enemies.get(i);
            if (temp.getId() == ID.BasicZombie) {
                if (temp.getBounds().intersects(getBounds())) {
                    if (!hit) {
                        temp.setEnemyHealth(temp.getEnemyHealth() - ((Player) player).getDamage());
                    }
                    hit = true;

                }
            }
        }

    }

    public void render(Graphics g) {

//        g.setColor(Color.WHITE);
//        g.fillOval((int) posX, (int) posY, 10, 10);
//        g.setColor(Color.red);
//        ((Graphics2D) g).draw(getBounds());

        if (hit) {
            boolean c = animation.runAnimation(1);
            animation.renderAnimation(g, (int) posX, (int) posY);

            if (c) {
                destroy = true;
            }
        } else {
            g.drawImage(image, (int) posX, (int) posY, null);
        }
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
