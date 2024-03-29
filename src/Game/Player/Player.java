package Game.Player;

import Game.Levels.LevelManager;
import Game.Levels.Tutorial;
import Game.Main.GamePanel;
import Game.Manager.*;
import Game.Map.Node;
import Game.Render.Animation;
import Game.Render.CreateImages;
import Game.Render.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Player extends GameObject {

    private final Handler handler;
    private final Inventory inventory;
    private ItemObject pistol;
    private final LevelManager levelManager;
    private final LinkedList<Node> closedNode;
    private GameObject button;
    private GameObject passage;
    private final Interface anInterface;

    private int ctn;
    private Node currentNode;
    private Node currentNodeY;
    private Node[][] grid;
    private Rectangle barRecX;
    private Rectangle barRecY;
    private int damage;
    private ImageManager imageManager;
    private Animation animation;
    private LinkedList<BufferedImage> idle, left, right, yDir;
    private int count = 0;
    public boolean canPress;

    public Player(double posX, double posY, double velX, double velY, Handler handler, ID id, Inventory inventory, LevelManager levelManager, Interface anInterface, ImageManager imageManager) {
        super(posX, posY, velX, velY, handler, id);

        ctn = 0;


        this.anInterface = anInterface;
        this.handler = handler;
        this.inventory = inventory;
        this.levelManager = levelManager;

        this.imageManager = imageManager;

        damage = 10;

        barRecX = new Rectangle(32, 0, 160, 30);
        barRecY = new Rectangle(40, 0, 144, 32);

        for (int i = 0; i < inventory.items.size(); i++) {
            ItemObject itemObject = inventory.items.get(i);
            if (itemObject.getId() == ID.PISTOL) {
                pistol = itemObject;
            }
        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.BUTTON) {
                button = temp;
            } else if (temp.getId() == ID.PASSAGE) {
                passage = temp;
            }
        }

        closedNode = new LinkedList<>();

        grid = levelManager.getGrid();

        animation = new Animation();
        CreateImages createImages = new CreateImages();
        BufferedImage image = imageManager.getSprite("p");

        idle = createImages.createFrames(image, 3, 3, 4);
        left = createImages.createFrames(image, 1, 3, 2);
        right = createImages.createFrames(image, 0, 3, 1);
        yDir = createImages.createFrames(image, 2, 3, 3);

        animation.init(3);

        canPress = true;


    }


    public void tick() {

//        System.out.println(inventory.getSelectedItem());
        ItemObject temp = inventory.getSelectedItem();
        if (temp != null) {
            if (temp.getId() == ID.PISTOL) {
                damage = 5;
            } else if (temp.getId() == ID.SHOTGUN) {
                damage = 10;
            }
        }

        if (velX == 0 && velY == 0) {
            animation.setFrames(idle, (int) velX);
        } else if (velX > 0) {
            animation.setFrames(right, (int) velX);
        } else if (velX < 0) {
            animation.setFrames(left, (int) velX);
        } else if (velY != 0) {
            // + 1 is only to trick the system *You are tricking the system that you created*
            animation.setFrames(yDir, (int) velY + 1);
        }
        currentNode = grid[(int) (posX / 32)][(int) (posY / 32)];

        if (currentNode.getY() < 64) {
            currentNodeY = grid[(int) (posX / 32)][(int) ((posY + 32) / 32)];
        }

        if (GamePanel.gameState != STATE.BUILD) {
            if (anInterface.getHealth() <= 0) {
            GamePanel.gameState = STATE.DEATH;
            }
        }

        if (posX + 32 > GamePanel.SCREEN_WIDTH /*|| posX + 32 > 32 && currentNode.getY() == 0 && currentNode.getX() == 0*/) {
            posX -= 5;
        }

        if (posX < 0 /*|| posX < 192 && currentNodeY.getY() == 0 && currentNode.getX() == 160*/) {
            posX += 5;
        }

        if (posY + 32 > GamePanel.SCREEN_HEIGHT) {
            posY -= 5;
        }
        if (posY < 0 /*|| posY < 32 && currentNodeY.getY() == 32 && currentNode.getX() >= 32 && currentNode.getX() < 192*/) {
            posY += 5;
        }


        if (barRecY.intersects(getBounds())) {
            posY += 5;
        }
        if (barRecX.intersects(getBounds())) {
            if (currentNode.getX() < 32) {
                posX -= 5;
            } else {
                posX += 5;
            }
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
        if (GamePanel.gameState == STATE.GAME) {
            passLevel();
        } else if (GamePanel.gameState == STATE.TUTORIAL) {
            passTutorial();
        }
        enemyCollision();
        wallCollision();
        getItem();
        collectCoin();
    }

    public void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

//        g.drawImage(image, (int) posX, (int) posY, null);

        animation.runAnimation();
        animation.renderAnimation(g, (int) posX, (int) posY);

//        g.setColor(Color.BLUE);
//        g2d.draw(getBounds());
    }

    public void toggleCanPress() {
        canPress = !canPress;
    }

    public boolean canPress() {
        return canPress;
    }

    public void pressButton() {
        if (button != null && canPress) {
            if (getBounds().intersects(button.getBounds())) {

                levelManager.setButtonPressed(true);
                if (ctn == 0) {
                    anInterface.increaseScore(15);
                    ctn++;
                }
            }
        }
    }

    public void passLevel() {

        if (passage != null) {
            if (getBounds().intersects(passage.getBounds())) {
                if (levelManager.isButtonPressed()) {
                    // 4 is the last level for now
                    if (levelManager.getLevel() == 4) {
                        GamePanel.gameState = STATE.END;
                        return;
                    }
                    anInterface.increaseScore(50);
                    levelManager.setButtonPressed(false);
                    levelManager.setLevel();
                    levelManager.loadLevel(levelManager.getLevel());
                    for (int i = 0; i < handler.object.size(); i++) {
                        GameObject temp = handler.object.get(i);
                        if (temp.getId() == ID.BUTTON) {
                            button = temp;
                        } else if (temp.getId() == ID.PASSAGE) {
                            passage = temp;
                        }
                    }
                    ctn = 0;

                }
            }
        }
    }

    public void passTutorial() {

        if (passage != null) {
            if (getBounds().intersects(passage.getBounds())) {
                if (levelManager.isButtonPressed()) {
                    GamePanel.gameState = STATE.GAME;
                    System.out.println(levelManager.getLevel());
                    levelManager.setButtonPressed(false);
                    levelManager.loadLevel(1);
                    levelManager.setLevel(1);
                    anInterface.setScore(0);
                    anInterface.setHealth(160);
                    inventory.cleanInventory();
                    Tutorial.isTutorial = false;

                }
            }
        }
    }


    private void getItem() {
        int offSet = 0;
        for (int i = 0; i < inventory.items.size(); i++) {
            ItemObject temp = inventory.items.get(i);
            if (temp.getBounds().intersects(getBounds())) {
                if (inventory.inventoryItems.size() < 5) {
//                    System.out.println("getting: " + temp);
                    int[] invInfo = inventory.getPos();
                    inventory.addToInventory(temp);
                    inventory.removeItem(temp);
                    temp.setInInventory(true);
                    if (temp.getId() == ID.PISTOL) {
                        offSet = 4;
                    } else if (temp.getId() == ID.HEALING) {
                        offSet = 9;
                    }
                    temp.setIconX(GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE * invInfo[0] + offSet);
                    temp.setInventoryPos(invInfo[1]);
                    temp.setIconY(8);

                }
            }

        }
    }

    private void collectCoin() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.COIN) {
                if (temp.getBounds().intersects(getBounds())) {
                    anInterface.increaseScore(10);
                    handler.removeObject(temp);
                }
            }
        }
    }

    // Wall collision
    private void wallCollision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.WALL) {

                if (getBounds().intersects(temp.getBoundsX())) {
//                    System.out.println("My bounds: " + getBounds());
//                    System.out.println("Rec: " + temp.getBoundsX());
                    int dx = (int) (posX - temp.getPosX());

                    if (dx > 0) {
                        posX += 5;
                    } else {
                        posX -= 5;
                    }


                }

                if (getBounds().intersects(temp.getBoundsY())) {
                    int dy = (int) (posY - temp.getPosY());

                    if (dy > 0) {
                        posY += 5;
                    } else {
                        posY -= 5;
                    }
                }
            }
        }
    }

    // Collision detection
    private void enemyCollision() {

        for (int i = 0; i < handler.enemies.size(); i++) {
            EnemyObject temp = handler.enemies.get(i);
            if (temp.getId() == ID.BASIC_ZOMBIE) {
                if (temp.getBounds().intersects(getBounds())) {
                    anInterface.hit(5);
                }
            }
        }
    }

    public int getDamage() {
        return damage;
    }

    public boolean bounds() {
        boolean collide = false;

        Node[][] grid = levelManager.getGrid();

        Node currentNode = grid[(int) ((posX + 16) / 32)][(int) ((posY + 16) / 32)];
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
        closedNode.forEach(System.out::println);
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
        return new Rectangle((int) posX, (int) posY, 32, 32);
    }

    public Node getNode() {
        return null;
    }

    public Rectangle getBoundsX() {
        return new Rectangle((int) posX + 2, (int) posY + 1, 29, 28);
    }

    public Rectangle getBoundsY() {
        return new Rectangle((int) posX + 5, (int) posY - 2, 25, 34);
    }

    @Override
    public String toString() {
        return "Game.Player.Game.Player{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", velX=" + velX +
                ", velY=" + velY +
                ", handler=" + handler +
                ", id=" + id +
                '}';
    }
}
