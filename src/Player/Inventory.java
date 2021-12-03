package Player;

import Levels.LevelManager;
import Main.GamePanel;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Manager.ItemObject;
import Map.Node;

import java.awt.*;
import java.util.LinkedList;

public class Inventory {

    // items is the item object in the map
    public LinkedList<ItemObject> items;
    // item is the item object in the inventory
    public LinkedList<ItemObject> inventoryItems;

    // offSet is where the player is selecting
    private int offSet;
    // pos is where to draw the item icon
    private int pos = 6;
    private int selected;
    private char itemInHand = 'n';
    private GameObject player;
    private Handler handler;
    private int[] positions;
    private ItemObject selectedItem;
    private Interface anInterface;

    public Inventory(Handler handler, Interface anInterface) {
        items = new LinkedList<>();
        inventoryItems = new LinkedList<>();
        this.handler = handler;
        this.anInterface = anInterface;

        offSet = 6;

        positions = new int[5];
        positions[0] = 1;
        positions[1] = 1;
        positions[2] = 1;
        positions[3] = 1;
        positions[4] = 1;

    }

    public void tick() {

        getSelectedItem();

//        inventoryItems.forEach(System.out :: println);
//        System.out.println(" ");

        for (int i = 0; i < items.size(); i++) {
            ItemObject temp = items.get(i);
            temp.tick();
        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.PLAYER) {
                player = temp;
            }
        }



    }

    public void render(Graphics g) {

//        System.out.println(items);
        g.setColor(new Color(0f, 0f, 0f, 0.8f));
        g.fillRect(GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE * 6, 0, GamePanel.UNIT_SIZE * 5, GamePanel.UNIT_SIZE);
        g.setColor(Color.white);
        g.drawRect(GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE * 6, 1, GamePanel.UNIT_SIZE * 5, GamePanel.UNIT_SIZE);
        for (int i = 26; i < 31; i++) {
            g.drawRect(32 * i, 0, 0, 32);
        }
        g.setColor(new Color(1f, 0f, 0f));
        selected = GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE * offSet;
        g.drawRect(selected, 1, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);


        // Load items on inventory
        for (int i = 0; i < items.size(); i++) {
            ItemObject temp = items.get(i);
            temp.render(g);

        }


//        inventoryItems.forEach(System.out :: println);
//
        // Draw icons
        for (int i = 0; i < inventoryItems.size(); i++) {
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
        if (inventoryItems.size() < 5) {
            inventoryItems.add(itemObject);
        }
    }

    public void removeFromInventory(LevelManager levelManager) {
//        System.out.println("playerX: " + player.getPosX());
        Node[][] grid = levelManager.getGrid();
        Node currentNode = grid[(int) (player.getPosX() + 16) / 32][(int) (player.getPosY() + 16) / 32];
        LinkedList<Node> neighbors;
        Node available = null;

        for (int i = 0; i < inventoryItems.size(); i++) {
            ItemObject tempItem = inventoryItems.get(i);
            double localX = tempItem.getIconX();

            if (localX >= selected && localX < selected + 32) {
                // Remove from inventory list and add to map items

                inventoryItems.remove(tempItem);
                items.add(tempItem);
//                System.out.println("currentNode: " + currentNode);

                neighbors = currentNode.getNeighbors();

//                neighbors.forEach(System.out :: println);

                for (int j = 0; j < neighbors.size(); j++) {
                    Node temp = neighbors.get(j);
                    if (!temp.getType().equals("w")) {
                        available = temp;
                        break;
                    }
                }

//                System.out.println("available Node: " + available);

                positions[(int) tempItem.getInventoryPos()] = 1;

                if (available != null) {
                    int aX = available.getX();
                    int aY = available.getY();
                    int pX = (int) player.getPosX();
                    int pY = (int) player.getPosY();
                    int cX = currentNode.getX();
                    int cY = currentNode.getY();

                    int dx = 0;
                    int dy = 0;

//                    System.out.println("aY: " + aY + " cY: " + cY);
                    if (aY == cY) {
                        // Only available left and right
                        if (aX > cX) {
                            dx = pX + 32 + 4;
                            dy = pY + 8;
                        }

                        if (aX < pX) {
                            dx = pX - 34;
                            dy = pY + 8;
                        }

                    } else if (aX == cX) {
                        // Only available up or down
                        if (aY > pY) {
                            dx = pX + 9;
                            dy = pY + 32 + 4;
                        }

                        if (aY < pY) {
                            dx = pX + 9;
                            dy = pY - 4;
                        }

                    }

//                    tempItem.setPosX(player.getPosX() + 32 + 4);
//                    tempItem.setPosY(available.getY() + 8);
//
//                    System.out.println("Setting to x: "  + dx);
//                    System.out.println("Setting to y: "  + dy);
                    tempItem.setPosX(dx);
                    tempItem.setPosY(dy);
                }


                break;
            }
        }

        pos++;
        selectedItem = null;
    }

    public ItemObject getSelectedItem() {
        for (int i = 0; i < inventoryItems.size(); i++) {
            ItemObject tempItem = inventoryItems.get(i);
//            System.out.println(tempItem);
            double localX = tempItem.getIconX();
//            System.out.println("x: " + tempItem.getIconX());
//            System.out.println("selected: " + selected);
            if (localX >= selected && localX < selected + 32) {
                selectedItem = tempItem;
                break;
            } else {
                selectedItem = null;
            }
        }
        return selectedItem;
    }

    public void heal() {
        if (canHeal()) {
            anInterface.setHealth(25);
            inventoryItems.remove(selectedItem);
            positions[(int) selectedItem.getInventoryPos()] = 1;
            selectedItem = null;
        }
    }

    public boolean canHeal() {
        if (selectedItem == null) {
            return false;
        }
        return selectedItem.getId() == ID.HEALING;
    }

    public boolean canShoot() {
        if (selectedItem == null) {
            return false;
        }
        return selectedItem.getId() == ID.PISTOL || selectedItem.getId() == ID.SHOTGUN;
    }

    public void setSelectedItem(ItemObject selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void changeItem(int opt) {
        if (opt == 0) {
            if (offSet != 2) {
                offSet--;
            } else {
                offSet = 6;
            }
        } else if (opt == 1) {
            if (offSet != 6) {
                offSet++;
            } else {
                offSet = 2;
            }
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


        int[] invInfo = new int[2];

        invInfo[0] = pos;
        invInfo[1] = index;

        return invInfo;
    }

    public void changePos() {
        pos -= 2;
    }

    public void addPos() {
        pos++;
    }

    public void clearItems() {
        items.clear();
    }

    public void cleanInventory() {
        offSet = 6;
        inventoryItems.clear();
        positions[0] = 1;
        positions[1] = 1;
        positions[2] = 1;
        positions[3] = 1;
        positions[4] = 1;
    }

}
