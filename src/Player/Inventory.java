package Player;

import Items.Pistol;
import Levels.LevelManager;
import Main.GamePanel;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Manager.ItemObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Inventory {

    // items is the item object in the map
    public LinkedList<ItemObject> items;
    // item is the item object in out inventory
    public LinkedList<ItemObject> inventoryItems;

    // offSet is where the player is selecting
    private int offSet = 6;
    // pos is where to draw the item icon
    private int pos = 6;
    private int selected;
    private char itemInHand = 'n';
    private GameObject player;
    private Handler handler;
    private int[] positions;

    public Inventory(Handler handler) {
        items =  new LinkedList<>();
        inventoryItems = new LinkedList<>();
        this.handler = handler;

        positions = new int[5];
        positions[0] = 1;
        positions[1] = 1;
        positions[2] = 1;
        positions[3] = 1;
        positions[4] = 1;

    }

    public void tick() {


        for (int i = 0; i < items.size(); i++) {
            items.get(i).tick();
        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Player) {
                player = temp;
            }
        }


    }

    public void render(Graphics g) {

//        System.out.println(items);
        g.setColor(new Color(0f, 0f, 0f, 0.3f));
        g.fillRect(GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE * 6, 0 , GamePanel.UNIT_SIZE * 5, GamePanel.UNIT_SIZE);
        g.setColor(new Color(1f, 0f ,0f));
        selected = GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE * offSet;
        g.drawRect(selected, 1 , GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);

        // Load items on inventory
        for (int i = 0; i < items.size(); i++) {
            items.get(i).render(g);
        }

        // Draw icons
        for (int i = 0; i < inventoryItems.size(); i++) {
//            System.out.println(inventoryItems.get(i));
            ItemObject itemObject = inventoryItems.get(i);
            itemObject.drawIcon(g);
        }
    }

    public void addItem(ItemObject itemObject) {
        items.add(itemObject);
    }

    public void removeItem(ItemObject itemObject) {
        this.items.remove(itemObject);
    }

    public void addToInventory(ItemObject itemObject) {
        inventoryItems.add(itemObject);
    }

    public void removeFromInventory() {


        for (int i = 0; i < inventoryItems.size(); i++) {
            ItemObject tempItem = inventoryItems.get(i);
            double localX = tempItem.getIconX();
//            System.out.println(inventoryItems.get(i));

            if (localX > selected && localX < selected + 32) {
                items.add(tempItem);
                inventoryItems.remove(tempItem);
                positions[(int)tempItem.getInventoryPos()] = 1;
                tempItem.setPosX(player.getPosX() + 35);
                tempItem.setPosY(player.getPosY());
                break;
            }
        }

        pos++;
    }

    public boolean canDrop(int playerX, int playerY) {
        boolean drop = false;
        LinkedList<Integer> available = new LinkedList<>(LevelManager.getAvailable());

        System.out.println("playerX: " + playerX);
        System.out.println("playerY: " + playerY);

        int possA = 0;
        int possB = 0;

        do {
            possA = available.get(0);
            possB = available.get(1);
            available.remove(0);
            available.remove(0);

            if (playerX + 32 == possA && playerY == possB) {
                break;
            }


        } while (available.size() > 0);

        System.out.println("possA: " + possA);
        System.out.println("possB: " + possB);

        return drop;
    }


    public void changeItem() {
        if (offSet != 2) {
            offSet--;
        } else {
            offSet = 6;
        }


    }

    public void dropItem() {

    }

    public int[] getPos() {
        int index = 0;
        int temPos = 0;
        int pos = 0;

        for (int i = 0; i < positions.length; i++) {
            temPos = positions[i];
            if (temPos == 1) {
                index = i;
                positions[i] = 0;
                break;
            }
        }

        switch (index) {
            case 0 -> pos = 6;
            case 1 -> pos = 5;
            case 2 -> pos = 4;
            case 3 -> pos = 3;
            case 4 -> pos = 2;
        }

        System.out.println("pos: " + pos);

        int[] invInfo = new int[2];

        invInfo[0] = pos;
        invInfo[1] = index;

        return invInfo;
    }

    public void changePos() {
        pos -= 2;
    }

    public void addPos() {pos++;}

}
