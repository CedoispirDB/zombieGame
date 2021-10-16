package Input;

import DataManager.Serializer;
import Levels.LevelBuilder;
import Levels.LevelManager;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import  Main.GamePanel;
import Player.Inventory;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.logging.Level;

public class KeyInput extends KeyAdapter {

    private final Handler handler;
    private final boolean[] keyDown = new boolean[4];
    private int typeIndex = 1;
    private int turnIndex = 1;
    private final LevelBuilder levelBuilder;
    private final Inventory inventory;

    public KeyInput(GamePanel game, Handler handler, LevelBuilder levelBuilder, Inventory inventory) {
        this.handler = handler;
        this.levelBuilder = levelBuilder;
        this.inventory = inventory;

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

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                //All keys events for player 1

                switch (code) {
                    case KeyEvent.VK_W -> {
                        tempObject.setVelY(-5);
                        keyDown[0] = true;
                    }
                    case KeyEvent.VK_S -> {
                        tempObject.setVelY(5);
                        keyDown[1] = true;
                    }
                    case KeyEvent.VK_D -> {
                        tempObject.setVelX(5);
                        keyDown[2] = true;
                    }
                    case KeyEvent.VK_A -> {
                        tempObject.setVelX(-5);
                        keyDown[3] = true;
                    }
                    case KeyEvent.VK_ESCAPE -> System.exit(0);
                }
            }

        }

        switch (code) {
            case KeyEvent.VK_V -> {
                LinkedList<Character> options = new LinkedList<>();
                options.add('w');
                options.add('b');
                options.add('p');
                options.add('h');
                options.add('g');

                LevelBuilder.type = options.get(typeIndex);
                typeIndex++;

                if (typeIndex == options.size()) {
                    typeIndex = 0;
                }
            }
            case KeyEvent.VK_T -> {
                LinkedList<Character> options = new LinkedList<>();
                options.add('t');
                options.add('l');
                options.add('b');
                options.add('r');

                LevelBuilder.position = options.get(turnIndex);
                turnIndex++;

                if (turnIndex == options.size()) {
                    turnIndex = 0;
                }
            }
            case KeyEvent.VK_W -> LevelBuilder.position = 't';
            case KeyEvent.VK_A -> LevelBuilder.position = 'l';
            case KeyEvent.VK_S -> LevelBuilder.position = 'b';
            case KeyEvent.VK_D -> LevelBuilder.position = 'r';
            case KeyEvent.VK_B -> LevelBuilder.drag = !LevelBuilder.drag;
            case KeyEvent.VK_Z -> LevelBuilder.undo();
            case KeyEvent.VK_K -> levelBuilder.saveLevel();
            case KeyEvent.VK_J -> LevelBuilder.reset();
            case KeyEvent.VK_M -> levelBuilder.getData();
            case KeyEvent.VK_E -> inventory.changeItem();
            case KeyEvent.VK_Q -> inventory.removeFromInventory();
        }


    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
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


            }
        }
    }

}