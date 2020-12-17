package zombieGame;

import java.util.LinkedList;
import java.awt.Graphics;

public class Handler {

    LinkedList<GameObject> object = new LinkedList<>();
    LinkedList<GameObject> bullets = new LinkedList<>();
    private final HUD hud;
    private Game game;
    protected SpriteSheet ss;

    public Handler(HUD hud, Game game, SpriteSheet ss) {
        this.hud = hud;
        this.game = game;
        this.ss = ss;
    }


    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
        for (int i = 0; i < bullets.size(); i++) {
            GameObject tempObject = bullets.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.render(g);
        }
        for (int i = 0; i < bullets.size(); i++) {
            GameObject tempObject = bullets.get(i);
            tempObject.render(g);
        }

    }

//    public void clearEnemies() {
//        for (int i = 0; i < object.size(); i++) {
//            GameObject tempObject = object.get(i);
//            if (tempObject.getId() == ID.Player) {
//                object.clear();
//                if (game.gameState != Game.STATE.End) {
//                    addObject(new DemonKing(game, (int) tempObject.getX(), (int) tempObject.getY(), ID.Player, this, hud, ss));
//                }
//            }
//        }
//    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    public void addBullet(GameObject object) {
        this.bullets.add(object);
    }


    public void removeBullet(GameObject object) {
        this.bullets.remove(object);
    }

    public void clear() {
        if (object.size() > 0) {
            this.object.clear();
        }
        if (bullets.size() > 0) {
            this.bullets.clear();
        }

    }


}
