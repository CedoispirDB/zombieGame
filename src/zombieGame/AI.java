package zombieGame;

import java.awt.*;
import java.util.Random;

public class AI extends GameObject {

    private Rectangle AI;
    private float x;
    private float y;
    private GameObject player;

    private int i;

    public AI(Game game, float x, float y, ID id, Handler handler, HUD hud, SpriteSheet ss) {
        super(game, x, y, id, handler, hud, ss);
        this.x = x;
        this.y = y;

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                player = tempObject;
            }
        }

    }


    public void tick() {

        System.out.println("AI: " + this.getX());
        System.out.println("player: " + player.getX());
        if (x < player.getX()) {
            this.setVelX(2);
        }
        if (x > player.getX()) {
            this.setVelX(-2);
        }
        if (y < player.getY()) {
            this.setVelY(2);
        }
        if (y> player.getY()) {
            this.setVelY(-2);
        }

        x += velX;
        y += velY;
    }


    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, 32, 32);

    }

    public Rectangle getBounds() {
        return null;
    }

    public Rectangle getBounds2() {
        return null;
    }
}
