package zombieGame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Menu extends MouseAdapter {

    private Game game;
    private final Handler handler;
    private final Random r = new Random();
    private final HUD hud;
    private final Spawn spawn;
    protected SpriteSheet ss;
    protected SpriteSheet sc;
    private Tuples tuples;

    public Menu(Game game, Handler handler, HUD hud, SpriteSheet ss, SpriteSheet sc, Spawn spawn) {
        this.game = game;
        this.handler = handler;
        this.hud = hud;
        this.ss = ss;
        this.sc = sc;
        this.spawn = spawn;

        tuples = new Tuples();

    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        System.out.println(mx);
        System.out.println(my);
        if (game.gameState == Game.STATE.Menu) {

            // Play button
            if (mouseOver(mx, my, 611, 154, 200, 64)) {
                game.gameState = Game.STATE.Game;
                handler.addObject(new DemonKing(game, 712, 549, ID.Player, handler, hud, ss));
                handler.addObject(new Obstacles(game, 460, 336, ID.Obstacles, handler, hud, null));
//        handler.addObject(new Enemy(this, 48, 48, ID.Enemy, handler, hud, ss));
//        handler.addObject(new Poppy(this, 48, 48, ID.Poppy, handler, hud, ss));
                spawn.spawnEnemies();
                Game.i = 0;
                Game.started = false;
                Game.mana = 100;
                HUD.santaHP = 500;
                HUD.healthColor = 500;
                tuples.clearTuples();
                return;
            }

            // Help Button

            if (mouseOver(mx, my, 611, 253, 200, 64)) {
                game.gameState = Game.STATE.Help;
            }

            // Quit button
            if (mouseOver(mx, my, 611, 353, 200, 64)) {
                System.exit(1);
            }

        }

        //Back button for help
        if (game.gameState == Game.STATE.Help) {
            if (mouseOver(mx, my, (Game.WIDTH / 2) - 75, 350, 150, 64)) {
                game.gameState = Game.STATE.Menu;
                return;
            }
        }

        //Back button for end
        if (game.gameState == Game.STATE.End) {
            if (mouseOver(mx, my, (Game.WIDTH / 2) - 90, 350, 200, 64)) {
                game.gameState = Game.STATE.Menu;
                HUD.HEALTH = 100;
                HUD.santaHP = 500;
                Game.paused = false;
                handler.clear();


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
        if (game.gameState == Game.STATE.Menu) {
            Font fnt = new Font("arial", Font.BOLD, 50);
            Font fnt2 = new Font("arial", Font.BOLD, 35);

            g.setColor(Color.gray.brighter());
            g.fillRect((Game.WIDTH / 2) - 90, 150, 200, 64);
            g.fillRect((Game.WIDTH / 2) - 90, 250, 200, 64);
            g.fillRect((Game.WIDTH / 2) - 90, 350, 200, 64);

            g.setFont(fnt);
            g.setColor(Color.BLACK);
            g.drawString("Demon King", (Game.WIDTH / 2) - 140, 70);

            g.setFont(fnt2);
            g.drawRect((Game.WIDTH / 2) - 90, 150, 200, 64);
            g.drawString("Play", (Game.WIDTH / 2) - 25, 195);

            g.drawRect((Game.WIDTH / 2) - 90, 250, 200, 64);
            g.drawString("Help", (Game.WIDTH / 2) - 25, 295);

            g.drawRect((Game.WIDTH / 2) - 90, 350, 200, 64);
            g.drawString("Quit", (Game.WIDTH / 2) - 25, 395);


        } else if (game.gameState == Game.STATE.Help) {
            Font fnt = new Font("arial", Font.BOLD, 50);
            Font fnt2 = new Font("arial", Font.BOLD, 35);
            Font fnt3 = new Font("arial", Font.BOLD, 20);

            g.setColor(Color.gray.brighter());
            g.fillRect((Game.WIDTH / 2) - 170, 180, 399, 77);
            g.fillRect((Game.WIDTH / 2) - 75, 350, 150, 64);

            g.setFont(fnt);
            g.setColor(Color.BLACK);
            g.drawString("Help", (Game.WIDTH / 2) - 50, 70);

            g.drawRect((Game.WIDTH / 2) - 170, 180, 399, 77);

            g.setFont(fnt3);
            g.drawString("Use WASD keys to move player", (Game.WIDTH / 2) - 165, 200);
            g.drawString("Use the mouse right button to use spells", (Game.WIDTH / 2) - 165, 225);
            g.drawString("Use space to use a magic bomb", (Game.WIDTH / 2) - 165, 250);

            g.setFont(fnt2);
            g.drawRect((Game.WIDTH / 2) - 75, 350, 150, 64);
            g.drawString("Back", (Game.WIDTH / 2) - 40, 395);
        } else if (game.gameState == Game.STATE.End) {
            Font fnt = new Font("arial", Font.BOLD, 50);
            Font fnt2 = new Font("arial", Font.BOLD, 35);

            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("You died", (Game.WIDTH / 2) - 100, (Game.HEIGHT / 2) - 100);

            g.setFont(fnt2);
            g.drawRect((Game.WIDTH / 2) - 90, 350, 200, 64);
            g.drawString("Try Again", (Game.WIDTH / 2) - 70, 395);

        }

    }


}
