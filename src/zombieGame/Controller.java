package zombieGame;

import java.awt.*;
import java.util.LinkedList;

public class Controller {

    private final LinkedList<Bullet> b = new LinkedList<>();

    Bullet tempBullet;

    Game game;

    public Controller(Game game){
        this.game = game;
        System.out.println("im here");

    }

    public void tick(){

        for (Bullet bullet : b) {
            tempBullet = bullet;
            tempBullet.tick();
        }
    }

    public void render(Graphics g){
        for (Bullet bullet : b) {
            tempBullet = bullet;

            tempBullet.render(g);
        }
    }

    public void addBullet(Bullet block){
        b.add(block);
    }

    public void removeBullet(Bullet block){
        b.remove(block);
    }

}
