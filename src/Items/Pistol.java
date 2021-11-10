package Items;

import Main.Game;
import Main.GamePanel;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Manager.ItemObject;
import Player.Inventory;
import Render.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pistol extends ItemObject {

    private final BufferedImage image;
    private GameObject player;
    private Inventory inventory;

    public Pistol(double posX, double posY, double iconX, double iconY, Inventory inventory, ID id, Handler handler) {
        super(posX, posY, iconX, iconY, inventory, id);

        this.inventory = inventory;
        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("/pistol_5.png");


        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Player) {
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
//                iconX = GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE * invInfo[0] + 4;
//                setInventoryPos(invInfo[1]);
//                iconY = 8;
//            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(image, (int) posX, (int) posY, null);

//        g.setColor(Color.red);
//        ((Graphics2D) g).draw(getBounds());

    }


    public Rectangle getBounds() {
        return new Rectangle((int) posX, (int) posY, image.getWidth(), image.getHeight());
    }

    public void drawIcon(Graphics g) {

        g.drawImage(image, (int)iconX, (int)iconY, null);

    }

    public void increase() {

    }


}
