package Game.Main;

import Game.DataManager.DataManager;
import Game.DataManager.SaveData;
import Game.Input.KeyInput;
import Game.Levels.LevelBuilder;
import Game.Levels.LevelManager;
import Game.Levels.Tutorial;
import Game.Manager.Handler;
import Game.Manager.STATE;
import Game.Map.Node;
import Game.Player.Interface;
import Game.Player.Inventory;
import  Game.DataManager.Deserializer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import Game.Render.ImageManager;
import Game.UI.*;
import Game.UI.Menu;

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
    private boolean editMode = true;
    private Inventory inventory;
    private Node[][] grid;
    private final int level = 1;
    private final Interface anInterface;
    private final Menu menu;
    private final Help help;
    private final Pause pause;
    private final DeathScreen deathScreen;
    private final Leaderboard leaderboard;
//    private final ScoreManager scoreManager;
    private final ImageManager imageManager;
    private final DataManager dataManager;
    private Tutorial tutorial;
    public static STATE gameState = STATE.MENU;


    public GamePanel() {
        DataManager dataManager1;
        Deserializer deserializer = new Deserializer();
        dataManager1 = deserializer.loadData();

        if (dataManager1 == null) {
            dataManager1 = new DataManager();
        }

        dataManager = dataManager1;
        dataManager.printLevelData();

        random = new Random();
        handler = new Handler();
        anInterface = new Interface();
        menu = new Menu(this);
        help = new Help();
//        scoreManager = new ScoreManager();
        leaderboard = new Leaderboard(dataManager);
        pause = new Pause(this);
        deathScreen = new DeathScreen(anInterface, this, dataManager);
        saveData = new SaveData();
        imageManager = new ImageManager();
        inventory = new Inventory(handler, anInterface);
        levelManager = new LevelManager(handler, saveData, inventory, anInterface, imageManager, dataManager);

//        dataManager.printData();

        tutorial = new Tutorial(handler, inventory, levelManager, anInterface, pause, imageManager);
        this.addKeyListener(tutorial);

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);



        this.addKeyListener(new KeyInput(this, handler, inventory, deathScreen, anInterface, imageManager, levelManager));
        this.addMouseListener(menu);
        this.addMouseListener(help);
        this.addMouseListener(leaderboard);
        this.addMouseListener(pause);
        this.addMouseListener(deathScreen);
        init();
        startGame();
    }


    public void init() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }


    public void startGame() {
        grid = levelManager.getGrid();
        if (gameState == STATE.GAME) {
            levelManager.loadLevel(level);
            levelManager.setLevel(level);
        }
    }

    public void restartGame() {

        levelManager.restart();
    }

    public void tick() {
        if (gameState == STATE.GAME || gameState == STATE.BUILD) {
            handler.tick();
            inventory.tick();
        } else if (gameState == STATE.TUTORIAL) {
            tutorial.tick();
        }
    }

    public void render(Graphics g) {

        if (running) {
//            System.out.println(gameState);
//            g.setColor(new Color(120, 230, 220));
//            for (int i = 1; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//            }
//            for (int i = 1; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//            }


            BufferedImage image = imageManager.getTexture("f");

            if (gameState == STATE.GAME || gameState == STATE.PAUSE && !Tutorial.isTutorial) {
                levelManager.renderLevel(g);
                handler.render(g);
                inventory.render(g);
                anInterface.render(g);
                if (gameState == STATE.PAUSE) {
                    pause.render(g);
                }
            } else if (gameState == STATE.MENU) {
                for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                    for (int j = 0; j < SCREEN_HEIGHT / UNIT_SIZE; j++) {
                        g.drawImage(image, i * 32, j * 32, null);

                    }
                }
                menu.render(g);
            } else if (gameState == STATE.HELP) {
                for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                    for (int j = 0; j < SCREEN_HEIGHT / UNIT_SIZE; j++) {
                        g.drawImage(image, i * 32, j * 32, null);

                    }
                }
                help.render(g);
            } else if (gameState == STATE.LEADERBOARD) {
                for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                    for (int j = 0; j < SCREEN_HEIGHT / UNIT_SIZE; j++) {
                        g.drawImage(image, i * 32, j * 32, null);

                    }
                }
                leaderboard.render(g);
            } else if (gameState == STATE.DEATH || gameState == STATE.END) {
                deathScreen.render(g);
            } else if (gameState == STATE.TUTORIAL || gameState == STATE.PAUSE) {
                tutorial.render(g);
            } else if (gameState == STATE.BUILD) {


                for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                    for (int j = 0; j < SCREEN_HEIGHT / UNIT_SIZE; j++) {
                        g.drawImage(image, i * 32, j * 32, null);

                    }
                }
                // Draw lines for debug
                g.setColor(Color.red.darker());
                for (int i = 1; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                    g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                }
                for (int i = 1; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                    g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                }

                handler.render(g);
                inventory.render(g);


                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g.setColor(Color.white);

                g.drawString("Block type (v): " + LevelBuilder.type, 30, 50);
                g.drawString("Can drag (b): " + LevelBuilder.drag, 30, 75);
                g.drawString("j: reset", 30, 100);
                g.drawString("k: save level", 30, 125);


            }

            // Print coordinates
//            g.setFont(new Font(null, 0, 10));
//            g.setColor(Color.red);
//            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
//                g.drawString(String.valueOf(i * 32), i * 32, 16);
//            }
//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawString(String.valueOf(i * 32), 0, i * 32 + 16);
//            }
//
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
