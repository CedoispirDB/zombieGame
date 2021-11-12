package Manager;

import Manager.GameObject;

import java.awt.*;
import java.io.Serializable;
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

    public void render(Graphics g) {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(g);
        }

        for (int i = 0; i< object.size(); i++) {
            object.get(i).render(g);
         //   System.out.println("Rendering: " + object.get(i));
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
            if (temp.getId() == ID.Player) {
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
