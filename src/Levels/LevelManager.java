package Levels;

import DataManager.SaveData;
import Enemies.BasicZombie;
import Items.HealingPotion;
import Items.Pistol;
import Main.Game;
import Main.GamePanel;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Map.Node;
import Player.Inventory;
import Player.Player;


import java.awt.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;

public class LevelManager {

    private final Handler handler;
    private final SaveData saveData;
    private Inventory inventory;
    private static LinkedList<Integer> available;
    private int cols = GamePanel.SCREEN_WIDTH / GamePanel.UNIT_SIZE;
    private int rows = GamePanel.SCREEN_HEIGHT / GamePanel.UNIT_SIZE;

    private Node[][] grid;

    public LevelManager(Handler handler, SaveData saveData, Inventory inventory) {
        this.handler = handler;
        this.saveData = saveData;
        this.inventory = inventory;

        available = new LinkedList<>();

        grid = new Node[cols][rows];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j] = new Node(i * 32, j * 32, 32, 32,i, j, rows, cols, "n");
            }
        }


//        for (int i = 0; i < cols; i++) {
//            for (int j = 0; j < rows; j++) {
//                System.out.println(grid[i][j].getNeighbors());
//            }
//        }

    }


    public void loadLevel(int level) {
        handler.addObject(new Player(352, 352, 0, 0, handler, ID.Player, inventory));

        LinkedList<String> savedData = saveData.readFromFile(level);

        if (savedData != null) {
//            System.out.println("Loading: " + savedData);
            LinkedList<String> data = new LinkedList<>(savedData);


            int x, y, w, h;
            String type;
            Node currentNode;

            do {
                x = Integer.parseInt(savedData.get(0));
                y = Integer.parseInt(savedData.get(1));
                w = Integer.parseInt(savedData.get(2));
                h = Integer.parseInt(savedData.get(3));
                type = savedData.get(4);

                currentNode = grid[x / 32][y /32];


                if (type.equals("w") && (w != 32 || y != 32)) {
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
                    case "w" -> handler.addObject(new Walls(x, y, 0, 0, w, h, handler, ID.Wall));
                    case "b" -> handler.addObject(new Button(x, y, 0, 0, w, h, handler, ID.Button));
                    case "p" -> handler.addObject(new Passage(x, y, 0, 0, w, h, handler, ID.Passage, this));
                    case "h" -> inventory.addItem(new HealingPotion(x, y, 0, 0, inventory, ID.HEALING, handler));
                    case "g" -> inventory.addItem(new Pistol(x, y, 0, 0, inventory, ID.Pistol, handler));
                    case "z" -> handler.addObject(new BasicZombie(x, y, 0, 0, handler, ID.BasicZombie, this));

                }

            } while (savedData.size() > 0);
            getEmptySpaces(data);
        }

//        int count = 1;
//        for (int i = 0; i < cols; i++) {
//            for (int j = 0; j < rows; j++) {
//                System.out.println("Node " + count + ": " + grid[i][j]);
//                count++;
//            }
//        }

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j].addNeighbors(grid);
            }
        }

//        for (int i = 0; i < cols; i++) {
//            for (int j = 0; j < rows; j++) {
//                System.out.println(grid[i][j].getNeighbors());
//            }
//        }


    }

    private void createNodes(int x, int y, int w, int h, String type) {

        if (w != 32 ) {
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

        for (int k = 0; k < GamePanel.SCREEN_HEIGHT / GamePanel.UNIT_SIZE; k++) {
            y = k * GamePanel.UNIT_SIZE;

            for (int j = 0; j < GamePanel.SCREEN_WIDTH / GamePanel.UNIT_SIZE; j++) {
                x = j * GamePanel.UNIT_SIZE;

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
}
