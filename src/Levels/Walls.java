package Levels;

import Main.Game;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Map.Node;

import java.awt.*;

public class Walls extends GameObject {

    private final int w, h;
    private GameObject player;

    public Walls(double posX, double posY, double velX, double velY, int w, int h, Handler handler, ID id) {
        super(posX, posY, velX, velY, handler, id);

        this.w = w;
        this.h = h;

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Player) {
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
        g.setColor(Color.gray.darker());
        g.fillRect((int) posX, (int) posY, w, h);

//        g.setColor(Color.red.brighter());
//        g2d.draw(getBounds());
    }

    public Rectangle getBounds() {
        return new Rectangle((int) posX, (int) posY, w, h);
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
