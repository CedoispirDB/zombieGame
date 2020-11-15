package zombieGame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Menu extends MouseAdapter {

    private final Game game;
    private final Handler handler;
    private final Random r = new Random();
    private final HUD hud;
    private final EnemyBoss eb;

    public Menu(Game game, Handler handler, HUD hud, EnemyBoss eb) {
        this.game = game;
        this.handler = handler;
        this.hud = hud;
        this.eb = eb;


    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (Game.gameState == Game.STATE.Menu) {
            // Play button
            if (mouseOver(mx, my, 210, 150, 200, 64)) {
                Game.gameState = Game.STATE.Select;
                return;
            }
            // Quit button
            if (mouseOver(mx, my, 210, 350, 200, 64)) {
                System.exit(1);
            }
        }

        if (Game.gameState == Game.STATE.Select) {
            // Normal button
            if (mouseOver(mx, my, 210, 150, 200, 64)) {
                Game.gameState = Game.STATE.Game;
                handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler, hud));
                handler.clearEnemys();
//                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler, hud));
//                handler.addObject(new EnemyBoss(((Game.WIDTH / 2) - 48), -115 , ID.EnemyBoss, handler, hud));
                handler.addObject(new EasyZombie(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler, hud));

                Game.diff = 0;
            }
            // Hard button
            if (mouseOver(mx, my, 210, 275, 200, 64)) {
                Game.gameState = Game.STATE.Game;
                handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler, hud));
                handler.clearEnemys();
                handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler, hud));

                Game.diff = 1;
            }
        }

        // Back Button
        if (Game.gameState == Game.STATE.Select) {
            if (mouseOver(mx, my, 210, 350, 200, 64)) {
                Game.gameState = Game.STATE.Menu;
                return;
            }
        }

        // Help Button
        if (Game.gameState == Game.STATE.Menu) {
            if (mouseOver(mx, my, 210, 250, 200, 64)) {
                Game.gameState = Game.STATE.Help;
            }
        }

        //Back button for help
        if (Game.gameState == Game.STATE.Help) {
            if (mouseOver(mx, my, 210, 350, 200, 64)) {
                Game.gameState = Game.STATE.Menu;
                return;
            }
        }

        //Back button for end
        if (Game.gameState == Game.STATE.End) {
            if (mouseOver(mx, my, 210, 350, 200, 64)) {
                Game.gameState = Game.STATE.Menu;
                hud.setLevel(1);
                hud.setScore(0);
                eb.setEnemyHealth(100);

            }
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void tick() {

    }

    public void render(Graphics g) {
        if (Game.gameState == Game.STATE.Menu) {
            Font fnt = new Font("arial", Font.BOLD, 50);
            Font fnt2 = new Font("arial", Font.BOLD, 35);


            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Wave", 240, 70);

            g.setFont(fnt2);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Play", 275, 195);

            g.drawRect(210, 250, 200, 64);
            g.drawString("Help", 275, 295);

            g.drawRect(210, 350, 200, 64);
            g.drawString("Quit", 275, 395);
        } else if (Game.gameState == Game.STATE.Help) {
            Font fnt = new Font("arial", Font.BOLD, 50);
            Font fnt2 = new Font("arial", Font.BOLD, 35);
            Font fnt3 = new Font("arial", Font.BOLD, 20);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Help", 240, 70);

            g.setFont(fnt3);
            g.drawString("Use WASD keys to move player and dodge enemies", 60, 200);

            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Back", 275, 395);
        } else if (Game.gameState == Game.STATE.End) {
            Font fnt = new Font("arial", Font.BOLD, 50);
            Font fnt2 = new Font("arial", Font.BOLD, 35);
            Font fnt3 = new Font("arial", Font.BOLD, 20);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Game Over", 180, 70);

            g.setFont(fnt3);
            g.drawString("You lost with the score of:  " + hud.getScore(), 155, 197);

            g.setFont(fnt2);
            g.drawRect(210, 350, 200, 64);
            g.drawString("Try Again", 230, 395);

        } else if (Game.gameState == Game.STATE.Select) {
            Font fnt = new Font("arial", Font.BOLD, 50);
            Font fnt2 = new Font("arial", Font.BOLD, 35);


            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Select Difficult", 140, 70);

            g.setFont(fnt2);
            g.drawRect(210, 150, 200, 64);
            g.drawString("Normal", 275, 195);

            g.drawRect(210, 250, 200, 64);
            g.drawString("Hard", 275, 295);

            g.drawRect(210, 350, 200, 64);
            g.drawString("Back", 275, 395);
        }

    }


}
