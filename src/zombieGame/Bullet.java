package zombieGame;

import java.awt.*;
import java.util.Random;

public class Bullet extends GameObject {

    private Handler handler;
    private GameObject player;
    private float x;
    private float y;

    public Bullet(float x, float y, ID id,Handler handler){
        super(x,y,id);
        this.x = x;
        this.y = y;
        this.handler = handler;
    }

    public void tick(){
        y -= 5;


//        Systemd.out.println("is working");
    }

    public void render(Graphics g){
        g.setColor(Color.white);
        g.fillRect((int)x,(int) y, 16, 16);

    }


    public Rectangle getBounds() {
        return new Rectangle((int)x,(int) y, 16, 16);
    }
}
