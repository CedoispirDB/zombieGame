package Main;

import DataManager.SaveData;
import Input.KeyInput;
import Levels.LevelBuilder;
import Levels.LevelManager;
import Manager.Handler;
import Map.Node;
import Player.Shooting;
import Player.Inventory;
import Manager.ID;
import Render.BufferedImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;
    public static final int UNIT_SIZE = 32;
    private static final int DELAY = 35;
    private boolean running = false;
    private Timer timer;
    private final Random random;

    private final Handler handler;
    private LevelManager levelManager;
    private final SaveData saveData;
    private boolean editMode = false;
    private Inventory inventory;
    private Node[][] grid;
    private final int level = 2;
    private BufferedImage image;


    public GamePanel() {
        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("/sprite.png");
        image = image.getSubimage(32,0,32,32);

        random = new Random();
        handler = new Handler();

        inventory = new Inventory(handler);

//        System.out.println(levelManager);

        Shooting shooting = new Shooting(handler);
        saveData = new SaveData();
        LevelBuilder levelBuilder = new LevelBuilder(handler, saveData, inventory);

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new KeyInput(this, handler, levelBuilder, inventory));
        this.addMouseListener(shooting);
        if (editMode) {
            this.addMouseListener(levelBuilder);
        }
        startGame();
    }


    public void startGame() {
        levelManager = new LevelManager(handler, saveData, inventory);
        grid = levelManager.getGrid();
        if (!editMode) {
            levelManager.loadLevel(level);
        }
//        handler.addObject(new Player.Player.Bullet(SCREEN_WIDTH / 2.0 + 16, SCREEN_HEIGHT / 2.0 + 16, 5,5, handler, Manager.ID.Player.Player.Bullet));
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void tick() {
        handler.tick();
        inventory.tick();
    }

    public void render(Graphics g) {
        if (running) {
            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                for (int j = 0; j < SCREEN_HEIGHT / UNIT_SIZE; j++) {
                    if (grid[i][j].getType().equals("n")) {
                        g.drawImage(image, i * 32, j * 32, null);
                    }
                }
            }

            g.setColor(Color.red);
            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
               g.drawString(String.valueOf(i * 32), i * 32, 16);
            }
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawString(String.valueOf(i * 32), 0, i*32 + 16);
            }
//            g.setColor(new Color(120, 230, 220));
//            for (int i = 1; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//            }
//            for (int i = 1; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//            }
            if (editMode) {

                // Draw lines for debug
                g.setColor(new Color(120, 230, 220));
                for (int i = 1; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                    g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                }
                for (int i = 1; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                    g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                }
                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g.setColor(Color.black);

                g.drawString("Block type (v): " + LevelBuilder.type, 30, 50);
                g.drawString("Can drag (b): " + LevelBuilder.drag, 30, 75);
                g.drawString("j: reset", 30, 100);
                g.drawString("k: save level", 30, 125);
            }




            handler.render(g);
            inventory.render(g);

//            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
//                for (int j = 0; j < SCREEN_HEIGHT / UNIT_SIZE; j++) {
//                    grid[i][j].render(g);
//                }
//            }
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        render(g);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            tick();
        }
        repaint();
    }


}
