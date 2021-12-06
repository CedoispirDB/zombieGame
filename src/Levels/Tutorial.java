package Levels;

import DataManager.ScoreManager;
import Enemies.BasicZombie;
import Items.Coin;
import Items.HealingPotion;
import Items.Pistol;
import Main.GamePanel;
import Manager.*;
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
    private final ScoreManager scoreManager;
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
    private final Font font;
    private int cnt;

    public Tutorial(Handler handler, Inventory inventory, LevelManager levelManager, Interface anInterface, Pause pause, ScoreManager scoreManager, ImageManager imageManager) {
        this.handler = handler;
        this.inventory = inventory;
        this.levelManager = levelManager;
        this.anInterface = anInterface;
        this.pause = pause;
        this.scoreManager = scoreManager;
        this.imageManager = imageManager;

        phases = new boolean[7];

        phases[0] = true;
        phases[1] = false;
        phases[2] = false;
        phases[3] = false;
        phases[4] = false;
        phases[5] = false;
        phases[6] = false;

        count = 1;
        keysPressed = 0;
        previous = 0;
        anInterface.hit(25);

        gotGun = false;
        killed = false;
        gotHealing = false;
        healSelected = false;
        healed = false;
        gotCoins = false;
        scrolled = false;
        walked = false;
        pressed = new LinkedList<>();
        index = 0;
        cnt = 0;

        font = new Font("Arial", Font.BOLD, 15);
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
            Player player = new Player(480, 320, 0, 0, handler, ID.PLAYER, inventory, levelManager, anInterface, scoreManager, imageManager);
            handler.addObject(player);
            this.player = player;

            count++;
        }
        if (phases[1] && count == 2) {
            inventory.addItem(new Pistol(612, 328, 0, 0, inventory, ID.PISTOL, handler));
            count++;
        }
        if (phases[2] && count == 3) {
//            handler.addEnemy(new BasicZombie(480, 96, 0, 0, handler, ID.BASIC_ZOMBIE, levelManager, anInterface, imageManager));
//            handler.addEnemy(new BasicZombie(480, 640, 0, 0, handler, ID.BASIC_ZOMBIE, levelManager, anInterface, imageManager));
            handler.addEnemy(new BasicZombie(672, 320, 0, 0, handler, ID.BASIC_ZOMBIE, levelManager, anInterface, imageManager));
//            handler.addEnemy(new BasicZombie(128, 320, 0, 0, handler, ID.BASIC_ZOMBIE, levelManager, anInterface, imageManager));
            count++;
        }

        if (phases[3] && count == 4) {
            count++;
        }


        if (phases[4] && count == 5) {
            inventory.addItem(new HealingPotion(329, 327, 0, 0, inventory, ID.HEALING, handler));
            count++;
        }
        if (phases[5] && count == 6) {
            handler.addObject(new Coin(576, 128, 0, 0, handler, ID.COIN));
            handler.addObject(new Coin(384, 128, 0, 0, handler, ID.COIN));
            count++;
        }
        if (phases[6] && count == 7) {
            count++;
        }


        if (phases[1]) {
            if (inventory.inventoryItems.size() == 1) {
                // Got gun
                gotGun = true;
            }
        }

        if (phases[2]) {
            if (handler.enemies.size() == 0) {
                // killed all zombies
                killed = true;
            }
        }

        if (phases[3]) {

        }

        if (phases[4]) {
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

        if (phases[5]) {

            if (handler.object.size() == 3) {
                gotCoins = true;
            }
        }

        if (walked) {
            // How to move
            phases[1] = true;
            phases[0] = false;
            keysPressed = 0;
            previous = 0;
            System.out.println("1 done");
            walked = false;
        }

        if (gotGun) {
            // Get the gun
            phases[2] = true;
            phases[1] = false;
            System.out.println("2 done");
            gotGun = false;
        }

        if (killed) {
            // Kill the zombies
            phases[3] = true;
            phases[2] = false;
            System.out.println("3 done");
            killed = false;

        }

        if (scrolled) {
            phases[4] = true;
            phases[3] = false;
            System.out.println("4 done");
            scrolled = false;
        }

        if (healed) {
            // Heal
            phases[5] = true;
            phases[4] = false;
            System.out.println("5 done");
            healed = false;
        }

        if (gotCoins) {
            // Get coins
            phases[6] = true;
            phases[5] = false;
            if (player != null) {
                player.toggleCanPress();
            }
            System.out.println("6 done");
            gotCoins = false;
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
        anInterface.render(g);
        inventory.render(g);

        if (phases[6]) {
            handler.render(g);
        } else {
            handler.renderObjects(g);
        }

        if (GamePanel.gameState == STATE.PAUSE) {
            pause.render(g);
        }

        g.setFont(font);
        // Instructions
        if (phases[0]) {
            printMessage("Use the keys WASD to walk", g, frc);
        }

        if (phases[1]) {
            printMessage("Go over an item to collect it", g, frc);
        }

        if (phases[2]) {
            printMessage("Use space bar to use your items", g, frc);
        }

        if (phases[3]) {
            printMessage("Use the key Q or E to change the selected item", g, frc);
        }

        if (phases[4]) {
            printMessage("Collect the potion and use it to recover life", g, frc);
        }

        if (phases[5]) {
            printMessage("Collect the coins to get points", g, frc);
        }

        if (phases[6]) {
            printMessage("Press the button and pass through the door to complete a level", g, frc);
        }

    }

    private void printMessage(String text, Graphics g, FontRenderContext frc) {
        double w = font.getStringBounds(text, frc).getWidth();
        double h = font.getStringBounds(text, frc).getHeight();
        g.drawString(text, (int)(player.getPosX() - w / 2) + 16 , (int)player.getPosY() - 10);
    }
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (phases[0]) {
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

        if (phases[3]) {
            if ((code == 69 || code == 81) && code != previous) {
                keysPressed++;
                previous = code;
                if (keysPressed == 2) {
                    scrolled = true;
                }
            }
        }

        if (phases[4]) {
            if (code == 32 && healSelected) {
                healed = true;
            }
        }

    }

}
