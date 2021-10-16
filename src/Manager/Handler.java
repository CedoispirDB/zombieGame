package Manager;

import Manager.GameObject;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

public class Handler {

    public LinkedList<GameObject> object = new LinkedList<>();

    public Handler() {

    }


    public void tick() {
        for (int i = 0; i < object.size(); i ++) {
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g) {
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
}
