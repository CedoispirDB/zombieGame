package Levels;

import DataManager.SaveData;
import DataManager.ScoreManager;
import Enemies.BasicZombie;
import Items.HealingPotion;
import Items.Pistol;
import Main.GamePanel;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Map.Node;
import Player.Inventory;
import Player.Player;
import Render.BufferedImageLoader;
import Player.Interface;
import Render.ImageManager;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import static Main.GamePanel.*;

public class LevelManager {

    private final Handler handler;
    private final SaveData saveData;
    private Inventory inventory;
    private static LinkedList<Integer> available;
    private int cols = SCREEN_WIDTH / UNIT_SIZE;
    private int rows = SCREEN_HEIGHT / UNIT_SIZE;
    public boolean buttonPressed;
    public int level;
    private final Interface anInterface;
    private final ScoreManager scoreManager;
    private boolean hasNeighbors;
    private final ImageManager imageManager;

    private Node[][] grid;

    public LevelManager(Handler handler, SaveData saveData, Inventory inventory, Interface anInterface, ScoreManager scoreManager , ImageManager imageManager) {
        this.handler = handler;
        this.saveData = saveData;
        this.inventory = inventory;
        this.anInterface = anInterface;
        this.scoreManager = scoreManager;
        this.imageManager = imageManager;


        available = new LinkedList<>();


        grid = new Node[cols][rows];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j] = new Node(i * 32, j * 32, 32, 32, i, j, rows, cols, "n");
            }
        }


//        for (int i = 0; i < cols; i++) {
//            for (int j = 0; j < rows; j++) {
//                System.out.println(grid[i][j].getNeighbors());
//            }
//        }

    }

    public void loadLevel(int level) {

        LinkedList<String> savedData = saveData.readFromFile(level);

        hasNeighbors = false;
        System.out.println("trying");
        if (savedData != null) {
//            System.out.println("Loading: " + savedData);
            System.out.println("Loading level " + level);
            LinkedList<String> data = new LinkedList<>(savedData);
            resetLevel();

            int x, y, w, h;
            String type;
            Node currentNode;

            do {
                x = Integer.parseInt(savedData.get(0));
                y = Integer.parseInt(savedData.get(1));
                w = Integer.parseInt(savedData.get(2));
                h = Integer.parseInt(savedData.get(3));
                type = savedData.get(4);

                currentNode = grid[x / 32][y / 32];

                System.out.println("Current Node: " + currentNode + ", actual w: " + w + ", actual h: " + h + ", actual type: " + type);


                if (type.equals("w") && (w != 32 || h != 32)) {
                    // Create multiple nodes
//                    System.out.println("Passing: x: " +  x + ", y: " + y + ", w: " + w + ", h: " + h + ", type: " + type);
                    createNodes(x, y, w, h, type);
                } else if (type.equals("w")) {
                    currentNode.setType(type);
                }


//                System.out.println("Saved -> x: " + x + ", y: " + y + ", w: " + w + ", h: " + h + ", type: " + type);

                LevelBuilder.addData(savedData.get(0), savedData.get(1), savedData.get(2), savedData.get(3), savedData.get(4));

                savedData.remove(0);
                savedData.remove(0);
                savedData.remove(0);
                savedData.remove(0);
                savedData.remove(0);

                switch (type) {
                    case "w" -> handler.addObject(new Walls(x, y, 0, 0, w, h, handler, ID.Wall, imageManager));
                    case "b" -> handler.addObject(new Button(x, y, 0, 0, w, h, handler, ID.Button, imageManager));
                    case "p" -> handler.addObject(new Passage(x, y, 0, 0, w, h, handler, ID.Passage, this, imageManager));
                    case "h" -> inventory.addItem(new HealingPotion(x, y, 0, 0, inventory, ID.HEALING, handler));
                    case "g" -> inventory.addItem(new Pistol(x, y, 0, 0, inventory, ID.Pistol, handler));
                    case "z" -> handler.addEnemy(new BasicZombie(x, y, 0, 0, handler, ID.BasicZombie, this, anInterface, imageManager));
                    case "m" -> {
//                        boolean playerExists = false;
//
//                        for (int i = 0; i < handler.object.size(); i++) {
//                            GameObject temp = handler.object.get(i);
//                            if (temp.getId() == ID.Player) {
//                                playerExists = true;
//                                break;
//                            }
//                        }
//                        if (!playerExists) {
                            handler.addObject(new Player(x, y, 0, 0, handler, ID.Player, inventory, this, anInterface, scoreManager, imageManager));
//                        } else {
//                            for (int i = 0; i < handler.object.size(); i++) {
//                                GameObject temp = handler.object.get(i);
//                                if (temp.getId() == ID.Player) {
//                                    temp.setPosX(x);
//                                    temp.setPosY(y);
//                                }
//                            }
//                        }
                    }
                }

            } while (savedData.size() > 0);
            getEmptySpaces(data);
        }

        int count = 1;
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                Node temp = grid[i][j];
//                if (temp.getType().equals("p")) {
                    System.out.println("Node " + count + ": " + grid[i][j]);
                    count++;
//                }
            }
        }

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j].addNeighbors(grid);
            }
        }

        hasNeighbors = true;

//        for (int i = 0; i < cols; i++) {
//            for (int j = 0; j < rows; j++) {
//                System.out.println(grid[i][j].getNeighbors());
//            }
//        }


    }

    public boolean hasNeighbors() {
        return hasNeighbors;
    }

    public void renderLevel(Graphics g) {
        BufferedImage image;

        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("/texture.png");
        image = image.getSubimage(32, 0, 32, 32);

        for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
            for (int j = 0; j < SCREEN_HEIGHT / UNIT_SIZE; j++) {
                if (grid[i][j].getType().equals("n")) {
                    g.drawImage(image, i * 32, j * 32, null);
                }
            }
        }

    }

    private void createNodes(int x, int y, int w, int h, String type) {

        if (w != 32) {
            for (int i = x; i <= x + w - 32; i += 32) {
                grid[i / 32][y / 32] = new Node(i, y, 32, 32, i / 32, y / 32, rows, cols, type);
            }
        }

        if (h != 32) {
            for (int i = y; i <= y + h - 32; i += 32) {
                grid[x / 32][i / 32] = new Node(x, i, 32, 32, x / 32, i / 32, rows, cols, type);
            }
        }
    }

    private void getEmptySpaces(LinkedList<String> data) {

        int x, y, filledX, filledY;

        boolean filled = false;

        for (int k = 0; k < SCREEN_HEIGHT / UNIT_SIZE; k++) {
            y = k * UNIT_SIZE;

            for (int j = 0; j < SCREEN_WIDTH / UNIT_SIZE; j++) {
                x = j * UNIT_SIZE;

                for (int i = 0; i < data.size() / 5; i++) {

                    filledX = Integer.parseInt(data.get(i * 5));
                    filledY = Integer.parseInt(data.get((i * 5) + 1));

                    if (filledY == y) {
                        if (filledX == x) {
                            filled = true;
                            break;
                        } else {
                            filled = false;
                        }
                    }


                }

                if (!filled) {
                    available.add(x);
                    available.add(y);

                }

            }
        }
    }

    public Node[][] getGrid() {
        return grid;
    }

    public static LinkedList<Integer> getAvailable() {
        return available;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLevel() {
        level++;
    }

    public boolean isButtonPressed() {
        return buttonPressed;
    }

    public void setButtonPressed(boolean buttonPressed) {
        this.buttonPressed = buttonPressed;
    }

    private void resetNodes() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j].setType("n");
            }
        }
    }

    public void resetLevel() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j] = new Node(i * 32, j * 32, 32, 32, i, j, rows, cols, "n");
            }
        }
        handler.reset();
        buttonPressed = false;
        inventory.clearItems();
    }

    public void restart() {
        handler.reset();
        buttonPressed = false;
        inventory.clearItems();
        inventory.cleanInventory();
        anInterface.reset();
        setLevel(0);
    }
}
