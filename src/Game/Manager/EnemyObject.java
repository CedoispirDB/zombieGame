package Game.Manager;

import Game.Map.Node;

import java.awt.*;

public abstract class EnemyObject {

    protected  int enemyHealth;
    protected double posX, posY;
    protected double velX, velY;
    protected Node node;
    protected ID id;
    private Handler handler;


    public EnemyObject(double posX, double posY, double velX, double velY, Handler handler, ID id) {
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.handler = handler;
        this.id = id;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {

        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }


    public int getEnemyHealth() {
        return enemyHealth;
    }

    public void setEnemyHealth(int enemyHealth) {
        this.enemyHealth = enemyHealth;
    }

    public abstract Rectangle getBounds();

    public abstract Node getNode();

    public abstract Rectangle getBoundsX();
    public abstract Rectangle getBoundsY();
}
