package zombieGame;

import java.awt.*;

public abstract class GameObject {

    // protect : Inherited by others
    protected float x, y;
    protected ID id;
    protected float velX, velY;
    private Handler handler;
    private HUD hud;
    private float h;
    private float w;
    protected SpriteSheet ss;

    public GameObject(Game game, float x, float y, ID id, Handler handler, HUD hud, SpriteSheet ss) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.handler = handler;
        this.hud = hud;
        this.ss = ss;
    }

    public GameObject(float x, float y, ID id, SpriteSheet ss) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.ss = ss;
    }




    public void hit() {
        if (handler.object.size() != 0) {

            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);

                if (tempObject.getId() == ID.Bullet) {
                    // tempObject is now Bullet
                    if (getBounds().intersects(tempObject.getBounds())) {
                        // Collision code
                        handler.object.remove(this);
                        handler.object.remove(tempObject);
                        int x = hud.getScore();
                        hud.setScore(x + 1);
                    }

                }
            }
        }
    }



    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    public abstract Rectangle getBounds2();

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public float getW(){
        return w;
    }

    public float getH(){
        return h;
    }

}