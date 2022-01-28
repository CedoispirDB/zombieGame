package LevelBuilder.Main;

import Game.DataManager.DataManager;
import Game.DataManager.Deserializer;
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
import Game.Render.ImageManager;
import Game.UI.Menu;
import Game.UI.*;

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


    public GamePanel() {

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
