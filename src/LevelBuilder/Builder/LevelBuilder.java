package LevelBuilder.Builder;

import Game.DataManager.SaveData;
import Game.Enemies.BasicZombie;
import Game.Items.Coin;
import Game.Items.HealingPotion;
import Game.Items.Pistol;
import Game.Items.Shotgun;
import Game.Levels.Button;
import Game.Levels.LevelManager;
import Game.Levels.Passage;
import Game.Levels.Walls;
import Game.Main.GamePanel;
import Game.Manager.GameObject;
import Game.Manager.Handler;
import Game.Manager.ID;
import Game.Player.Inventory;
import Game.Player.Player;
import Game.Render.ImageManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class LevelBuilder extends MouseAdapter {

    private static Handler handler;
    private LevelManager levelManager;
    public static char type = 'w';
    public static char position = 't';
    public static boolean drag = false;
    public static ID recentID, recentItemID;
    private static final LinkedList<ID> recentIDS = new LinkedList<>();
    private final LinkedList<ID> recentItemIDS = new LinkedList<>();
    public static LinkedList<String> data = new LinkedList<>();

    private final int unitSize;
    private final SaveData saveData;
    private int xi, yi;
    private boolean dragged = false;
    private static Inventory inventory;
    private final ImageManager imageManager;


    public LevelBuilder(Handler handler, SaveData saveData, Inventory inventory, ImageManager imageManager, LevelManager levelManager) {
        LevelBuilder.handler = handler;
        unitSize = GamePanel.UNIT_SIZE;
        this.saveData = saveData;
        this.inventory = inventory;
        this.imageManager = imageManager;
        this.levelManager = levelManager;

    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        int startX = 0;
        int endX = unitSize;
        int startY = 0;
        int endY = unitSize;
        int x, y;
        int w = unitSize;
        int h = unitSize;
        String currentType = "";


        do {

            if (startX <= mx && mx <= endX) {
                x = startX;
                break;
            }

            startX = endX;
            endX += unitSize;

        } while (true);

        do {

            if (startY <= my && my <= endY) {
                y = startY;
                break;
            }

            startY = endY;
            endY += unitSize;

        } while (true);

//        System.out.println("x: " + x + "\ny:" + y + "\n");

        if (drag) {
            xi = x;
            yi = y;
            dragged = true;
            drag = false;
            return;
        }

        if (dragged) {
            int yf;
            int xf;

            if (yi > y) {
                yf = yi + unitSize;
                h = yf - y;
            } else {
                yf = y + unitSize;
                h = yf - yi;
                y = yi;
            }


            if (xi > x) {
                xf = xi + unitSize;
                w = xf - x;
            } else {
                xf = x + unitSize;
                w = xf - xi;
                x = xi;
            }

//            System.out.println("y: " + y);
//            System.out.println("yf: " + yf + " yi: " + yi);
//            System.out.println("w : "+ w + " h: " + h + "\n");

            dragged = false;
        }

        switch (type) {
            case 'w' -> {
                recentID = ID.WALL;
                handler.addObject(new Walls(x, y, 0, 0, w, h, handler, ID.WALL, imageManager));
                currentType = "w";
            }
            case 'b' -> {
                recentID = ID.BUTTON;
                handler.addObject(new Button(x, y, 0, 0, w, h, handler, recentID, imageManager));
                currentType = "b";
            }
            case 'p' -> {
                recentID = ID.PASSAGE;
                handler.addObject(new Passage(x, y, 0, 0, w, h, handler, recentID, null, imageManager));
                currentType = "p";
            }
            case 'h' -> {
                recentID = ID.HEALING;
                inventory.addItem(new HealingPotion(x, y, 0, 0, inventory, recentID, handler));
                currentType = "h";
            }
            case 'g' -> {
                recentID = ID.PISTOL;
                inventory.addItem(new Pistol(x, y, 0, 0, inventory, recentID, handler));
                currentType = "g";
            }
            case 's' -> {
                recentID = ID.SHOTGUN;
                inventory.addItem(new Shotgun(x, y, 0, 0, inventory, ID.SHOTGUN));
                currentType = "s";
            }

            case 'z' -> {
                recentID = ID.BASIC_ZOMBIE;
                handler.addEnemy(new BasicZombie(x, y, 0, 0, handler, recentID, levelManager, null, imageManager));
                currentType = "z";
            }
            case 'm' -> {
                recentID = ID.PLAYER;
                handler.addObject(new Player(x, y, 0, 0, handler, recentID, inventory, levelManager, null,  imageManager));
                currentType = "m";

            }
            case 'c' -> {
                recentID = ID.COIN;
                handler.addObject(new Coin(x,y,0,0,handler, ID.COIN));
                currentType = "c";

            }
        }


        addData(String.valueOf(x), String.valueOf(y), String.valueOf(w), String.valueOf(h), currentType);

    }


    public static void undo() {

        if (recentIDS.size() > 0) {
            for (int i = handler.object.size() - 1; i >= 0; i--) {
                GameObject temp = handler.object.get(i);
                if (temp.getId() == recentIDS.get(recentIDS.size() - 1)) {
                    handler.removeObject(temp);
                    recentIDS.remove(recentIDS.size() - 1);
                    break;
                }
            }
        }

        if (data.size() > 0) {
            int size = data.size();
            for (int i = size - 1; i >= size - 5; i--) {
                data.remove(i);
            }
        }
    }

    public static void reset() {
//        if (recentIDS.size() > 0) {
//            for (int j = handler.object.size() - 1; j >= 0; j--) {
//                GameObject temp = handler.object.get(j);
//                if (temp.getId() == recentIDS.get(recentIDS.size() - 1)) {
//                    handler.removeObject(temp);
//                    recentIDS.remove(recentIDS.size() - 1);
//                }
//            }
//        }
        handler.reset();
        inventory.clearItems();
        if (data.size() > 0) {
            int size = data.size();
            for (int i = size - 1; i >= size - 5; i--) {
                data.remove(i);
            }
        }
    }

    public static void addData(String x, String y, String w, String h, String type) {
        if (x.length() > 0 && y.length() > 0 && type.length() > 0) {
            data.add(x);
            data.add(y);
            data.add(w);
            data.add(h);
            data.add(type);
        }

    }

    public void saveLevel() {
        saveData.saveToFile(data);

    }

    public void getData() {
        System.out.println(saveData.readFromFile(2));
    }


}
