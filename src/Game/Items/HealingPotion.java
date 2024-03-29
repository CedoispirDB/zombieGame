package Game.Items;

import Game.Manager.GameObject;
import Game.Manager.Handler;
import Game.Manager.ID;
import Game.Manager.ItemObject;
import Game.Player.Inventory;
import Game.Render.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealingPotion extends ItemObject {

    private final BufferedImage image;
    private GameObject player;
    private Inventory inventory;
    private int count = 0;

    public HealingPotion(double posX, double posY, double iconX, double iconY, Inventory inventory, ID id, Handler handler) {
        super(posX, posY, iconX, iconY, inventory, id);

        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("/health.png");

        this.inventory = inventory;


        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.PLAYER) {
                player = temp;
            }
        }
    }


    public void tick() {
        if (player != null) {
//            if (getBounds().intersects(player.getBounds())) {
//                int[] invInfo = inventory.getPos();
//                inventory.addToInventory(this);
//                inventory.removeItem(this);
//                iconX = GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE * invInfo[0] + 9;
//                setInventoryPos(invInfo[1]);
//                iconY = 8;
//            }
        }

    }

    public void render(Graphics g) {

        g.drawImage(image, (int) posX, (int) posY, null);


    }

    public Rectangle getBounds() {
        return new Rectangle((int) posX, (int) posY, image.getWidth(), image.getHeight());
    }

    public void drawIcon(Graphics g) {
        g.drawImage(image, (int)iconX, (int)iconY, null);

//        if (count > 0) {
//            g.setColor(Color.black);
//            g.drawString(String.valueOf(count), x + image.getWidth(), y + image.getHeight());
//        }

    }


    public void increase() {
        count++;
    }

}
