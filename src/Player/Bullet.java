package Player;

import Manager.EnemyObject;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Main.GamePanel;
import Map.Node;
import Render.Animation;
import Render.CreateImages;
import Render.ImageManager;

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
    private int ox, oy;

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
                ox = -8;
                oy = 0;
            }
            case "s" -> {
                this.velX = 0;
                this.velY = speed;
                image = sprite.getSubimage(0, 16, 16, 16);
                ox = -8;
                oy = 0;
            }
            case "d" -> {
                this.velX = speed;
                this.velY = 0;
                image = sprite.getSubimage(0, 0, 16, 16);
                ox = 0;
                oy = -8;
            }
            case "a" -> {
                this.velX = -speed;
                this.velY = 0;
                image = sprite.getSubimage(16, 0, 16, 16);
                ox = 0;
                oy = -8;
            }
        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.PLAYER) {
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
//        System.out.println("posX: " +posX);
//        System.out.println("bound: " + (GamePanel.SCREEN_WIDTH - 30));

        if (destroy) {
            handler.removeObject(this);
        }

        if (!hit) {
            posX += velX;
            posY += velY;
        }

        if (posX < 0 || posX + 16 >= GamePanel.SCREEN_WIDTH - 10 || posY < 0 || posY + 16 >= GamePanel.SCREEN_HEIGHT - 10) {
            hit = true;

        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.WALL) {
                if (temp.getBounds().intersects(getBounds())) {
                    hit = true;

                }
            }
        }

        for (int i = 0; i < handler.enemies.size(); i++) {
            EnemyObject temp = handler.enemies.get(i);
            if (temp.getId() == ID.BASIC_ZOMBIE) {
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


        if (hit) {
            boolean c = animation.runAnimation(1);
            animation.renderAnimation(g, (int) posX + ox, (int) posY + oy);

            if (c) {
                destroy = true;
            }
        } else {
            g.drawImage(image, (int) posX, (int) posY, null);
        }
//        g.setColor(Color.red);
//        ((Graphics2D) g).draw(getBounds());
    }

    public Rectangle getBounds() {
        return new Rectangle((int) posX, (int) posY, image.getWidth(), image.getHeight());
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
