package Levels;

import Enemies.BasicZombie;
import Items.Coin;
import Items.HealingPotion;
import Items.Pistol;
import Main.GamePanel;
import Manager.*;
import Map.Node;
import Player.Interface;
import Player.Inventory;
import Player.Player;
import Render.ImageManager;
import UI.Pause;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.util.LinkedList;

public class Tutorial extends KeyAdapter {

    private final Handler handler;
    private final Inventory inventory;
    private final LevelManager levelManager;
    private final Interface anInterface;
    private final Pause pause;
    private final ImageManager imageManager;
    private Player player;
    private boolean[] phases;
    private int count;
    private int keysPressed;
    private int previous;
    private LinkedList<Integer> pressed;
    private int index;
    private boolean gotGun;
    private boolean killed;
    private boolean gotHealing;
    private boolean healSelected;
    private boolean healed;
    private boolean gotCoins;
    private boolean scrolled;
    private boolean walked;
    private boolean dropped;
    private boolean paused, unpaused;
    private final Font font, font2;
    private int cnt;
    private int time;
    public static boolean isTutorial;
    private float alpha;
    private int pauseClicked;
    private STATE previousState;

    public Tutorial(Handler handler, Inventory inventory, LevelManager levelManager, Interface anInterface, Pause pause, ImageManager imageManager) {
        this.handler = handler;
        this.inventory = inventory;
        this.levelManager = levelManager;
        this.anInterface = anInterface;
        this.pause = pause;
        this.imageManager = imageManager;

        phases = new boolean[9];

        phases[0] = true;
        phases[1] = false;
        phases[2] = false;
        phases[3] = false;
        phases[4] = false;
        phases[5] = false;
        phases[6] = false;
        phases[7] = false;
        phases[8] = false;

        count = 1;
        keysPressed = 0;
        previous = 0;
        anInterface.hit(25);
        levelManager.setHasNeighbors(true);

        gotGun = false;
        killed = false;
        gotHealing = false;
        healSelected = false;
        healed = false;
        gotCoins = false;
        scrolled = false;
        walked = false;
        dropped = false;
        paused = false;
        unpaused = false;
        pressed = new LinkedList<>();
        index = 0;
        cnt = 0;
        time = 0;
        pauseClicked = 0;


        font = new Font("Arial", Font.BOLD, 15);
        font2 = new Font("Arial", Font.BOLD, 35);

        Node[][] grid = levelManager.getGrid();

        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 24; j++) {
                grid[i][j].addNeighbors(grid);
            }
        }

        alpha = 1.0f;

        isTutorial = true;
//        handler.addObject(new Walls(0,0,0,0,32, 768, handler, ID.WALL, imageManager));
//        handler.addObject(new Walls(992,0,0,0,32, 768, handler, ID.WALL, imageManager));

    }

    public void tick() {


        handler.tick();
        inventory.tick();

        if (player != null) {
            if (cnt == 0) {

                player.toggleCanPress();
                cnt++;
            }
        }


        if (phases[0] && count == 1) {
            handler.addObject(new Button(352, 480, 0, 0, 32, 32, handler, ID.BUTTON, imageManager));
            handler.addObject(new Passage(672, 480, 0, 0, 32, 32, handler, ID.PASSAGE, levelManager, imageManager));
            Player player = new Player(480, 320, 0, 0, handler, ID.PLAYER, inventory, levelManager, anInterface, imageManager);
            handler.addObject(player);
            this.player = player;
            count++;
        }


        if (phases[2] && count == 2) {
//            System.out.println("Creating a new weapon");
            inventory.addItem(new Pistol(612, 328, 0, 0, inventory, ID.PISTOL, handler));
            count++;
        }
        if (phases[3] && count == 3) {
//            handler.addEnemy(new BasicZombie(480, 96, 0, 0, handler, ID.BASIC_ZOMBIE, levelManager, anInterface, imageManager));
//            handler.addEnemy(new BasicZombie(480, 640, 0, 0, handler, ID.BASIC_ZOMBIE, levelManager, anInterface, imageManager));
            handler.addEnemy(new BasicZombie(672, 320, 0, 0, handler, ID.BASIC_ZOMBIE, levelManager, anInterface, imageManager));
//            handler.addEnemy(new BasicZombie(128, 320, 0, 0, handler, ID.BASIC_ZOMBIE, levelManager, anInterface, imageManager));
            count++;
        }

        if (phases[4] && count == 4) {
            count++;
        }


        if (phases[5] && count == 5) {
            inventory.addItem(new HealingPotion(329, 327, 0, 0, inventory, ID.HEALING, handler));
            count++;
        }
        if (phases[6] && count == 6) {
            handler.addObject(new Coin(584, 136, 0, 0, handler, ID.COIN));
            handler.addObject(new Coin(392, 136, 0, 0, handler, ID.COIN));
            count++;
        }

        if (phases[7] && count == 7) {
            count++;
        }

        if (phases[8] && count == 8) {
            count++;
        }


        if (phases[2]) {
            if (inventory.inventoryItems.size() == 1) {
                // Got gun
                gotGun = true;
            }
        }

        if (phases[3]) {
            if (handler.enemies.size() == 0) {
                // killed all zombies
                killed = true;
            }
        }

        if (phases[4]) {

        }

        if (phases[5]) {
            if (inventory.inventoryItems.size() == 2) {
                gotHealing = true;
            }
            if (gotHealing) {
                ItemObject temp = inventory.getSelectedItem();
                if (temp != null) {
                    if (temp.getId() == ID.HEALING) {
                        healSelected = true;
                    }
                }
            }
        }

        if (phases[6]) {

            if (handler.object.size() == 3) {
                gotCoins = true;
            }
        }

        if (phases[7]) {
            // check if item dropped
            if (inventory.inventoryItems.size() == 0) {
                dropped = true;
            }
        }

        if (paused && Pause.resumeClicked){
            paused = false;
            if (phases[0]) {
                unpaused = true;
            }
            Pause.resumeClicked = false;
            pauseClicked = 0;
        }

        if (unpaused) {
            phases[1] = true;
            phases[0] = false;
            unpaused = false;
        }

        if (walked) {
            // How to move
            phases[2] = true;
            phases[1] = false;
            keysPressed = 0;
            previous = 0;
            walked = false;
        }

        if (gotGun) {
            // Get the gun
            phases[3] = true;
            phases[2] = false;
            gotGun = false;
        }

        if (killed) {
            // Kill the zombies
            phases[4] = true;
            phases[3] = false;
            killed = false;

        }

        if (scrolled) {
            phases[5] = true;
            phases[4] = false;
            scrolled = false;
        }

        if (healed) {
            // Heal
            phases[6] = true;
            phases[5] = false;
            healed = false;
        }

        if (gotCoins) {
            // Get coins
            phases[7] = true;
            phases[6] = false;

            gotCoins = false;
        }

        if (dropped) {
            phases[8] = true;
            phases[7] = false;
            if (player != null) {
                player.toggleCanPress();
            }
            dropped = false;

        }

//
//        if () {
//            // Click button and pass passage;
//        }


    }


    public void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();


        levelManager.renderLevel(g);


        if (phases[8]) {
            handler.render(g);
        } else {
            handler.renderObjects(g);
        }


        anInterface.render(g);
        inventory.render(g);

        if (alpha >= 0) {
            g.setColor(new Color(1, 0, 0, alpha));
            alpha -= 0.02;
            g.setFont(font2);
            String text = "Tutorial";
            g.drawString(text, (int) (GamePanel.SCREEN_WIDTH / 2 - font2.getStringBounds(text, frc).getWidth() / 2), 100);
        }


        g.setColor(Color.RED);
        g.setFont(font);
        // Instructions
        if (phases[0]) {
            printMessage("Press P to pause the game", g, frc);

        }


        if (phases[1]) {
            printMessage("Use the keys WASD to walk", g, frc);
        }

        if (phases[2]) {
            printMessage("Go over an item to collect it", g, frc);
        }

        if (phases[3]) {
            printMessage("Use space bar to use your items. Kill the zombies to gain points", g, frc);
        }

        if (phases[4]) {
            printMessage("Use either the key Q or E to change the selected item", g, frc);
        }

        if (phases[5]) {
            printMessage("Collect the potion and use it to recover life", g, frc);
        }

        if (phases[6]) {
            printMessage("Collect coins to get points", g, frc);
        }

        if (phases[7]) {
            printMessage("Select an item and press F to drop an item", g, frc);
        }

        if (phases[8]) {

            printMessage("Press the button and pass through the door to complete a level", g, frc);

        }

        if (GamePanel.gameState == STATE.PAUSE) {
            pause.render(g);
        }

        if (paused) {
//345
//posX: 450
//125

//469
//491
//486
            g.setColor(Color.RED);
            g.setFont(font);
            // Draw lines
            g.drawLine(580, 225, 645, 200);
            g.drawLine(445, 305, 380, 280);
            g.drawLine(580, 380, 645, 365);

            // Draw messages
            String text = "Press Continue to resume the game";
            int w;
            int h;
            g.drawString(text, 650, 200);

            text = "Press Help to see the instructions for how to play";
            w = (int) font.getStringBounds(text, frc).getWidth();
            g.drawString(text, 375 - w, 280);

            text = "Press Menu to go back to the menu";
            h = (int) font.getStringBounds(text, frc).getHeight();
            g.drawString(text, 650, 365);

            text = "Doing so will restart the game";
            g.drawString(text, 650, 370 + h);

        }

    }

    private void printMessage(String text, Graphics g, FontRenderContext frc) {
        double w = font.getStringBounds(text, frc).getWidth();
        double h = font.getStringBounds(text, frc).getHeight();
        g.drawString(text, (int) (player.getPosX() - w / 2) + 16, (int) player.getPosY() - 10);
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();



        if (phases[0]) {
            if (code == 80) {
                pauseClicked++;
                if (pauseClicked == 1) {
                    paused = true;
                }
                if (pauseClicked == 2) {
                    unpaused = true;
                    paused = false;
                    pauseClicked = 0;
                }
            }
        } else {
            if (code == 80) {
                pauseClicked++;
                if (pauseClicked == 1) {
                    paused = true;
                }
                if (pauseClicked == 2) {
                    paused = false;
                    pauseClicked = 0;
                }
            }
        }

        if (phases[1]) {
            if (code == 87 || code == 83 || code == 65 || code == 68) {
                if (!pressed.contains(code)) {
                    pressed.add(code);
                }

                if (pressed.size() == 4) {
                    walked = true;
                    pressed.clear();
                }
            }
        }

        if (phases[4]) {
            if ((code == 69 || code == 81) && code != previous) {
                keysPressed++;
                previous = code;
                if (keysPressed == 2) {
                    scrolled = true;
                }
            }
        }

        if (phases[5]) {
            if (code == 32 && healSelected) {
                healed = true;
            }
        }

    }

}
