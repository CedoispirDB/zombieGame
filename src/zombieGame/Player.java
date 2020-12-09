package zombieGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

    private final Handler handler;
    private GameObject player;
    private final Game game;
    protected SpriteSheet ss;

    private final BufferedImage[] playerSkin = new BufferedImage[3];

    Animation anim;


    public Player(Game game, int x, int y, ID id, Handler handler, HUD hud, SpriteSheet ss) {
        super(x, y, id, ss);

        this.handler = handler;
        this.game = game;

        playerSkin[0] = ss.grabImage(1, 1, 32, 48);
        playerSkin[1] = ss.grabImage(2, 1, 32, 48);
        playerSkin[2] = ss.grabImage(3, 1, 32, 48);

        anim = new Animation(3, playerSkin[0], playerSkin[1], playerSkin[2]);

    }

    //Horizontal collision
    public Rectangle getBounds() {

        float bx = x + velX + 2;
        float by = y;
        float bw = 32 + (velX - 5) / 2;
        float bh = 48;

        return new Rectangle((int) bx, (int) by, (int) bw, (int) bh);
    }

    //Vertical collision
    public Rectangle getBounds2() {

        float bx = x;
        float by = y + velY;
        float bw = 31;
        float bh = 31 + velY / 2;


        return new Rectangle((int) bx, (int) by, (int) bw, (int) bh);
    }

    public void tick() {

        // x max = 1165
        // y max = 744
        x += velX;
        y += velY;


        x = Game.clamp(x, 0, Game.WIDTH - 35);
        y = Game.clamp(y, 0, Game.HEIGHT - 56);

        collision();

        anim.runAnimation();

    }

//    private void collision() {
//
//        for (int i = 0; i < handler.object.size(); i++) {
//            GameObject tempObject = handler.object.get(i);
//            if (tempObject.getId() == ID.Obstacles) {
//
//                if (getBounds().intersects(tempObject.getBounds())) {
//
//                    if (velX > 0) {// Going to the right
//
//                        velX = 0;
//                        x = tempObject.getX() - 33;
//                        velX = 5;
//
//                    } else if (velX < 0) {// Going to the left
//                        velX = 0;
//                        x = tempObject.getX() + 28;
//                        velX = -5;
//
//                    }
//
//                }
//
//                if (getBounds2().intersects(tempObject.getBounds())) {
//
//                    if (velY > 0) {// Going to Down
//
//                        velY = 0;
//                        y = tempObject.getY() - 34;
//                        velY = 5;
//
//                    } else if (velY < 0) {// Going up
//                        velY = 0;
//                        y = tempObject.getY() + tempObject.getH() + 3;
//                        velY = -5;
//
//                    }
//                }
//
//            }
//
//        }
//
//        for (int i = 0; i < handler.object.size(); i++) {
//            System.out.print(handler.object.size());
//            GameObject tempObject = handler.object.get(i);
//
//            if (tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy || tempObject.getId() == ID.EnemyBullet || tempObject.getId() == ID.EnemyBoss) {
//                // tempObject is now enemy
//                if (getBounds().intersects(tempObject.getBounds())) {
//                    // Collision code
//                    HUD.HEALTH -= 2;
//
//                    if (tempObject.getId() != ID.EnemyBoss && hud.getLevel() == 10) {
//                        handler.removeObject(tempObject);
//                    }
//
//                }
//
//                if (hud.getLevel() == 10) {
//                    for (int j = 0; j < handler.object.size(); j++) {
//                        player = handler.object.get(j);
//                    }
//                    if (player.getY() < 135) {
//                        HUD.HEALTH -= 2;
//
//                    }
//
//                }
//            }
//
//        }
//    }

    private void collision() {

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Block) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    x += velX * -1;
                    y += velY * -1;
                }
            }
        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Crate) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    game.ammo += 10;
                    handler.removeObject(tempObject);
                }
            }
        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Enemy) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    HUD.HEALTH--;
                }
            }
        }

    }

    public void render(Graphics g) {

//        Shows the hit box

//        Graphics2D g2d = (Graphics2D) g;
//        g.setColor(Color.red);
//        g2d.draw(getBounds());
//        g.setColor(Color.yellow);
//        g2d.draw(getBounds2());

//        g.drawImage(PS, (int) x, (int) y, null);
        if (velX == 0 && velY == 0) {
            g.drawImage(playerSkin[0], (int) x, (int) y, null);
        } else {
            anim.drawAnimation(g, x, y, 0);

        }
    }


}
