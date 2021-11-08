package Main;

import DataManager.SaveData;
import Input.KeyInput;
import Levels.LevelBuilder;
import Levels.LevelManager;
import Manager.Handler;
import Manager.STATE;
import Map.Node;
import Player.Interface;
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
import UI.Menu;
import java.util.Set;

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
    private final int level = 0;
    private Interface anInterface;
    private Menu menu;
    public static STATE gameState = STATE.GAME;


    public GamePanel() {

        random = new Random();
        handler = new Handler();

        inventory = new Inventory(handler);
        anInterface = new Interface();
        menu = new Menu();

//        System.out.println(levelManager);

        saveData = new SaveData();
        LevelBuilder levelBuilder = new LevelBuilder(handler, saveData, inventory);

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new KeyInput(this, handler, levelBuilder, inventory));
        if (editMode) {
            this.addMouseListener(levelBuilder);
        }
        this.addMouseListener(menu);
        startGame();
    }


    public void startGame() {
        if (gameState == STATE.GAME) {
            levelManager = new LevelManager(handler, saveData, inventory, anInterface);
            grid = levelManager.getGrid();
            if (!editMode) {
                levelManager.loadLevel(level);
            }
        }
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void tick() {
        if(gameState != STATE.PAUSE) {
            handler.tick();
            inventory.tick();
        }
    }

    public void render(Graphics g) {

        if (running) {

//

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

            if (gameState == STATE.GAME) {
                levelManager.renderLevel(g);
                handler.render(g);
                inventory.render(g);
                anInterface.render(g);
            } else if(gameState == STATE.MENU) {
                menu.render(g);
            }

            // Print coordinates
//            g.setFont(new Font(null, 0 , 10));
//            g.setColor(Color.red);
//            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
//                g.drawString(String.valueOf(i * 32), i * 32, 16);
//            }
//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawString(String.valueOf(i * 32), 0, i*32 + 16);
//            }

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
