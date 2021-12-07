package Main;

import DataManager.SaveData;
import DataManager.ScoreManager;
import Input.KeyInput;
import Levels.LevelBuilder;
import Levels.LevelManager;
import Levels.Tutorial;
import Manager.Handler;
import Manager.STATE;
import Map.Node;
import Player.Interface;
import Player.Inventory;
import Render.BufferedImageLoader;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import Render.ImageManager;
import Sound.SoundManager;
import UI.*;
import UI.Menu;

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
    private final int level = 0;
    private final Interface anInterface;
    private final Menu menu;
    private final Help help;
    private final Pause pause;
    private final DeathScreen deathScreen;
    private final Leaderboard leaderboard;
    private final ScoreManager scoreManager;
    private final ImageManager imageManager;
    private Tutorial tutorial;
    private LevelBuilder levelBuilder;
    public static STATE gameState = STATE.TUTORIAL;


    public GamePanel() {

        random = new Random();
        handler = new Handler();
        anInterface = new Interface();
        menu = new Menu(this);
        help = new Help();
        scoreManager = new ScoreManager();
        leaderboard = new Leaderboard(scoreManager);
        pause = new Pause(this);
        deathScreen = new DeathScreen(anInterface, scoreManager, this);
        saveData = new SaveData();
        imageManager = new ImageManager();
        inventory = new Inventory(handler, anInterface);
        levelManager = new LevelManager(handler, saveData, inventory, anInterface, scoreManager, imageManager);
        levelBuilder = new LevelBuilder(handler, saveData, inventory, imageManager, levelManager);

        if (gameState == STATE.TUTORIAL) {
            tutorial = new Tutorial(handler, inventory, levelManager, anInterface, pause, scoreManager, imageManager);
            this.addKeyListener(tutorial);

        }

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);

        if (gameState == STATE.BUILD) {
            this.addMouseListener(levelBuilder);
        }

        this.addKeyListener(new KeyInput(this, handler, levelBuilder, inventory, deathScreen, anInterface, imageManager, levelManager));
        this.addMouseListener(menu);
        this.addMouseListener(help);
        this.addMouseListener(leaderboard);
        this.addMouseListener(pause);
        this.addMouseListener(deathScreen);
        anInterface.increaseScore(100);
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

            if (gameState == STATE.GAME || gameState == STATE.PAUSE) {
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
            } else if (gameState == STATE.DEATH) {
                deathScreen.render(g);
            } else if (gameState == STATE.TUTORIAL) {
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
            g.setFont(new Font(null, 0, 10));
            g.setColor(Color.red);
            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                g.drawString(String.valueOf(i * 32), i * 32, 16);
            }
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawString(String.valueOf(i * 32), 0, i * 32 + 16);
            }
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
