package Manager;

import Player.Inventory;

import java.awt.*;

public abstract class ItemObject {

    protected double posX, posY;
    protected double velX, velY;
    protected double iconX;
    protected double inventoryPos;

    protected double iconY;
    protected ID id;
    protected Inventory inventory;

    public ItemObject(double posX, double posY, double iconX, double iconY, Inventory inventory, ID id) {
        this.posX = posX;
        this.posY = posY;
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


    public double getIconX() {
        return iconX;
    }

    public void setIconX(double iconX) {
        this.iconX = iconX;
    }

    public double getIconY() {
        return iconY;
    }

    public void setIconY(double iconY) {
        this.iconY = iconY;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public double getInventoryPos() {
        return inventoryPos;
    }

    public void setInventoryPos(double inventoryPos) {
        this.inventoryPos = inventoryPos;
    }

    public abstract Rectangle getBounds();

    public abstract void drawIcon(Graphics g);

    public abstract void increase();




}
