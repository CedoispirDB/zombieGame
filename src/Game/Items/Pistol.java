package Game.Items;

import Game.Manager.GameObject;
import Game.Manager.Handler;
import Game.Manager.ID;
import Game.Manager.ItemObject;
import Game.Player.Inventory;
import Game.Render.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pistol extends ItemObject {

    private final BufferedImage image;
    private GameObject player;
    private Inventory inventory;
    private Handler handler;

    public Pistol(double posX, double posY, double iconX, double iconY, Inventory inventory, ID id, Handler handler) {
        super(posX, posY, iconX, iconY, inventory, id);

        this.inventory = inventory;
        this.handler = handler;

        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("/pistol_5.png");



    }

    public void tick() {

        if (player == null) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject temp = handler.object.get(i);
                if (temp.getId() == ID.PLAYER) {
                    player = temp;
                }
            }
        }
//        System.out.println("posX: " + posX);
//        System.out.println("posY: " + posY);

        if (player != null) {
//            System.out.println("player posX: " + player.getPosX());
//            posX = player.getPosX();
//            posY = player.getPosY();
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
