package Game.Input;

import Game.Levels.LevelBuilder;
import Game.Levels.LevelManager;
import Game.Levels.Tutorial;
import Game.Manager.*;
import Game.Main.GamePanel;
import Game.Player.Bullet;
import Game.Player.Interface;
import Game.Player.Inventory;
import Game.Render.ImageManager;
import Game.UI.DeathScreen;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class KeyInput extends KeyAdapter {

    private final Handler handler;
    private final boolean[] keyDown = new boolean[4];
    private int typeIndex = 1;
    private int turnIndex = 1;
    private final Inventory inventory;
    private String lastDir;
    private boolean canShoot;
    private DeathScreen deathScreen;
    private boolean shiftPressed;
    private boolean capsPressed;
    private GamePanel gamePanel;
    private Interface anInterface;
    private ImageManager imageManager;
    private LevelManager levelManager;


    public KeyInput(GamePanel gamePanel, Handler handler, Inventory inventory, DeathScreen deathScreen, Interface anInterface, ImageManager imageManager, LevelManager levelManager) {
        this.handler = handler;
        this.inventory = inventory;
        this.deathScreen = deathScreen;
        this.gamePanel = gamePanel;
        this.anInterface = anInterface;
        this.imageManager = imageManager;
        this.levelManager = levelManager;

        canShoot = true;

        lastDir = "d";

        // 'w' key
        keyDown[0] = false;
        // 's' key
        keyDown[1] = false;
        // 'd' key
        keyDown[2] = false;
        // 'a' key
        keyDown[3] = false;


    }


    public void keyPressed(KeyEvent e) {
//        System.out.println(e.getKeyCode());
//        System.out.println(KeyEvent.VK_W);
        int code = e.getKeyCode();
        String opt = "a";

        if (code == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

        if (GamePanel.gameState == STATE.DEATH || GamePanel.gameState == STATE.END) {
            String str = String.valueOf((char) code);
//            System.out.println("str 1: " + str);

            if (code == 16) {
                // shift pressed
                shiftPressed = true;
            }
            if (code == 20) {
                // caps pressed
                capsPressed = true;
            }

            if (code == 10) {
                GamePanel.gameState = STATE.MENU;
                gamePanel.restartGame();
            }

            if (!shiftPressed && !capsPressed) {
                str = str.toLowerCase();
            }

            if (code == 8) {
                opt = "r";
            }

            if (code > 49 && code < 91 || code == 8) {
                deathScreen.reloadString(str, opt);
            }
        }



         if (GamePanel.gameState == STATE.GAME || GamePanel.gameState == STATE.TUTORIAL) {

            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getId() == ID.PLAYER) {
                    //All keys events for player 1

                    switch (code) {
                        case KeyEvent.VK_W -> {
                            tempObject.setVelY(-5);
                            keyDown[0] = true;
                            lastDir = "w";
                        }
                        case KeyEvent.VK_S -> {
                            tempObject.setVelY(5);
                            keyDown[1] = true;
                            lastDir = "s";
                        }
                        case KeyEvent.VK_D -> {
                            tempObject.setVelX(5);
                            keyDown[2] = true;
                            lastDir = "d";
                        }
                        case KeyEvent.VK_A -> {
                            tempObject.setVelX(-5);
                            keyDown[3] = true;
                            lastDir = "a";
                        }
                    }
                }

            }

            switch (code) {
                case KeyEvent.VK_E -> inventory.changeItem(0);
                case KeyEvent.VK_Q -> inventory.changeItem(1);
                case KeyEvent.VK_SPACE -> {
                    GameObject player = null;
                    for (int i = 0; i < handler.object.size(); i++) {
                        GameObject temp = handler.object.get(i);
                        if (temp.getId() == ID.PLAYER) {
                            player = temp;
                        }
                    }
                    if (player != null) {

                        if (canShoot && inventory.canShoot()) {
                            double posX = player.getPosX();
                            double posY = player.getPosY();

                            switch (lastDir) {
                                case "w" -> {
                                    posX += 8;
                                    posY -= 8;
                                }
                                case "s" -> {
                                    posX += 8;
                                    posY += 25;
                                }
                                case "d" -> {
                                    posX += 23;
                                    posY += 8;
                                }
                                case "a" -> {
                                    posY += 8;
                                    posX -= 5;
                                }
                            }


                            handler.addObject(new Bullet(posX, posY, 0, 0, handler, ID.BULLET, lastDir, imageManager));
                            canShoot = false;

                        } else {
                            if (anInterface.getHealth() < 160) {
                                inventory.heal();
                            }
                        }
                    }

                }
                case KeyEvent.VK_P -> GamePanel.gameState = STATE.PAUSE;
                case KeyEvent.VK_F -> {
                    GameObject player = null;
                    for (int i = 0; i < handler.object.size(); i++) {
                        GameObject temp = handler.object.get(i);
                        if (temp.getId() == ID.PLAYER) {
                            player = temp;
                        }
                    }
                    inventory.removeFromInventory(levelManager);

                }

            }
        } else if (GamePanel.gameState == STATE.PAUSE) {
            if (code == KeyEvent.VK_P) {
                if (Tutorial.isTutorial) {
                    GamePanel.gameState = STATE.TUTORIAL;
                } else {
                    GamePanel.gameState = STATE.GAME;
                }
            }
        }


    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (GamePanel.gameState == STATE.DEATH) {
            if (key == 16) {
                shiftPressed = false;
            }
            if (key == 20) {
                capsPressed = false;
            }
        }

        if (GamePanel.gameState == STATE.GAME || GamePanel.gameState == STATE.TUTORIAL) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getId() == ID.PLAYER) {
                    //All keys events for player 1
                    if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
                        keyDown[0] = false;
                    }
                    if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                        keyDown[1] = false;
                    }
                    if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                        keyDown[2] = false;
                    }
                    if (key == KeyEvent.VK_A | key == KeyEvent.VK_LEFT) {
                        keyDown[3] = false;
                    }

                    // Vertical movement
                    if (!keyDown[0] && !keyDown[1]) {
                        tempObject.setVelY(0);
                    }
                    // Horizontal movement

                    if (!keyDown[2] && !keyDown[3]) {
                        tempObject.setVelX(0);
                    }

                    if (key == KeyEvent.VK_SPACE) {
                        canShoot = true;
                    }
                }
            }
        }
    }

}
