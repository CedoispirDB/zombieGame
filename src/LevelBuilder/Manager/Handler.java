package LevelBuilder.Manager;

import LevelBuilder.Objects.Entity;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    LinkedList<Entity> objects;

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

}
