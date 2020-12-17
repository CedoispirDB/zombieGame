package zombieGame;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class DemonKing extends GameObject {

    private final Handler handler;
    private GameObject player;
    private GameObject enemy;
    private final Game game;
    protected SpriteSheet ss;
    private Tuples tuples;
    private final BufferedImage[] playerSkin = new BufferedImage[3];
    public static boolean movedX = false;
    public static boolean movedY = false;
    private float preX = this.getX();
    private float preY = this.getY();

    private int demonWidth;
    private int demonHeight;

    private Map map;


    Animation anim;


    public DemonKing(Game game, int x, int y, ID id, Handler handler, HUD hud, Map map) {
        super(x, y, id);

        this.handler = handler;
        this.game = game;
        this.map = map;

        tuples = new Tuples();


        playerSkin[0] = map.spriteSheet.grabImage(1, 1, 32, 48);
        playerSkin[1] = map.spriteSheet.grabImage(2, 1, 32, 48);
        playerSkin[2] = map.spriteSheet.grabImage(3, 1, 32, 48);

        anim = new Animation(3, playerSkin[0], playerSkin[1], playerSkin[2]);


    }

    public void tick() {

        x += velX;
        y += velY;


        x = Game.clamp(x, 0, (64 * 32));
        y = Game.clamp(y, 0, (64 * 32));

//        System.out.println("x: " + x);
//        System.out.println("y: " + y);


        collision();

        anim.runAnimation();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Minion) {
                enemy = tempObject;
            }
        }

        if (game.gameState == Game.STATE.Castle) {
            if (this.getX() <= 277) {
                x += velX * -1;
            }
            if (this.getX() >= 1164) {
                x += velX * -1;
            }
            if (this.getY() >= map.castleHeight) {
                y += velY * -1;
            }
            if (this.getY() <= 0) {
                y += velY * -1;
            }

        }


    }

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
                    Game.mana += 10;
                    handler.removeObject(tempObject);
                }
            }
        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Minion || tempObject.getId() == ID.Santa) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    HUD.HEALTH--;
                }
            }
        }


        checkSurrounds();


    }

    public boolean checkSurrounds() {
//                    System.out.println("x1: " + tempObject.getX());
//                    System.out.println("y1: " + tempObject.getY());
//                    System.out.println("x2: " + this.getX());
//                    System.out.println("y2: " + this.getY());
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Block) {
                if (tempObject.getBoundX().intersects(this.getBounds())) {
                    if (tempObject.getX() > this.getX()) {
//                        System.out.println("Right");
                    }
                    if (tempObject.getX() < this.getX()) {
//                        System.out.println("Left");
                    }
                }
                if (tempObject.getBoundY().intersects(this.getBounds())) {
                    if (tempObject.getY() > this.getY()) {
//                        System.out.println("Down");
                    }
                    if (tempObject.getY() < this.getY()) {
//                        System.out.println("Up");
                    }
                }
            }
        }
        return false;
    }

    public void render(Graphics g) {

        //Shows the hit box
        if (Game.showBounds) {
            Graphics2D g2d = (Graphics2D) g;
            g.setColor(Color.CYAN);
            g2d.draw(getBounds());
            g.setColor(Color.pink);
            g2d.draw(getBounds2());
        }
        if (velX == 0 && velY == 0) {
            g.drawImage(playerSkin[0], (int) x, (int) y, null);
        } else {
            anim.drawAnimation(g, x, y, 0);

        }
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

    public Rectangle getBoundX() {
        return null;
    }

    public Rectangle getBoundY() {
        return null;
    }


}
