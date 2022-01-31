package LevelBuilder.Main;


import LevelBuilder.Manager.Handler;
import LevelBuilder.Manager.ImageManager;
import LevelBuilder.Builder.LevelBuilder;
import LevelBuilder.Manager.Handler;
import LevelBuilder.Objects.Entity;
import LevelBuilder.UserInput.KeyInput;
import LevelBuilder.UserInput.MouseDragged;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Level;

public class GamePanel extends JPanel implements ActionListener {

    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;
    public static final int UNIT_SIZE = 32;
    public static int displayX;
    private static final int DELAY = 35;
    private boolean running = false;
    private Timer timer;
    private String type;

    private ImageManager imageManager;
    private BufferedImage floorImage;
    private LevelBuilder levelBuilder;
    private Handler handler;
    private KeyInput keyInput;
    private MouseDragged mouseDragged;


    public GamePanel() {

        displayX = 30;

        imageManager = new ImageManager();
        handler = new Handler();
        levelBuilder = new LevelBuilder(handler, imageManager);
        keyInput = new KeyInput(levelBuilder);

        floorImage = imageManager.getTexture("f");
        mouseDragged = new MouseDragged();
//
        this.addMouseMotionListener(mouseDragged);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addMouseListener(levelBuilder);
        this.addKeyListener(keyInput);


        init();
        startGame();
    }


    public void init() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }


    public void startGame() {

    }


    public void tick() {
    }

    public void render(Graphics g) {



        for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
            for (int j = 0; j < SCREEN_HEIGHT / UNIT_SIZE; j++) {
                g.drawImage(floorImage, i * 32, j * 32, null);

            }
        }


        handler.render(g);

        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.setColor(Color.white);

        switch (levelBuilder.getType()) {
            case "w" -> type = "Wall";
            case "cd" -> type = "Door";
            case "b" -> type = "Button";
            case "p" -> type = "Passage";
            case "z" -> type = "Zombie";
            case "c" -> type = "Coin";
        }
//        SCREEN_WIDTH - 7 * 32
        g.drawString("Block type (v): " + type, displayX, 50);
        g.drawString("Can drag (b): " + levelBuilder.getDrag(), displayX, 75);
        g.drawString("j: reset", displayX, 100);
        g.drawString("k: save level", displayX, 125);

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
