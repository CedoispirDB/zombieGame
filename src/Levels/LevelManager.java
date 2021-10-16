package Levels;

import DataManager.SaveData;
import Items.HealingPotion;
import Items.Pistol;
import Main.Game;
import Main.GamePanel;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
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

    public LevelManager(Handler handler, SaveData saveData, Inventory inventory) {
        this.handler = handler;
        this.saveData = saveData;
        this.inventory = inventory;

        available = new LinkedList<>();
    }


    public void loadLevel(int level) {
        handler.addObject(new Player(352, 352, 0, 0, handler, ID.Player, inventory));

        LinkedList<String> savedData = saveData.readFromFile(level);

        if (savedData != null) {
//            System.out.println("Loading: " + savedData);
            LinkedList<String> data = new LinkedList<>(savedData);


            int x, y, w, h;
            String type;

            do {
                x = Integer.parseInt(savedData.get(0));
                y = Integer.parseInt(savedData.get(1));
                w = Integer.parseInt(savedData.get(2));
                h = Integer.parseInt(savedData.get(3));
                type = savedData.get(4);

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
                }
            } while (savedData.size() > 0);

            getEmptySpaces(data);

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

    public static LinkedList<Integer> getAvailable() {
        return available;
    }
}
