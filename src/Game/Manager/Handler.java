package Game.Manager;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    public LinkedList<GameObject> object = new LinkedList<>();
    public LinkedList<EnemyObject> enemies = new LinkedList<>();

    public Handler() {

    }


    public void tick() {
        for (int i = 0; i < object.size(); i ++) {
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).tick();
        }
    }

    public void tickPlayer() {
        for (int i = 0; i < object.size(); i ++) {
            GameObject temp = object.get(i);
            if (temp.getId() == ID.PLAYER) {
                temp.tick();
            }
        }
    }

    public void tickBullets() {
        for (int i = 0; i < object.size(); i ++) {
            GameObject temp = object.get(i);
            if (temp.getId() == ID.BULLET) {
                temp.tick();
            }
        }
    }

    public void tickEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).tick();
        }
    }

    public void tickObjects() {
        for (int i = 0; i< object.size(); i++) {
            GameObject temp = object.get(i);
            if (temp.getId() != ID.PLAYER && temp.getId() != ID.BULLET) {
                temp.tick();
            }
        }
    }


    public void render(Graphics g) {
        renderCoins(g);
        renderEnemies(g);
        for (int i = 0; i< object.size(); i++) {
            GameObject temp = object.get(i);
            if (temp.getId() != ID.COIN) {
                temp.render(g);
            }

        }
    }

    public void renderCoins(Graphics g) {
        for (int i = 0; i< object.size(); i++) {
            GameObject temp = object.get(i);
            if (temp.getId() == ID.COIN) {
                temp.render(g);
            }

        }
    }

    public void removePlayer() {
        for (int i = 0; i< object.size(); i++) {
            GameObject temp = object.get(i);
            if (temp.getId() == ID.PLAYER) {
                removeObject(temp);
            }
        }
    }

    public void renderPlayer(Graphics g) {
        for (int i = 0; i< object.size(); i++) {
            GameObject temp = object.get(i);
            if (temp.getId() == ID.PLAYER) {
                temp.render(g);
            }
        }

    }

    public void renderBullets(Graphics g) {
        for (int i = 0; i< object.size(); i++) {
            GameObject temp = object.get(i);
            if (temp.getId() == ID.BULLET) {
                temp.render(g);
            }
        }
    }

    public void renderObjects(Graphics g) {
        for (int i = 0; i< object.size(); i++) {
            GameObject temp = object.get(i);
            if (temp.getId() != ID.PASSAGE && temp.getId() != ID.BUTTON) {
                temp.render(g);
            }
        }
        renderEnemies(g);
    }

    public void renderEnemies(Graphics g) {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(g);
        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

    public void addEnemy(EnemyObject object) {
        this.enemies.add(object);
    }

    public void removeEnemy(EnemyObject object) {
        this.enemies.remove(object);
    }

    public void removeAll() {
        int size = object.size();
        GameObject temp = null;
        for (int i = 0; i < size; i++) {
            temp = object.get(i);
            if (temp.getId() == ID.PLAYER) {
                break;
            }
        }
        object.clear();
        if (temp != null) {
            object.add(temp);
        }

        enemies.clear();

    }


    public void reset() {
        object.clear();
        enemies.clear();
    }
}
