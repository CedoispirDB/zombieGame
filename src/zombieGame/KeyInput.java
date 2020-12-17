package zombieGame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private final Handler handler;
    private final boolean[] keyDown = new boolean[4];
    private int dir;
    private final Game game;
    protected SpriteSheet ss;

    public KeyInput(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;

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
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                //All keys events for player 1

                if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
                    tempObject.setVelY(-5);
                    keyDown[0] = true;
                    dir = 0;

                }

                if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                    tempObject.setVelY(5);
                    keyDown[1] = true;
                    dir = 1;
                }
                if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                    tempObject.setVelX(5);
                    keyDown[2] = true;
                    dir = 2;
                }
                if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                    tempObject.setVelX(-5);
                    keyDown[3] = true;
                    dir = 3;
                }
                if (key == KeyEvent.VK_SPACE) {
                    if (Game.bombs >= 1) {
                        handler.addBullet(new MagicBomb(tempObject.getX() + 8, tempObject.getY() + 8, ID.MagicBomb, dir, ss));
                    }
                    Game.bombs--;
//                    handler.addBullet(new SantaSpell(game, 500, 500, ID.SantaSpell, handler, null, ss));

                }
                if (key == KeyEvent.VK_Z) {
                    HUD.HEALTH = 0;
                }
                if (key == KeyEvent.VK_L) {
                    Poppy.sit = !Poppy.sit;
                }

            }

            if (key == KeyEvent.VK_P) {
                if (game.gameState == Game.STATE.Garden) {
                    Game.paused = !Game.paused;
                }
            }

        }
        if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_Q) {
            System.exit(1);
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                //All keys events for player 1
                if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
//                    tempObject.setVelY(0);
                    keyDown[0] = false;

                }
                if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
//                    tempObject.setVelY(0);
                    keyDown[1] = false;
                }
                if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
//                    tempObject.setVelX(0);
                    keyDown[2] = false;
                }
                if (key == KeyEvent.VK_A | key == KeyEvent.VK_LEFT) {
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
