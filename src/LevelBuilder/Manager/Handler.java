package LevelBuilder.Manager;

import LevelBuilder.Objects.Entity;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    public LinkedList<Entity> objects;

    public  Handler() {
        objects = new LinkedList<>();

    }

    public void render(Graphics g) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).render(g);
        }
    }

    public void addObject(Entity obj) {
        objects.add(obj);
    }

    public void removeObject() {
        if(objects.size() > 0) {
            objects.remove(objects.size() - 1);
        }
    }

    public void removeAll() {
        objects.clear();
    }

}
