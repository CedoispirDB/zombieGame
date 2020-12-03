package zombieGame;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    LinkedList<GameObject> object = new LinkedList<>();
    private final HUD hud;

    public Handler(HUD hud) {
        this.hud = hud;
    }


    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void clearEnemys() {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            if (tempObject.getId() == ID.Player) {
                object.clear();
                if (Game.gameState != Game.STATE.End) {
                    addObject(new Player((int) tempObject.getX(), (int) tempObject.getY(), ID.Player, this, hud));
                }
            }
        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

}
