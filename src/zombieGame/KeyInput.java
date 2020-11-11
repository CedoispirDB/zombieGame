package zombieGame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;

public class KeyInput extends KeyAdapter {

    private final Handler handler;
    private GameObject player;
    private final boolean[] keyDown = new boolean[4];
    private  int dir;
    private int anteriorKey;
    private HUD hud;

    Game game;

    public KeyInput(Handler handler, Game game, HUD hud) {
        this.handler = handler;
        this.game = game;
        this.hud = hud;

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
        int key = e.getKeyCode();
        anteriorKey = key;
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            player = tempObject;
            if (tempObject.getId() == ID.Player) {
                //All keys events for player 1

                if (key == KeyEvent.VK_W) {
                    tempObject.setVelY(-5);
                    keyDown[0] = true;
                    dir = 0;
                }

                if (key == KeyEvent.VK_S) {
                    tempObject.setVelY(5);
                    keyDown[1] = true;
                    dir = 1;
                }
                if (key == KeyEvent.VK_D) {
                    tempObject.setVelX(5);
                    keyDown[2] = true;
                    dir = 2;
                }
                if (key == KeyEvent.VK_A) {
                    tempObject.setVelX(-5);
                    keyDown[3] = true;
                    dir = 3;
                }
                if (key == KeyEvent.VK_SPACE) {

                    handler.addObject(new Bullet(player.getX() + 8, player.getY(), ID.Bullet, handler, dir));

                }

            }

        }


        if (key == KeyEvent.VK_P) {
            if (Game.gameState == Game.STATE.Game) {
                if (Game.paused) {
                    Game.paused = false;
                } else {
                    Game.paused = true;
                }
            }
        }

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }


    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                //All keys events for player 1
                if (key == KeyEvent.VK_W) {
//                    tempObject.setVelY(0);
                    keyDown[0] = false;
                }
                if (key == KeyEvent.VK_S) {
//                    tempObject.setVelY(0);
                    keyDown[1] = false;
                }
                if (key == KeyEvent.VK_D) {
//                    tempObject.setVelX(0);
                    keyDown[2] = false;
                }
                if (key == KeyEvent.VK_A) {
//                    tempObject.setVelX(0);
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