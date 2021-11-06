package Player;

import java.awt.*;

public class Interface {
    private int health;
    private int barG;
    private int barR;

    public Interface() {

        health = 100;
        barG = 100;
        barR = 0;

    }

    public void tick() {


    }

    public void render(Graphics g) {
        // Health bar
        g.setColor(new Color(192,192,192));
        g.fillRect(32, 0, 160, 32);
        g.setColor(new Color(barR, barG, 0));
        g.fillRect(32, 0, health + 60, 32);
    }

    public void hit(int damage) {
        health -= damage;
        if (barG > 0) {
            barG -= damage;
        }
        if (barR < 255) {
            barR += damage;
        }

    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;

    }
}
