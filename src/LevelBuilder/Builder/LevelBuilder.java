package LevelBuilder.Builder;


import LevelBuilder.DataManager.DataManager;
import LevelBuilder.Main.GamePanel;
import LevelBuilder.Manager.ImageManager;
import LevelBuilder.Manager.Handler;
import LevelBuilder.Objects.Entity;

import javax.xml.crypto.Data;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class LevelBuilder extends MouseAdapter {

    public static String type = "w";
    public static char position = 't';
    public static boolean drag = false;
    public LinkedList<String> data = new LinkedList<>();
    public String levelData;


    private final int unitSize;
    private int xi, yi;
    private boolean dragged = false;
    private boolean load = false;
    private int lvl = 0;

    private ImageManager imageManager;
    private Handler handler;
    private DataManager dataManager;

    public LevelBuilder(Handler handler, ImageManager imageManager, DataManager dataManager) {
        this.handler = handler;
        this.imageManager = imageManager;
        this.dataManager = dataManager;
        unitSize = GamePanel.UNIT_SIZE;
        levelData = "";

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
            case "w" -> handler.addObject(new Entity("w", x,y, w, h, imageManager));
            case "cd" -> handler.addObject(new Entity("cd", x,y, w, h, imageManager));
            case "b" -> handler.addObject(new Entity("b", x,y, w, h,imageManager));
            case "p" -> handler.addObject(new Entity("p", x,y, w, h, imageManager));
            case "z" -> handler.addObject(new Entity("z", x,y,w, h, imageManager));
            case "c" -> handler.addObject(new Entity("c", x,y, w, h,imageManager));
        }


        addData(String.valueOf(x), String.valueOf(y), String.valueOf(w), String.valueOf(h), type);

    }


    // Remove last placed object
    public void undo() {
        handler.removeObject();
    }

    // Clean all objects from screen
    public void reset() {
        handler.removeAll();
    }

    // Load an existing level to work on
    public void load(int lvl) {
        load = true;
        this.lvl = lvl;
        LinkedList<String> data = dataManager.loadData(lvl);
        int i = 0;
        String t;
        int x, y, w, h;


        do {
            x = Integer.parseInt(data.get(i));
            y = Integer.parseInt(data.get(i + 1));
            w = Integer.parseInt(data.get(i + 2));
            h = Integer.parseInt((data.get(i + 3)));
            t = data.get(i + 4);

            handler.addObject(new Entity(t, x, y, w, h, imageManager));

            if (!levelData.equals("")) {
                levelData = levelData + " ";
            }

            levelData = levelData + x + " " +  y + " " + w + " " + h + " " + t;


            i += 5;
        } while (i < data.size());

        System.out.println(levelData);
    }

    // Save level data
    public void save() {
//        System.out.println("Data: " + levelData);
        if (!load) {
            dataManager.saveData(levelData + "\n*");
        } else {
            System.out.println("load:" + levelData);
//            dataManager.insert(levelData , lvl);
            load = false;
        }
    }

//    data = x + y + w + h + type
    public void addData(String x, String y, String w, String h, String type) {
        if (x.length() > 0 && y.length() > 0 && type.length() > 0) {
            if (!levelData.equals("")) {
                levelData = levelData + " ";
            }
            levelData = levelData + x + " " +  y + " " + w + " " + h + " " + type;
        }

    }

    public String getType() {
        return type;
    }

    public void setType(String t) {
        type = t;
    }

    public boolean getDrag() {
        return drag;
    }

    public void setDrag(boolean opt) {
        drag = opt;
    }

    public void toggleDrag() {
        drag = !drag;
    }


}
