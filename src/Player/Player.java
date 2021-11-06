package Player;

import Levels.LevelManager;
import Main.Game;
import Main.GamePanel;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Manager.ItemObject;
import Map.Node;
import Render.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Player extends GameObject {

    private final Handler handler;
    private final BufferedImage image;
    private final Inventory inventory;
    private ItemObject pistol;
    private LevelManager levelManager;
    private LinkedList<Node> closedNode;
    private int health;
    private GameObject button;
    private GameObject passage;

    public Player(double posX, double posY, double velX, double velY, Handler handler, ID id, Inventory inventory, LevelManager levelManager) {
        super(posX, posY, velX, velY, handler, id);

        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("/player.png");

        this.handler = handler;
        this.inventory = inventory;
        this.levelManager = levelManager;

        health = 100;

        for (int i = 0; i < inventory.items.size(); i++) {
            ItemObject itemObject  = inventory.items.get(i);
            if (itemObject.getId() == ID.Pistol) {
                pistol = itemObject;
            }
        }

        for (int i = 0; i < handler.object.size() ; i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Button) {
                button = temp;
            } else if(temp.getId() == ID.Passage) {
                passage = temp;
            }
        }

        closedNode = new LinkedList<>();
    }


    public void tick() {

        if (posX + 32 > GamePanel.SCREEN_WIDTH) {
            posX -= 5;
        }
        if (posX < 0) {
            posX += 5;
        }

        if (posY + 32 > GamePanel.SCREEN_HEIGHT) {
            posY -= 5;
        }
        if (posY < 0) {
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

        pressButton();
        passLevel();
//        collision();
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

//        g.setColor(Color.blue.brighter());
//        g2d.draw(getBounds());
//        g.setColor(Color.green.brighter());
//        g2d.draw(getBoundsY());
    }

    public void pressButton() {
        if (getBounds().intersects(button.getBounds())) {
            levelManager.setButtonPressed(true);
        }
    }

    public void passLevel() {
        if (getBounds().intersects(passage.getBounds())) {
            if (levelManager.isButtonPressed()) {
                levelManager.setButtonPressed(false);
                levelManager.setLevel();
                levelManager.loadLevel(levelManager.getLevel());

            }
        }
    }


    // Collision detection
    public boolean collision() {
        boolean collision = false;

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.BasicZombie) {
                if (temp.getBounds().intersects(getBounds())) {
                    collision = true;
                }
            }
        }
        return collision;
    }

    public boolean bounds() {
        boolean collide = false;

        Node[][] grid = levelManager.getGrid();

        Node currentNode = grid[(int) ((posX + 16) / 32)][(int) ((posY + 16)/ 32)];
        LinkedList<Node> neighbors = currentNode.getNeighbors();
        int obsPx = 0;
        int obsPy = 0;

        for (int i = 0; i < neighbors.size(); i++) {
            Node temp = neighbors.get(i);
            if (temp.getType().equals("w")) {
                closedNode.add(temp);
            }
        }

        System.out.println("Closed Node");
        closedNode.forEach(System.out :: println);
//        System.out.println("\n");

        for (int i = 0; i < closedNode.size(); i++) {
            Node temp = closedNode.get(i);
//            System.out.println("Temp: "  + temp);

            int currentX = currentNode.getX();
            int currentY = currentNode.getY();
            int tempX = temp.getX();
            int tempY = temp.getY();

            if (tempX < currentX) {
                if (temp.getX() + 32 == currentNode.getX()) {
                    obsPx = tempX;
                    if (obsPx + 32 < posX && obsPx + 35 >= posX) {
                        System.out.println("collide");
                        posX += 5;
                    }
                }
            }

            if (tempX > currentX) {
                if (temp.getX() == currentNode.getX() + 32) {
                    obsPx = tempX;
                    if (obsPx > posX && obsPx - 30 <= posX) {
                        System.out.println("collide");
                        posX -= 5;
                    }
                }
            }

            if (tempY > currentY) {
                if (temp.getY() == currentNode.getY() + 32) {
                    obsPy = tempY;
                }
            }

            if (tempY < currentY) {
                if (temp.getY() + 32 == currentNode.getY()) {
                    obsPy = tempY;
                }
            }
        }





        closedNode.clear();
        return collide;
    }


    public Rectangle getBounds() {
        return new Rectangle((int) posX, (int) posY, image.getWidth(), image.getHeight());
    }

    public Node getNode() {
        return null;
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
