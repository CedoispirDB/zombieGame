package Game.Levels;

import Game.Manager.GameObject;
import Game.Manager.Handler;
import Game.Manager.ID;
import Game.Map.Node;
import Game.Render.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Walls extends GameObject {

    private final int w, h;
    private GameObject player;
    private BufferedImage image;

    public Walls(double posX, double posY, double velX, double velY, int w, int h, Handler handler, ID id, ImageManager imageManager) {
        super(posX, posY, velX, velY, handler, id);

        this.w = w;
        this.h = h;


        image = imageManager.getTexture("w");


        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.PLAYER) {
                this.player = temp;
            }
        }
    }

    public void tick() {
        if (player != null) {
            // Check if player collides with any of the walls

            // Check player's x position relative to the wall's x position
//            if (player.getPosX() > this.posX && this.posX + 64 > player.getPosX() && player.getPosY() < this.posY + 32 && this.posY  < player.getPosY()) {
////                System.out.println("case 1");
//                player.setVelX(0);
//                player.setPosX(player.getPosX() + 5);
//            }
//            if (player.getPosX() < this.posX) {
//                System.out.println("case 2");
//                player.setVelX(0);
//                player.setPosX(player.getPosX() - 5);
//
//            }
//
//            // Check player's y position relative to the wall's y position
//            if (player.getPosY() > this.posY) {
//                System.out.println("case 3");
//                player.setVelY(0);
//                player.setPosY(player.getPosY() + 5);
//            }
//            if (player.getPosY() < this.posY) {
//                System.out.println("case 4");
//                player.setVelY(0);
//                player.setPosY(player.getPosY() - 5);
//
//            }
        }

    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

//        g.fillRect((int) posX, (int) posY, w, h);

       g2d.setColor(Color.BLUE);
//        g2d.draw(getBoundsY());

        if(w == 32 && h == 32) {
            g.drawImage(image, (int) posX, (int) posY, null);
        } else {

            if (w != 32 ) {

                for (int i = (int)posX; i <= posX + w - 32; i += 32) {
                    g.drawImage(image,  i, (int) posY, null);
                }
            }

            if (h != 32) {
                for (int i = (int)posY; i <= posY + h - 32; i += 32) {
                    g.drawImage(image, (int) posX,  i, null);
                }
            }
        }

//        g2d.setColor(Color.red);
//        g2d.draw(getBoundsX());

    }

    public Rectangle getBounds() {
        return new Rectangle((int) posX, (int) posY, w, h);
    }

    public Node getNode() {
        return null;
    }

    public Rectangle getBoundsX() {
        return new Rectangle((int) posX, (int) posY + 4, w, h - 10);
    }

    public Rectangle getBoundsY() {
        return new Rectangle((int) posX + 2, (int) posY, w - 4, h);
    }
}
