package Player;

import javax.swing.*;
import java.awt.*;

public class Interface {

    private int health;
    private int healthBar;
    private int score;
    private int barG;
    private int barR;

    public Interface() {

        health = 160;
        healthBar = 160;
        barG = 100;
        barR = 0;
        score = 0;

    }

    public void tick() {

    }

    public void render(Graphics g) {
        renderHealthBar(g);
        renderScore(g);
    }

    private void renderHealthBar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        float thickness = 2;
        g.setColor(new Color(0.192f,0.192f,0.192f, 1f));
        g.fillRect(32, 0, 160, 32);
        g.setColor(new Color(barR, barG, 0));
        healthBar = clamp(healthBar,0, 160);
        g.fillRect(32, 0, healthBar, 32);
        g.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(thickness));
        g.drawRect(32, 0, 160, 32);
        for (int i = 2; i < 6; i++) {
            g.drawRect(32 * i, 0, 0, 32);
        }
    }

    private void renderScore(Graphics g) {
        String scoreStr = String.valueOf(score);
        int scoreLength = scoreStr.length();
        Font font = new Font("Serif", Font.BOLD, 40);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(scoreStr, 512 - 10 * scoreLength, 32);
    }

    public void hit(int damage) {
        health -= damage;
        healthBar -= damage;
        barG -= damage - 2;
        barR += damage;


        if (barG < 0) {
            barG = 0;
        }
        if (barR > 100) {
            barR = 100;
        }
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int points) {
        score += points;
    }

    public void setScore(int points) {
        score = points;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int life) {

        health += life;
        healthBar += life;
        barG += life;
        barR -= life;

        if (barG > 100) {
            barG = 100;
        }
        if (barR < 0) {
            barR = 0;
        }
    }

    public void reset() {
        health = 160;
        healthBar = 160;
        score = 0;
        barG = 100;
        barR = 0;
    }
    private int clamp(int value, int min, int max) {
        if (value >= max) {
            return max;
        } else if(value <= min) {
            return min;
        }
        return value;
    }
}
