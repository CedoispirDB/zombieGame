package Player;

import Main.GamePanel;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Manager.ItemObject;
import Render.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

    private final Handler handler;
    private final BufferedImage image;
    private final Inventory inventory;
    private ItemObject pistol;

    public Player(double posX, double posY, double velX, double velY, Handler handler, ID id, Inventory inventory) {
        super(posX, posY, velX, velY, handler, id);

        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("/player_5.png");

        this.handler = handler;
        this.inventory = inventory;

        for (int i = 0; i < inventory.items.size(); i++) {
            ItemObject itemObject  = inventory.items.get(i);
            if (itemObject.getId() == ID.Pistol) {
                pistol = itemObject;
            }
        }


    }


    public void tick() {
//        System.out.println("velX: " + velX);
//        System.out.println("velY: " + velY);
//        System.out.println(id);

//        System.out.println("posX: " + posX);
//        System.out.println("posY: " + posY);

        if (posX + 32 > GamePanel.SCREEN_WIDTH) {
            velX = 0;
            posX -= 5;
        }
        if (posX < 0) {
            velX = 0;
            posX += 5;
        }

        if (posY + 32 > GamePanel.SCREEN_HEIGHT) {
            velY = 0;
            posY -= 5;
        }
        if (posY < 0) {
            velY = 0;
            posY += 5;
        }

//        if (pistol != null) {
//            if (getBounds().intersects(pistol.getBounds())) {
//                System.out.println("got gun");
//                inventory.addToInventory(pistol);
//                inventory.removeItem(pistol);
//
//            }
//        }

        posX += velX;
        posY += velY;
    }

    public void render(Graphics g) {
//        g.setColor(Color.red);
//        g.fillRect((int) posX, (int) posY, 32, 32);
        Graphics2D g2d = (Graphics2D) g;

        int x = (int) posX;
        int y = (int) posY;
        g.setColor(Color.red);
//        g.drawLine(x, y, x + image.getWidth(), y);
//        g.drawLine(x, y + image.getHeight(), x + image.getWidth(), y + image.getHeight());
//        g.drawLine(x, y, x, y + image.getHeight());
//        g.drawLine(x + image.getWidth(), y, x + image.getWidth(), y + image.getHeight());
//        System.out.println("w: " + image.getWidth());
//        System.out.println("h: " + image.getHeight());

        g.drawImage(image, (int) posX, (int) posY, null);

        g.setColor(Color.blue.brighter());
        g2d.draw(getBounds());
//        g.setColor(Color.green.brighter());
//        g2d.draw(getBoundsY());
    }

    public Rectangle getBounds() {
        return new Rectangle((int) posX, (int) posY, image.getWidth(), image.getHeight());
    }

    public Rectangle getBoundsX() {
        return new Rectangle((int) posX + 2, (int) posY + 1, 29,28);
    }

    public Rectangle getBoundsY() {
        return new Rectangle((int) posX + 5, (int) posY - 2, 25,34);
    }

    @Override
    public String toString() {
        return "Player.Player{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", velX=" + velX +
                ", velY=" + velY +
                ", handler=" + handler +
                ", id=" + id +
                '}';
    }
}
